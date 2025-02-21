package br.com.roselabs.gateway_meus_macros.aws;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Volumes {

    @JsonProperty("DockerName")
    private String dockerName;

    @JsonProperty("Source")
    private String source;

    @JsonProperty("Destination")
    private String destination;

    public String getDockerName() {
        return dockerName;
    }

    public void setDockerName(String dockerName) {
        this.dockerName = dockerName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}