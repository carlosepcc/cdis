package com.pid.proyecto.Json.Borrar;

import com.pid.proyecto.entity.ExpedientePK;
import java.util.LinkedList;
import java.util.List;

public class JsonBorrarExpedientes {

    List<ExpedientePK> LPK = new LinkedList<>();

    public JsonBorrarExpedientes() {
    }

    public List<ExpedientePK> getLPK() {
        return LPK;
    }

    public void setLPK(List<ExpedientePK> LPK) {
        this.LPK = LPK;
    }

}
