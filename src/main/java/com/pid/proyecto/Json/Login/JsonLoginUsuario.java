package com.pid.proyecto.Json.Login;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class JsonLoginUsuario {

    @NotBlank(message = "INTRODUZCA SU USUARIO")
    @Pattern(regexp = "[a-z]*", message = "FORMATO DE USUARIO INCORRECTO")
    private String usuario;
    @NotBlank(message = "INTRODUZCA SU CONTRASEÑA")
    private String contrasena;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

}
