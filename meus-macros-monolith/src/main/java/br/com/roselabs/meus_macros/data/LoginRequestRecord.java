package br.com.roselabs.meus_macros.data;

import java.io.Serializable;

public record LoginRequestRecord(String email, String password) implements Serializable {
}
