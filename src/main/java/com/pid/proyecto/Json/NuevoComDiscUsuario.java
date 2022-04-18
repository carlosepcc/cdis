package com.pid.proyecto.Json;

import javax.validation.constraints.NotBlank;

public class NuevoComDiscUsuario {

    @NotBlank
    private int idComision;

    @NotBlank
    private int idUsuario;

    @NotBlank
    private String rol;


    public NuevoComDiscUsuario() {

    }

    public int getIdComision() {
        return idComision;
    }

    public void setIdComision(int idComision) {
        this.idComision = idComision;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
