package br.com.roselabs.ai_meus_macros.aws;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Network {

    @JsonProperty("NetworkMode")
    private String networkMode;

    @JsonProperty("IPv4Addresses")
    private String[] iPv4Addresses;

}