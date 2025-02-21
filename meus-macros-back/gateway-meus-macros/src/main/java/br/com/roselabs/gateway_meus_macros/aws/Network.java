package br.com.roselabs.gateway_meus_macros.aws;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Network {

    @JsonProperty("NetworkMode")
    private String networkMode;

    @JsonProperty("IPv4Addresses")
    private String[] iPv4Addresses;

    public String getNetworkMode() {
        return networkMode;
    }

    public void setNetworkMode(String networkMode) {
        this.networkMode = networkMode;
    }

    public String[] getIPv4Addresses() {
        return iPv4Addresses;
    }

    public void setIPv4Addresses(String[] iPv4Addresses) {
        this.iPv4Addresses = iPv4Addresses;
    }
}