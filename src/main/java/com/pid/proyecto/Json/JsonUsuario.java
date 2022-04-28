package com.pid.proyecto.Json;

public class JsonUsuario {
//  @NotBlank(message = "DEBES ESPECIFICAR EL NOMBRE")
//  @Pattern(
//    regexp = "([A-Z|Á|É|Í|Ó|Ú|Ñ][a-z|á|é|í|ó|ú|ñ|ű]*)|([A-Z|Á|É|Í|Ó|Ú|Ñ][a-z|á|é|í|ó|ú|ñ|ű]* [A-Z|Á|É|Í|Ó|Ú|Ñ][a-z|á|é|í|ó|ú|ñ|ű]*)",
//    message = "FORMATO DE NOMBRE INCORRECTO, DEBE COMENZAR CON MAYÚSCULA SEGUIDO DE MINÚSCULAS Y SOLO ADMITE DOS ENTRADAS"
//  )

    private String rol = "";
    private String nombre = "";
    private String usuario = "";
    private String contrasena = "";
    private String cargo = "";

    public JsonUsuario() {
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

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
