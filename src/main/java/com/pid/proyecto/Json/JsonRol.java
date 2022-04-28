package com.pid.proyecto.Json;

import java.util.LinkedList;
import java.util.List;

public class JsonRol {
//  @NotBlank(message = "DEBES ESPECIFICAR EL ROL")
//  @Pattern(
//    regexp = "ROLE_[A-Z|_]*",
//    message = "FORMATO DE ROL INCORRECTO, EL ROL DEBE COMENZAR CON [ROL_] Y SOLO ADMITE [_] Y [A-Z]"
//  )

    private String rol = "";

    private List<Integer> permisos = new LinkedList<>();

    private List<Integer> agregarPermisos = new LinkedList<>();

    List<Integer> eliminarPermisos = new LinkedList<>();

    public JsonRol() {
    }

    public List<Integer> getPermisos() {
        return permisos;
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

    public void setPermisos(List<Integer> permisos) {
        this.permisos = permisos;
    }

    public JsonRol(String rol) {
        this.rol = rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }
}
