package br.com.roselabs.ai_meus_macros.aws;

import lombok.Data;

@Data
public class Health {

    private String status;

    private String statusSince;

    private String output;
}