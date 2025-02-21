package br.com.roselabs.gateway_meus_macros.aws;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public String getDockerID() {
        return dockerID;
    }

    public void setDockerID(String dockerID) {
        this.dockerID = dockerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDockerName() {
        return dockerName;
    }

    public void setDockerName(String dockerName) {
        this.dockerName = dockerName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public Labels getLabels() {
        return labels;
    }

    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    public String getDesiredStatus() {
        return desiredStatus;
    }

    public void setDesiredStatus(String desiredStatus) {
        this.desiredStatus = desiredStatus;
    }

    public String getKnownStatus() {
        return knownStatus;
    }

    public void setKnownStatus(String knownStatus) {
        this.knownStatus = knownStatus;
    }

    public Limits getLimits() {
        return limits;
    }

    public void setLimits(Limits limits) {
        this.limits = limits;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Network[] getNetworks() {
        return networks;
    }

    public void setNetworks(Network[] networks) {
        this.networks = networks;
    }

    public Health getHealth() {
        return health;
    }

    public void setHealth(Health health) {
        this.health = health;
    }

    public Volumes[] getVolumes() {
        return volumes;
    }

    public void setVolumes(Volumes[] volumes) {
        this.volumes = volumes;
    }
}