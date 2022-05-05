package com.pid.proyecto.Json.Crear;

import java.time.LocalDate;

public class JsonCrearDenuncia {

    private String descripcion = "";

    private String acusado = "";

    public JsonCrearDenuncia() {
    }

    public String getAcusado() {
        return acusado;
    }

    public void setAcusado(String acusado) {
        this.acusado = acusado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
