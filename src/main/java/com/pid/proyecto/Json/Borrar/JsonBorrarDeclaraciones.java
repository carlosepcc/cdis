package com.pid.proyecto.Json.Borrar;

import java.util.LinkedList;
import java.util.List;

public class JsonBorrarDeclaraciones {

    private List<Integer> LU = new LinkedList<>();
    private List<Integer> LD = new LinkedList<>();
    private List<Integer> LC = new LinkedList<>();

    public JsonBorrarDeclaraciones() {
    }

    public List<Integer> getLU() {
        return LU;
    }

    public void setLU(List<Integer> LU) {
        this.LU = LU;
    }

    public List<Integer> getLD() {
        return LD;
    }

    public void setLD(List<Integer> LD) {
        this.LD = LD;
    }

    public List<Integer> getLC() {
        return LC;
    }

    public void setLC(List<Integer> LC) {
        this.LC = LC;
    }

}
