package br.com.roselabs.meus_macros.data;

public record RegisterRequestRecord(
        String email,
        String password,
        String firstName,
        String lastName
) {}
