package com.pid.proyecto.Json.Crear;

import java.util.LinkedList;
import java.util.List;
import javax.validation.constraints.NotNull;

public class JsonCrearDenuncia {

    @NotNull(message = "DEBE INTRODUCIR UNA DESCRIPCION")
    private String descripcion = "";
    
    @NotNull(message = "DEBE ESPECIFICAR A QUIEN ESTÁ DENUNCIANDO")
    private List<String> acusado = new LinkedList<>();

    public JsonCrearDenuncia() {
    }

    public List<String> getAcusados() {
        return acusado;
    }

    public void setAcusados(List<String> acusado) {
        this.acusado = acusado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
