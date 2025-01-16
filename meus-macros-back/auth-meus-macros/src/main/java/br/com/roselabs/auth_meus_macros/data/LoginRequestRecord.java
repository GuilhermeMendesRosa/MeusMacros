package br.com.roselabs.auth_meus_macros.data;

import java.io.Serializable;

public record LoginRequestRecord(String email, String password) implements Serializable {
}
