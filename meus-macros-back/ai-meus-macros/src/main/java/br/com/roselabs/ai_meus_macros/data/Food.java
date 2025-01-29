package br.com.roselabs.ai_meus_macros.data;

import lombok.Data;

import java.util.List;

@Data
public class Food {

    private String name;
    private String unit;
    private Integer portions;
    private Boolean isGenericFood;
    private List<Double> embedding;

}
