package com.pid.proyecto.Json.ModificarEntidad;

import javax.validation.constraints.NotBlank;

public class ModificarComDiscUsuario {

    @NotBlank
    private int idComision;

    @NotBlank
    private int idUsuario;

    @NotBlank
    private String rol;


    public ModificarComDiscUsuario() {

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
