package com.pid.proyecto.Json.Modificar;

import org.springframework.stereotype.Component;

@Component
public class JsonModificarDeclaracion {

    private int idDenuncia = -1;

    private int idComision = -1;

    private String usuario = "";

    private boolean abierta = false;

    public JsonModificarDeclaracion() {
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

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isAbierta() {
        return abierta;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }

}
