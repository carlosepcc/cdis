package com.pid.proyecto.Json.Crear;

import com.pid.proyecto.auxiliares.UsuarioRol;
import java.util.LinkedList;
import java.util.List;

public class JsonCrearComision {

    private int idResolucion;
    
    private List<UsuarioRol> integrantes = new LinkedList<>();

    public JsonCrearComision() {
    }

    public int getIdResolucion() {
        return idResolucion;
    }

    public void setIdResolucion(int idResolucion) {
        this.idResolucion = idResolucion;
    }

    public List<UsuarioRol> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<UsuarioRol> integrantes) {
        this.integrantes = integrantes;
    }

}
