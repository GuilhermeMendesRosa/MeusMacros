package br.com.roselabs.ai_meus_macros.aws;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Limits {

    @JsonProperty("CPU")
    private double cpu;

    @JsonProperty("Memory")
    private long memory;

}