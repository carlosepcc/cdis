package com.pid.proyecto.Json.ModificarEntidad;

public class ModificarPdfExpediente {

    private String descripcion;

    private int idDeclaracion;
    
    public ModificarPdfExpediente() {
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
