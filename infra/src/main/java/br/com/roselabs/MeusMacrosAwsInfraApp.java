package br.com.roselabs;

import br.com.roselabs.stacks.*;
import br.com.roselabs.stacks.rds.MeusMacrosAuthRdsStack;
import br.com.roselabs.stacks.services.MeusMacrosAuthServiceStack;
import br.com.roselabs.stacks.MeusMacrosClusterStack;
import br.com.roselabs.stacks.services.MeusMacrosDiscoveryServiceStack;
import br.com.roselabs.stacks.services.MeusMacrosGatewayServiceStack;
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

        MeusMacrosAuthRdsStack authRds = new MeusMacrosAuthRdsStack(app, "AuthRds", vpcStack.getVpc());
        authRds.addDependency(vpcStack);

        MeusMacrosAuthServiceStack auth = new MeusMacrosAuthServiceStack(app, "AuthService", clusterStack.getCluster());
        auth.addDependency(clusterStack);
        auth.addDependency(discovery);
        auth.addDependency(authRds);

        app.synth();
    }
}

