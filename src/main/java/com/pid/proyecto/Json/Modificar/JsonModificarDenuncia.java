package com.pid.proyecto.Json.Modificar;

import java.util.LinkedList;
import java.util.List;

public class JsonModificarDenuncia {

    private int idDenuncia = -1;

    private String descripcion = "";

    private List<String> acusados = new LinkedList<>();

    public JsonModificarDenuncia() {
    }

    public int getIdDenuncia() {
        return idDenuncia;
    }

    public void setIdDenuncia(int idDenuncia) {
        this.idDenuncia = idDenuncia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getAcusados() {
        return acusados;
    }

    public void setAcusados(List<String> acusados) {
        this.acusados = acusados;
    }

}
