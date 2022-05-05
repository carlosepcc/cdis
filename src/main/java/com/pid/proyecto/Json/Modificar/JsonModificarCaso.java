package com.pid.proyecto.Json.Modificar;

public class JsonModificarCaso {

    private int idDenuncia = -1;

    private int idComision = -1;

    private boolean abierto = true;

    private int diaExp = -1;

    private int mesExp = -1;

    private int anoExp = -1;

    public JsonModificarCaso() {
    }

    public int getIdComision() {
        return idComision;
    }

    public void setIdComision(int idComision) {
        this.idComision = idComision;
    }

    public boolean isAbierto() {
        return abierto;
    }

    public void setAbierto(boolean abierto) {
        this.abierto = abierto;
    }

    public int getIdDenuncia() {
        return idDenuncia;
    }

    public void setIdDenuncia(int idDenuncia) {
        this.idDenuncia = idDenuncia;
    }

    public int getDiaExp() {
        return diaExp;
    }

    public void setDiaExp(int diaExp) {
        this.diaExp = diaExp;
    }

    public int getMesExp() {
        return mesExp;
    }

    public void setMesExp(int mesExp) {
        this.mesExp = mesExp;
    }

    public int getAnoExp() {
        return anoExp;
    }

    public void setAnoExp(int anoExp) {
        this.anoExp = anoExp;
    }

}
