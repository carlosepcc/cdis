package com.pid.proyecto.Json.CrearEntidad;

import javax.validation.constraints.NotBlank;

public class NuevoPdfExpediente {

    @NotBlank(message = "Debe introducir una descripcion")
    private String descripcion;

    private int idDeclaracion;

    public NuevoPdfExpediente() {
    }

    public NuevoPdfExpediente(int idDeclaracion, String descripcion) {
        this.idDeclaracion = idDeclaracion;
        this.descripcion = descripcion;
    }

    public int getIdDeclaracion() {
        return idDeclaracion;
    }

    public void setIdDeclaracion(int idDeclaracion) {
        this.idDeclaracion = idDeclaracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
