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

public class MeusMacrosMacrosCalculatorServiceStack extends Stack {
    public MeusMacrosMacrosCalculatorServiceStack(final Construct scope, final String id, final Cluster cluster) {
        this(scope, id, null, cluster);
    }

    public MeusMacrosMacrosCalculatorServiceStack(final Construct scope, final String id, final StackProps props, final Cluster cluster) {
        super(scope, id, props);
        Map<String, String> autenticacao = new HashMap<>();
        autenticacao.put("SPRING_DATASOURCE_URL", "jdbc:mysql://" + Fn.importValue("meus-macros-macros-calculator-db-endpoint") + ":3306/macros_calculator_meus_macros?createDatabaseIfNotExist=true");
        autenticacao.put("SPRING_DATASOURCE_USERNAME", "admin");
        autenticacao.put("SPRING_DATASOURCE_PASSWORD", Fn.importValue("meus-macros-macros-calculator-db-senha"));
        autenticacao.put("EUREKA_SERVER_URL", Fn.importValue("eureka-server-url"));

        ApplicationLoadBalancedFargateService macrosCalculator = ApplicationLoadBalancedFargateService.Builder.create(this, "MeusMacrosService")
                .serviceName("MacrosCalculatorMacros")
                .cluster(cluster)           // Required
                .cpu(512)   // Default is 1
                .listenerPort(8084)
                .desiredCount(1)            // Default is 1
                .assignPublicIp(true)
                .taskImageOptions(
                        ApplicationLoadBalancedTaskImageOptions.builder()
                                .containerName("macros-calculator-meus-macros")  // Default is 1
                                .containerPort(8084)
                                .image(ContainerImage.fromRegistry("guilhermemendesrosa/macros-calculator-meus-macros:latest"))
                                .environment(autenticacao)
                                .logDriver(LogDriver.awsLogs(AwsLogDriverProps.builder()
                                        .logGroup(LogGroup.Builder
                                                .create(this, "MacrosCalculatorMacrosLogGroup")
                                                .logGroupName("MacrosCalculatorMacros")
                                                .removalPolicy(RemovalPolicy.DESTROY)
                                                .build())
                                        .streamPrefix("macros-calculator-meus-macros")
                                        .build()))
                                .build())
                .memoryLimitMiB(1024)       // Default is 512
                .publicLoadBalancer(true)   // Default is false
                .build();

        macrosCalculator.getTargetGroup().configureHealthCheck(HealthCheck.builder()
                .path("/actuator/health")
                .port("8084")
                .healthyHttpCodes("200")
                .build());

        ScalableTaskCount scalableTaskCount = macrosCalculator.getService().autoScaleTaskCount(EnableScalingProps.builder()
                .minCapacity(1)
                .maxCapacity(1)
                .build());

        scalableTaskCount.scaleOnCpuUtilization("MacrosCalculatorMacrosAutoScaling", CpuUtilizationScalingProps.builder()
                .targetUtilizationPercent(50)
                .scaleInCooldown(Duration.seconds(60))
                .scaleOutCooldown(Duration.seconds(60))
                .build());
    }
}
