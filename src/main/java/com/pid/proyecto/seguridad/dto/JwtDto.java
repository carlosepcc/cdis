package com.pid.proyecto.seguridad.dto;

public class JwtDto {

    // solo vamos a enviar el token
    private String token;

    //constructor vacio
    public JwtDto() {
    }

    //constructor con campos
    public JwtDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
