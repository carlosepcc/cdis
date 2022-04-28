package com.pid.proyecto.Json.Borrar;

import com.pid.proyecto.entity.ExpedientePK;
import java.util.LinkedList;
import java.util.List;

public class JsonBorrarExpedientes {

    List<Integer> idUsuarios = new LinkedList();
    List<Integer> idDenuncias = new LinkedList();
    List<Integer> idComision = new LinkedList();
    List<ExpedientePK> LPK = new LinkedList<>();

    public JsonBorrarExpedientes() {
    }

    public List<ExpedientePK> getLPK() {

        ExpedientePK PK;
        for (int i = 0; i < idUsuarios.size(); i++) {

            PK = new ExpedientePK(idUsuarios.get(i), idDenuncias.get(i), idComision.get(i));

            LPK.add(PK);
        }

        return LPK;
    }

    public void setLPK(List<ExpedientePK> LPK) {
        this.LPK = LPK;
    }

    public List<Integer> getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(List<Integer> idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public List<Integer> getIdDenuncias() {
        return idDenuncias;
    }

    public void setIdDenuncias(List<Integer> idDenuncias) {
        this.idDenuncias = idDenuncias;
    }

    public List<Integer> getIdComision() {
        return idComision;
    }

    public void setIdComision(List<Integer> idComision) {
        this.idComision = idComision;
    }

}
