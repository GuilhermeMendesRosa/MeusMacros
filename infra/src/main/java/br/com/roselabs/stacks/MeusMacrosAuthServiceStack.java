package br.com.roselabs.stacks;

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

public class MeusMacrosAuthServiceStack extends Stack {
    public MeusMacrosAuthServiceStack(final Construct scope, final String id, final Cluster cluster) {
        this(scope, id, null, cluster);
    }

    public MeusMacrosAuthServiceStack(final Construct scope, final String id, final StackProps props, final Cluster cluster) {
        super(scope, id, props);
        Map<String, String> autenticacao = new HashMap<>();
        autenticacao.put("SPRING_DATASOURCE_URL", "jdbc:postgresql://" + Fn.importValue("meus-macros-auth-db-endpoint") + ":5432/auth_meus_macros?createDatabaseIfNotExist=true");
        autenticacao.put("SPRING_DATASOURCE_USERNAME", "postgres");
        autenticacao.put("SPRING_DATASOURCE_PASSWORD", Fn.importValue("meus-macros-auth-db-senha"));
        autenticacao.put("EUREKA_SERVER_URL", Fn.importValue("eureka-server-url"));

        ApplicationLoadBalancedFargateService auth = ApplicationLoadBalancedFargateService.Builder.create(this, "MeusMacrosService")
                .serviceName("AuthMeusMacros")
                .cluster(cluster)           // Required
                .cpu(512)                   // Default is 256
                .desiredCount(1)            // Default is 1
                .listenerPort(8082)
                .assignPublicIp(true)
                .taskImageOptions(
                        ApplicationLoadBalancedTaskImageOptions.builder()
                                .containerName("auth-meus-macros")
                                .image(ContainerImage.fromRegistry("guilhermemendesrosa/auth-meus-macros:latest"))
                                .environment(autenticacao)
                                .logDriver(LogDriver.awsLogs(AwsLogDriverProps.builder()
                                        .logGroup(LogGroup.Builder
                                                .create(this, "AuthMeusMacrosLogGroup")
                                                .logGroupName("AuthMeusMacros")
                                                .removalPolicy(RemovalPolicy.DESTROY)
                                                .build())
                                        .streamPrefix("auth-meus-macros")
                                        .build()))
                                .build())
                .memoryLimitMiB(1024)       // Default is 512
                .publicLoadBalancer(true)   // Default is false
                .build();

        auth.getTargetGroup().configureHealthCheck(HealthCheck.builder()
                .path("/actuator/health")
                .port("traffic-port")
                .healthyHttpCodes("200")
                .build());

        ScalableTaskCount scalableTaskCount = auth.getService().autoScaleTaskCount(EnableScalingProps.builder()
                .minCapacity(2)
                .maxCapacity(4)
                .build());

        scalableTaskCount.scaleOnCpuUtilization("AuthMeusMacrosAutoScaling", CpuUtilizationScalingProps.builder()
                .targetUtilizationPercent(50)
                .scaleInCooldown(Duration.seconds(60))
                .scaleOutCooldown(Duration.seconds(60))
                .build());
    }
}
