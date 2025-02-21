package br.com.roselabs.ai_meus_macros.aws;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Container {

    @JsonProperty("DockerId")
    private String dockerID;

    @JsonProperty("Name")
    private String name;

    @JsonProperty("DockerName")
    private String dockerName;

    @JsonProperty("Image")
    private String image;

    @JsonProperty("ImageID")
    private String imageID;

    @JsonProperty("Labels")
    private Labels labels;

    @JsonProperty("DesiredStatus")
    private String desiredStatus;

    @JsonProperty("KnownStatus")
    private String knownStatus;

    @JsonProperty("Limits")
    private Limits limits;

    @JsonProperty("CreatedAt")
    private String createdAt;

    @JsonProperty("StartedAt")
    private String startedAt;

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Networks")
    private Network[] networks;

    @JsonProperty("Health")
    private Health health;

    @JsonProperty("Volumes")
    private Volumes[] volumes;


}