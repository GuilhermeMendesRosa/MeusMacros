package br.com.roselabs.ai_meus_macros.aws;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
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
}