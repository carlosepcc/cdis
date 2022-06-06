package com.pid.proyecto.Json.Modificar;

import java.util.LinkedList;
import java.util.List;

public class JsonModificarRol {

    private int id = -1;

    private String rol = "";

    private List<Integer> permisos = new LinkedList<>();

    private boolean tipoComision = false;

    public JsonModificarRol() {
    }

    public List<Integer> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Integer> permisos) {
        this.permisos = permisos;
    }

    public boolean isTipoComision() {
        return tipoComision;
    }

    public void setTipoComision(boolean tipoComision) {
        this.tipoComision = tipoComision;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }
}
