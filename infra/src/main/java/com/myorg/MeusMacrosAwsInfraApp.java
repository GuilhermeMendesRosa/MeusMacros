package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;

import java.util.Arrays;

public class MeusMacrosAwsInfraApp {
    public static void main(final String[] args) {
        App app = new App();

        MeusMacrosVpcStack vpcStack = new MeusMacrosVpcStack(app, "Vpc");
        MeusMacrosClusterStack clusterStack = new MeusMacrosClusterStack(app, "Cluster", vpcStack.getVpc());
        clusterStack.addDependency(vpcStack);

        MeusMacrosServiceStack MeusMacrosServiceStack = new MeusMacrosServiceStack(app, "Service", clusterStack.getCluster());
        MeusMacrosServiceStack.addDependency(clusterStack);
        app.synth();
    }
}

