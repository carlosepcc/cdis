package com.pid.proyecto.Json.Borrar;

import com.pid.proyecto.entity.DeclaracionPK;
import java.util.LinkedList;
import java.util.List;

public class JsonBorrarDeclaraciones {

    private List<DeclaracionPK> LDPK = new LinkedList<>();

    public List<DeclaracionPK> getLDPK() {
        return LDPK;
    }

    public void setLDPK(List<DeclaracionPK> LDPK) {
        this.LDPK = LDPK;
    }

    public JsonBorrarDeclaraciones() {
    }


}
