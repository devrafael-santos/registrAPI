package com.raffasdev.registrapi.application.gateways;

public interface TokenGenerator {

    String generateToken(String email);
}
