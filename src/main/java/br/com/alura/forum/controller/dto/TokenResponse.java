package br.com.alura.forum.controller.dto;

public class TokenResponse {

    private String tokenString;
    private String tipo;

    public TokenResponse() {}

    public TokenResponse(String tokenString, String tipo) {
        this.tokenString = tokenString;
        this.tipo = tipo;
    }

    public String getTokenString() {
        return tokenString;
    }

    public String getTipo() {
        return tipo;
    }

}
