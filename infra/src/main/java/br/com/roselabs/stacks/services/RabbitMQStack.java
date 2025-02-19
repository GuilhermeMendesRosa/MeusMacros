package br.com.roselabs.stacks.services;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ecs.*;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck;
import software.amazon.awscdk.services.logs.LogGroup;
import software.constructs.Construct;

import java.util.Map;

public class RabbitMQStack extends Stack {

    public RabbitMQStack(final Construct scope, final String id, final Cluster cluster) {
        this(scope, id, null, cluster);
    }

    public RabbitMQStack(final Construct scope, final String id, final StackProps props, final Cluster cluster) {
        super(scope, id, props);

        int managementPort = 15672;
        int amqpPort = 5672;

        ApplicationLoadBalancedFargateService rabbitmqService = ApplicationLoadBalancedFargateService.Builder
                .create(this, "RabbitMQService")
                .serviceName("RabbitMQ")
                .cluster(cluster)
                .cpu(512)
                .desiredCount(1)
                .listenerPort(managementPort)
                .assignPublicIp(true)
                .taskImageOptions(ApplicationLoadBalancedTaskImageOptions.builder()
                        .containerName("rabbitmq")
                        .image(ContainerImage.fromRegistry("rabbitmq:3.10-management"))
                        // Configura o container para escutar na porta de gerenciamento (HTTP)
                        .containerPort(managementPort)
                        .logDriver(LogDriver.awsLogs(AwsLogDriverProps.builder()
                                .logGroup(LogGroup.Builder.create(this, "RabbitMqLogGroup")
                                        .logGroupName("RabbitMq")
                                        .removalPolicy(RemovalPolicy.DESTROY)
                                        .build())
                                .streamPrefix("rabbitmq")
                                .build()))
                        .environment(Map.of(
                                "RABBITMQ_DEFAULT_USER", "guest",
                                "RABBITMQ_DEFAULT_PASS", "guest"
                        ))
                        .build())
                .memoryLimitMiB(1024)
                .publicLoadBalancer(true)
                .build();

        rabbitmqService.getTaskDefinition().getDefaultContainer().addPortMappings(
                PortMapping.builder()
                        .containerPort(amqpPort)
                        .protocol(Protocol.TCP)
                        .build());

        rabbitmqService.getTargetGroup().configureHealthCheck(HealthCheck.builder()
                .path("/") // A interface HTTP do RabbitMQ deve responder nesta rota
                .port(String.valueOf(managementPort))
                .healthyHttpCodes("200")
                .build());

        CfnOutput.Builder.create(this, "rabbit-mqp-management-url")
                .exportName("rabbit-mqp-management-url")
                .value("http://" + rabbitmqService.getLoadBalancer().getLoadBalancerDnsName())
                .build();
    }
}
