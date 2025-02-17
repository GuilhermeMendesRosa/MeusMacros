package com.myorg;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.applicationautoscaling.EnableScalingProps;
import software.amazon.awscdk.services.ecs.*;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.amazon.awscdk.services.elasticloadbalancingv2.HealthCheck;
import software.amazon.awscdk.services.logs.LogGroup;
import software.constructs.Construct;

public class MeusMacrosDiscoveryServiceStack extends Stack {
    private final String dns;
    private final int port;

    public String getDns() {
        return dns;
    }

    public int getPort() {
        return port;
    }

    public MeusMacrosDiscoveryServiceStack(final Construct scope, final String id, final Cluster cluster) {
        this(scope, id, null, cluster);
    }

    public MeusMacrosDiscoveryServiceStack(final Construct scope, final String id, final StackProps props, final Cluster cluster) {
        super(scope, id, props);

        this.port = 8081;

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

        this.dns = discovery.getLoadBalancer().getLoadBalancerDnsName();
    }
}
