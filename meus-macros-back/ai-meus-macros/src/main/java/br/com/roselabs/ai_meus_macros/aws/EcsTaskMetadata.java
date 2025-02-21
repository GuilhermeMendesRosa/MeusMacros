package br.com.roselabs.ai_meus_macros.aws;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EcsTaskMetadata {

    @JsonProperty("Cluster")
    private String cluster;

    @JsonProperty("TaskARN")
    private String taskARN;

    @JsonProperty("Family")
    private String family;

    @JsonProperty("Revision")
    private String revision;

    @JsonProperty("DesiredStatus")
    private String desiredStatus;

    @JsonProperty("KnownStatus")
    private String knownStatus;

    @JsonProperty("Containers")
    private Container[] containers;

    @JsonProperty("Limits")
    private Limits limits;

    @JsonProperty("PullStartedAt")
    private String pullStartedAt;

    @JsonProperty("PullStoppedAt")
    private String pullStoppedAt;

    @JsonProperty("AvailabilityZone")
    private String availabilityZone;

}