package com.pid.proyecto.Json.Crear;

public class JsonCrearDeclaracion {

    private int idDenuncia = -1;

    private int idComision = -1;

    private String usuario = "";

    public JsonCrearDeclaracion() {
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String Usuario) {
        this.usuario = Usuario;
    }

}
