package com.pid.proyecto.Json;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;

public class NuevoExpediente {

    @NotBlank
    private String descripcion;
    @NotBlank
    private Date fecha;
    @NotBlank
    private int idCaso;
    @NotBlank
    private String usuario;

    public NuevoExpediente() {
    }

    public int getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(int idCaso) {
        this.idCaso = idCaso;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
