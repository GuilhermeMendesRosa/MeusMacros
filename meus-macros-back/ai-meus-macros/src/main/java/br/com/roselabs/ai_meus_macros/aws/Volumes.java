package br.com.roselabs.ai_meus_macros.aws;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Volumes {

    @JsonProperty("DockerName")
    private String dockerName;

    @JsonProperty("Source")
    private String source;

    @JsonProperty("Destination")
    private String destination;
}