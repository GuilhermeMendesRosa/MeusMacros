package br.com.roselabs.auth_meus_macros.data;

public record RegisterRequestRecord(
        String email,
        String password,
        String firstName,
        String lastName
) {}
