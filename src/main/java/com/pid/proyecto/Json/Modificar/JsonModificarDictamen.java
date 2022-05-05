package com.pid.proyecto.Json.Modificar;

public class JsonModificarDictamen {
    private int idDenuncia = -1;

    private int idComision = -1;

    private String Descripcion = "";

    public JsonModificarDictamen() {
    }

    public int getIdDenuncia() {
        return idDenuncia;
    }

    public void setIdDenuncia(int idDenuncia) {
        this.idDenuncia = idDenuncia;
    }

    public int getIdComision() {
        return idComision;
    }

    public void setIdComision(int idComision) {
        this.idComision = idComision;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
}
