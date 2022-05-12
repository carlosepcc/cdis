package com.pid.proyecto.Json.Crear;

import com.pid.proyecto.entity.DeclaracionPK;

public class JsonCrearExpediente {

    private DeclaracionPK declaracionPK = new DeclaracionPK();
    private String descripcion = "";

    public JsonCrearExpediente() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public DeclaracionPK getDeclaracionPK() {
        return declaracionPK;
    }

    public void setDeclaracionPK(DeclaracionPK declaracionPK) {
        this.declaracionPK = declaracionPK;
    }

}
