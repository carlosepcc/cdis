package com.pid.proyecto.Json.Modificar;


public class JsonModificarDenuncia {

    private int idDenuncia = -1;

    private String descripcion = "";

    private String acusado = "";

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

    public String getAcusado() {
        return acusado;
    }

    public void setAcusado(String acusado) {
        this.acusado = acusado;
    }

}
