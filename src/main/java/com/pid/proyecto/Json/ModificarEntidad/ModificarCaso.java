package com.pid.proyecto.Json.ModificarEntidad;

import java.util.Date;
import javax.validation.constraints.NotBlank;

public class ModificarCaso {

    private boolean abierto;
    @NotBlank
    private int idDenuncia;
    @NotBlank
    private int idComision;
    @NotBlank
    private Date fechaExpiracion;

    public ModificarCaso(int idDenuncia, int idComision, boolean abierto, Date fechaExpiracion) {
        this.idDenuncia = idDenuncia;
        this.idComision = idComision;
        this.abierto = abierto;
        this.fechaExpiracion = fechaExpiracion;
    }

    public ModificarCaso() {
    }

    public Date getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(Date fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public int getIdComision() {
        return idComision;
    }

    public void setIdComision(int idComision) {
        this.idComision = idComision;
    }

    public int getIdDenuncia() {
        return idDenuncia;
    }

    public void setIdDenuncia(int idDenuncia) {
        this.idDenuncia = idDenuncia;
    }

    public boolean isAbierto() {
        return abierto;
    }

    public void setAbierto(boolean abierto) {
        this.abierto = abierto;
    }

}
