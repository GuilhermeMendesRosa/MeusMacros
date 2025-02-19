package br.com.roselabs;

import br.com.roselabs.stacks.MeusMacrosClusterStack;
import br.com.roselabs.stacks.MeusMacrosVpcStack;
import br.com.roselabs.stacks.rds.MeusMacrosAuthRdsStack;
import br.com.roselabs.stacks.rds.MeusMacrosMacrosCalculatorRdsStack;
import br.com.roselabs.stacks.services.*;
import software.amazon.awscdk.App;

public class MeusMacrosAwsInfraApp {
    public static void main(final String[] args) {
        App app = new App();

        MeusMacrosVpcStack vpcStack = new MeusMacrosVpcStack(app, "Vpc");
        MeusMacrosClusterStack clusterStack = new MeusMacrosClusterStack(app, "Cluster", vpcStack.getVpc());
        clusterStack.addDependency(vpcStack);

        MeusMacrosDiscoveryServiceStack discovery = new MeusMacrosDiscoveryServiceStack(app, "Discovery", clusterStack.getCluster());
        discovery.addDependency(clusterStack);

        MeusMacrosGatewayServiceStack gateway = new MeusMacrosGatewayServiceStack(app, "Gateway", clusterStack.getCluster());
        gateway.addDependency(clusterStack);
        gateway.addDependency(discovery);

        MeusMacrosAIServiceStack ai = new MeusMacrosAIServiceStack(app, "AIService", clusterStack.getCluster());
        ai.addDependency(clusterStack);
        ai.addDependency(discovery);

        MeusMacrosAuthRdsStack authRds = new MeusMacrosAuthRdsStack(app, "AuthRds", vpcStack.getVpc());
        authRds.addDependency(vpcStack);

        RabbitMQStack rabbitMQ = new RabbitMQStack(app, "RabbitMQ", clusterStack.getCluster());
        rabbitMQ.addDependency(clusterStack);

        MeusMacrosMacrosCalculatorRdsStack macrosCalculatorRds = new MeusMacrosMacrosCalculatorRdsStack(app, "MacrosCalculatorRds", vpcStack.getVpc());
        macrosCalculatorRds.addDependency(vpcStack);

        MeusMacrosAuthServiceStack auth = new MeusMacrosAuthServiceStack(app, "AuthService", clusterStack.getCluster());
        auth.addDependency(clusterStack);
        auth.addDependency(discovery);
        auth.addDependency(authRds);
        auth.addDependency(rabbitMQ);

        MeusMacrosMacrosCalculatorServiceStack macrosCalculatorServiceStack = new MeusMacrosMacrosCalculatorServiceStack(app, "MacrosCalculatorService", clusterStack.getCluster());
        macrosCalculatorServiceStack.addDependency(clusterStack);
        macrosCalculatorServiceStack.addDependency(discovery);
        macrosCalculatorServiceStack.addDependency(macrosCalculatorRds);

        MeusMacrosEmailServiceStack email = new MeusMacrosEmailServiceStack(app, "EmailService", clusterStack.getCluster());
        email.addDependency(clusterStack);
        email.addDependency(discovery);
        email.addDependency(rabbitMQ);

        app.synth();
    }
}

