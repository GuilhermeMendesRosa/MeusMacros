package br.com.roselabs.gateway_meus_macros.aws;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Labels {

    @JsonProperty("com.amazonaws.ecs.cluster")
    private String comAmazonawsEcsCluster;

    @JsonProperty("com.amazonaws.ecs.container-name")
    private String comAmazonawsEcsContainerName;

    @JsonProperty("com.amazonaws.ecs.task-arn")
    private String comAmazonawsEcsTaskArn;

    @JsonProperty("com.amazonaws.ecs.task-definition-family")
    private String comAmazonawsEcsTaskDefinitionFamily;

    @JsonProperty("com.amazonaws.ecs.task-definition-version")
    private String comAmazonawsEcsTaskDefinitionVersion;

    public String getComAmazonawsEcsCluster() {
        return comAmazonawsEcsCluster;
    }

    public void setComAmazonawsEcsCluster(String comAmazonawsEcsCluster) {
        this.comAmazonawsEcsCluster = comAmazonawsEcsCluster;
    }

    public String getComAmazonawsEcsContainerName() {
        return comAmazonawsEcsContainerName;
    }

    public void setComAmazonawsEcsContainerName(String comAmazonawsEcsContainerName) {
        this.comAmazonawsEcsContainerName = comAmazonawsEcsContainerName;
    }

    public String getComAmazonawsEcsTaskArn() {
        return comAmazonawsEcsTaskArn;
    }

    public void setComAmazonawsEcsTaskArn(String comAmazonawsEcsTaskArn) {
        this.comAmazonawsEcsTaskArn = comAmazonawsEcsTaskArn;
    }

    public String getComAmazonawsEcsTaskDefinitionFamily() {
        return comAmazonawsEcsTaskDefinitionFamily;
    }

    public void setComAmazonawsEcsTaskDefinitionFamily(String comAmazonawsEcsTaskDefinitionFamily) {
        this.comAmazonawsEcsTaskDefinitionFamily = comAmazonawsEcsTaskDefinitionFamily;
    }

    public String getComAmazonawsEcsTaskDefinitionVersion() {
        return comAmazonawsEcsTaskDefinitionVersion;
    }

    public void setComAmazonawsEcsTaskDefinitionVersion(String comAmazonawsEcsTaskDefinitionVersion) {
        this.comAmazonawsEcsTaskDefinitionVersion = comAmazonawsEcsTaskDefinitionVersion;
    }
}