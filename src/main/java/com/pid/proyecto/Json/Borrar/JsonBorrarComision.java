package com.pid.proyecto.Json.Borrar;

import java.util.LinkedList;
import java.util.List;

public class JsonBorrarComision {

    private List<Integer> idComisiones = new LinkedList<>();

    public JsonBorrarComision() {
    }

    public List<Integer> getIdComisiones() {
        return idComisiones;
    }

    public void setIdComisiones(List<Integer> idComisiones) {
        this.idComisiones = idComisiones;
    }

}
