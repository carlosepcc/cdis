package com.pid.proyecto.Json.Modificar;

import java.util.LinkedList;
import java.util.List;

public class JsonModificarRol {

    private int id = -1;

    private String rol = "";

    private List<Integer> eliminarPermisos = new LinkedList<>();

    private List<Integer> agregarPermisos = new LinkedList<>();

    private boolean tipoComision = false;

    public JsonModificarRol() {
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

    public List<Integer> getAgregarPermisos() {
        return agregarPermisos;
    }

    public void setAgregarPermisos(List<Integer> agregarPermisos) {
        this.agregarPermisos = agregarPermisos;
    }

    public List<Integer> getEliminarPermisos() {
        return eliminarPermisos;
    }

    public void setEliminarPermisos(List<Integer> eliminarPermisos) {
        this.eliminarPermisos = eliminarPermisos;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }
}
