package br.com.roselabs.gateway_meus_macros.aws;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Limits {

    @JsonProperty("CPU")
    private double cpu;

    @JsonProperty("Memory")
    private long memory;

    public double getCpu() {
        return cpu;
    }

    public void setCpu(double cpu) {
        this.cpu = cpu;
    }

    public long getMemory() {
        return memory;
    }

    public void setMemory(long memory) {
        this.memory = memory;
    }
}