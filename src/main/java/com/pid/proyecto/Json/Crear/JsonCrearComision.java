package com.pid.proyecto.Json.Crear;

import com.pid.proyecto.auxiliares.UsuarioRol;
import java.util.LinkedList;
import java.util.List;

public class JsonCrearComision {

    private int idResolucion = -1;

    private List<UsuarioRol> IntegrantesComision = new LinkedList<>();

    public JsonCrearComision() {
    }

    public int getIdResolucion() {
        return idResolucion;
    }

    public void setIdResolucion(int idResolucion) {
        this.idResolucion = idResolucion;
    }

    public List<UsuarioRol> getIntegrantesComision() {
        return IntegrantesComision;
    }

    public void setIntegrantesComision(List<UsuarioRol> IntegrantesComision) {
        this.IntegrantesComision = IntegrantesComision;
    }

}
