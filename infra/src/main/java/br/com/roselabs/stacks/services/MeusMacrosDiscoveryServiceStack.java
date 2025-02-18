package br.com.roselabs.stacks.services;

import software.amazon.awscdk.*;
import software.amazon.awscdk.services.applicationautoscaling.EnableScalingProps;
import software.amazon.awscdk.services.ecs.*;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck;
import software.amazon.awscdk.services.logs.LogGroup;
import software.constructs.Construct;

public class MeusMacrosDiscoveryServiceStack extends Stack {

    public MeusMacrosDiscoveryServiceStack(final Construct scope, final String id, final Cluster cluster) {
        this(scope, id, null, cluster);
    }

    public MeusMacrosDiscoveryServiceStack(final Construct scope, final String id, final StackProps props, final Cluster cluster) {
        super(scope, id, props);

        int port = 8081;
        ApplicationLoadBalancedFargateService discovery = ApplicationLoadBalancedFargateService.Builder.create(this, "MeusMacrosService")
                .serviceName("DiscoveryMeusMacros")
                .cluster(cluster)           // Required
                .cpu(512)                   // Default is 256
                .desiredCount(1)            // Default is 1
                .listenerPort(port)
                .assignPublicIp(true)
                .taskImageOptions(
                        ApplicationLoadBalancedTaskImageOptions.builder()
                                .containerName("discovery-meus-macros")
                                .image(ContainerImage.fromRegistry("guilhermemendesrosa/discovery-meus-macros:latest"))
                                .containerPort(port)
                                .logDriver(LogDriver.awsLogs(AwsLogDriverProps.builder()
                                        .logGroup(LogGroup.Builder
                                                .create(this, "DiscoveryMeusMacrosLogGroup")
                                                .logGroupName("DiscoveryMeusMacros")
                                                .removalPolicy(RemovalPolicy.DESTROY)
                                                .build())
                                        .streamPrefix("discovery-meus-macros")
                                        .build()))
                                .build())
                .memoryLimitMiB(1024)       // Default is 512
                .publicLoadBalancer(true)   // Default is false
                .build();

        discovery.getTargetGroup().configureHealthCheck(HealthCheck.builder()
                .path("/actuator/health")
                .port("8081")
                .healthyHttpCodes("200")
                .build());

        ScalableTaskCount scalableTaskCount = discovery.getService().autoScaleTaskCount(EnableScalingProps.builder()
                .minCapacity(2)
                .maxCapacity(4)
                .build());

        scalableTaskCount.scaleOnCpuUtilization("DiscoveryMeusMacrosAutoScaling", CpuUtilizationScalingProps.builder()
                .targetUtilizationPercent(50)
                .scaleInCooldown(Duration.seconds(60))
                .scaleOutCooldown(Duration.seconds(60))
                .build());

        String eurekaServerUrl = "http://" + discovery.getLoadBalancer().getLoadBalancerDnsName() + ":" + port + "/eureka";

        CfnOutput.Builder.create(this, "eureka-server-url")
                .exportName("eureka-server-url")
                .value(eurekaServerUrl)
                .build();

    }
}
