package com.pid.proyecto.Json.CrearEntidad;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class NuevoUsuario {

    @NotBlank(message = "DEBES ESPECIFICAR EL NOMBRE")
    @Pattern(regexp = "[A-Z][a-z]*" , message = "FORMATO DE NOMBRE INCORRECTO")
    private String nombre;
    
    @NotBlank(message = "DEBES ESPECIFICAR EL APELLIDO")
    @Pattern(regexp = "[A-Z][a-z]*" , message = "FORMATO DE APELLIDO INCORRECTO")
    private String apellidos;
    
    @NotBlank(message = "DEBES ESPECIFICAR EL USUARIO")
    @Pattern(regexp = "[a-z]*" , message = "FORMATO DE USUARIO INCORRECTO")
    private String usuario;
    
    @Size(min = 4, max = 8, message = "LA CONTRASEÑA NO DEBE CONTENER DE 4 - 8 CARACTERES")
    @NotBlank(message = "DEBES ESPECIFICAR UNA CONTRASEÑA")
    private String contrasena;

    @NotBlank(message = "DEBES ESPECIFICAR UN ROL")
    private String rol;

    public NuevoUsuario() {
    }

    public NuevoUsuario(String nombre, String apellidos, String usuario, String contrasena, String rol) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
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
