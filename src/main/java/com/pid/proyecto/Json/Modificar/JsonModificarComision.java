package com.pid.proyecto.Json.Modificar;

import com.pid.proyecto.auxiliares.UsuarioRol;
import java.util.LinkedList;
import java.util.List;

public class JsonModificarComision {

    private int idComision = -1;

    private List<UsuarioRol> integrantes = new LinkedList<>();


    public JsonModificarComision() {
    }

    public int getIdComision() {
        return idComision;
    }

    public List<UsuarioRol> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<UsuarioRol> integrantes) {
        this.integrantes = integrantes;
    }

    public void setIdComision(int idComision) {
        this.idComision = idComision;
    }
}
