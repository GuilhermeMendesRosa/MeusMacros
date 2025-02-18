package br.com.roselabs.stacks;

import software.amazon.awscdk.*;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.rds.*;
import software.constructs.Construct;

import java.util.Collections;

public class MeusMacrosAuthRdsStack extends Stack {
    public MeusMacrosAuthRdsStack(final Construct scope, final String id, final Vpc vpc) {
        this(scope, id, null, vpc);
    }

    public MeusMacrosAuthRdsStack(final Construct scope, final String id, final StackProps props, final Vpc vpc) {
        super(scope, id, props);

        CfnParameter senha = CfnParameter.Builder.create(this, "senha")
                .type("String")
                .description("Senha meus-macros-auth")
                .build();

        // Obtém o grupo de segurança padrão da VPC e altera a porta para PostgreSQL (5432)
        ISecurityGroup iSecurityGroup = SecurityGroup.fromSecurityGroupId(this, id, vpc.getVpcDefaultSecurityGroup());
        iSecurityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(5432));

        DatabaseInstance database = DatabaseInstance.Builder
                .create(this, "Rds-pedidos")
                .instanceIdentifier("meus-macros-auth-db")
                .engine(DatabaseInstanceEngine.postgres(PostgresInstanceEngineProps.builder()
                        .version(PostgresEngineVersion.VER_16)
                        .build()))
                .vpc(vpc)
                .credentials(Credentials.fromUsername("postgres",
                        CredentialsFromUsernameOptions.builder()
                                .password(SecretValue.unsafePlainText(senha.getValueAsString()))
                                .build()))
                .instanceType(InstanceType.of(InstanceClass.BURSTABLE2, InstanceSize.MICRO))
                .multiAz(false)
                .allocatedStorage(10)
                .securityGroups(Collections.singletonList(iSecurityGroup))
                .vpcSubnets(SubnetSelection.builder()
                        .subnets(vpc.getPrivateSubnets())
                        .build())
                .build();

        CfnOutput.Builder.create(this, "meus-macros-auth-db-endpoint")
                .exportName("meus-macros-auth-db-endpoint")
                .value(database.getDbInstanceEndpointAddress())
                .build();

        CfnOutput.Builder.create(this, "meus-macros-auth-db-senha")
                .exportName("meus-macros-auth-db-senha")
                .value(senha.getValueAsString())
                .build();
    }
}
