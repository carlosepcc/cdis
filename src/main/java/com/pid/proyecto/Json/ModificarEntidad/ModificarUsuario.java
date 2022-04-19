package com.pid.proyecto.Json.ModificarEntidad;

public class ModificarUsuario {

    //@Pattern(regexp = "[A-Z][a-z]*", message = "FORMATO DE NOMBRE INCORRECTO")
    private String nombre = "";

    //@Pattern(regexp = "[A-Z][a-z]*", message = "FORMATO DE APELLIDO INCORRECTO")
    private String apellidos = "";

    //@Pattern(regexp = "[a-z]*", message = "FORMATO DE USUARIO INCORRECTO")
    private String usuario = "";

    //@Size(min = 4, max = 8, message = "LA CONTRASEÑA DEBE CONTENER DE 4 - 8 CARACTERES")
    private String contrasena = "";
    
    private String rol = "";

    public ModificarUsuario() {
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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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
