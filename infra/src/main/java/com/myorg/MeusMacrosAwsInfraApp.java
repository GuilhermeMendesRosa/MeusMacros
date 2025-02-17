package com.myorg;

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
        
        app.synth();
    }
}

