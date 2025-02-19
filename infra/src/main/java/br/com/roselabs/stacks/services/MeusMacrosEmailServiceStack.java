package br.com.roselabs.stacks.services;

import software.amazon.awscdk.*;
import software.amazon.awscdk.services.applicationautoscaling.EnableScalingProps;
import software.amazon.awscdk.services.ecs.*;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck;
import software.amazon.awscdk.services.logs.LogGroup;
import software.constructs.Construct;

import java.util.HashMap;
import java.util.Map;

public class MeusMacrosEmailServiceStack extends Stack {
    public MeusMacrosEmailServiceStack(final Construct scope, final String id, final Cluster cluster) {
        this(scope, id, null, cluster);
    }

    public MeusMacrosEmailServiceStack(final Construct scope, final String id, final StackProps props, final Cluster cluster) {
        super(scope, id, props);
        Map<String, String> autenticacao = new HashMap<>();
        autenticacao.put("EUREKA_SERVER_URL", Fn.importValue("eureka-server-url"));
        autenticacao.put("RABBITMQ_URL", Fn.importValue("rabbit-mqp-management-url"));

        ApplicationLoadBalancedFargateService email = ApplicationLoadBalancedFargateService.Builder.create(this, "MeusMacrosService")
                .serviceName("EmailMeusMacros")
                .cluster(cluster)           // Required
                .cpu(512)         // Default is 1
                .listenerPort(8086)               // Default is 256
                .desiredCount(1)            // Default is 1
                .assignPublicIp(true)
                .taskImageOptions(
                        ApplicationLoadBalancedTaskImageOptions.builder()
                                .containerName("email-meus-macros")
                                .containerPort(8086)
                                .image(ContainerImage.fromRegistry("guilhermemendesrosa/email-meus-macros:latest"))
                                .environment(autenticacao)
                                .logDriver(LogDriver.awsLogs(AwsLogDriverProps.builder()
                                        .logGroup(LogGroup.Builder
                                                .create(this, "EmailMeusMacrosLogGroup")
                                                .logGroupName("EmailMeusMacros")
                                                .removalPolicy(RemovalPolicy.DESTROY)
                                                .build())
                                        .streamPrefix("email-meus-macros")
                                        .build()))
                                .build())
                .memoryLimitMiB(1024)       // Default is 512
                .publicLoadBalancer(true)   // Default is false
                .build();

        email.getTargetGroup().configureHealthCheck(HealthCheck.builder()
                .path("/actuator/health")
                .port("8086")
                .healthyHttpCodes("200")
                .build());

        ScalableTaskCount scalableTaskCount = email.getService().autoScaleTaskCount(EnableScalingProps.builder()
                .minCapacity(1)
                .maxCapacity(1)
                .build());

        scalableTaskCount.scaleOnCpuUtilization("EmailMeusMacrosAutoScaling", CpuUtilizationScalingProps.builder()
                .targetUtilizationPercent(50)
                .scaleInCooldown(Duration.seconds(60))
                .scaleOutCooldown(Duration.seconds(60))
                .build());
    }
}