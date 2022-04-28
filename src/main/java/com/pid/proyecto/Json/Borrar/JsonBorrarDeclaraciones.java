package com.pid.proyecto.Json.Borrar;

import com.pid.proyecto.entity.DeclaracionPK;
import java.util.LinkedList;
import java.util.List;

public class JsonBorrarDeclaraciones {

    private List<DeclaracionPK> LDPK = new LinkedList<>();
    private List<Integer> LU = new LinkedList<>();
    private List<Integer> LD = new LinkedList<>();
    private List<Integer> LC = new LinkedList<>();

    public JsonBorrarDeclaraciones() {
    }

    public List<DeclaracionPK> getLDPK() {
        DeclaracionPK PK;
        for (int i = 0; i < LU.size(); i++) {
            PK = new DeclaracionPK(LU.get(i), LD.get(i), LC.get(i));
            LDPK.add(PK);
        }
        return LDPK;
    }

    public void setLDPK(List<DeclaracionPK> LDPK) {
        this.LDPK = LDPK;
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
