package br.com.roselabs.gateway_meus_macros.aws;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getTaskARN() {
        return taskARN;
    }

    public void setTaskARN(String taskARN) {
        this.taskARN = taskARN;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
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

    public Container[] getContainers() {
        return containers;
    }

    public void setContainers(Container[] containers) {
        this.containers = containers;
    }

    public Limits getLimits() {
        return limits;
    }

    public void setLimits(Limits limits) {
        this.limits = limits;
    }

    public String getPullStartedAt() {
        return pullStartedAt;
    }

    public void setPullStartedAt(String pullStartedAt) {
        this.pullStartedAt = pullStartedAt;
    }

    public String getPullStoppedAt() {
        return pullStoppedAt;
    }

    public void setPullStoppedAt(String pullStoppedAt) {
        this.pullStoppedAt = pullStoppedAt;
    }

    public String getAvailabilityZone() {
        return availabilityZone;
    }

    public void setAvailabilityZone(String availabilityZone) {
        this.availabilityZone = availabilityZone;
    }
}