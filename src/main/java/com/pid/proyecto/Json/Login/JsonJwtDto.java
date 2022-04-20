package com.pid.proyecto.Json.Login;

public class JsonJwtDto {

    // solo vamos a enviar el token
    private String token;

    //constructor vacio
    public JsonJwtDto() {
    }

    //constructor con campos
    public JsonJwtDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
