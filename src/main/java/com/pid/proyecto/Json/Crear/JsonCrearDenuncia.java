package com.pid.proyecto.Json.Crear;

import javax.validation.constraints.NotNull;

public class JsonCrearDenuncia {

    @NotNull(message = "DEBE INTRODUCIR UNA DESCRIPCION")
    private String descripcion = "";
    
    @NotNull(message = "DEBE ESPECIFICAR A QUIEN ESTÁ DENUNCIANDO")
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
