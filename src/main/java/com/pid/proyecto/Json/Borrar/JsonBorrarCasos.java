package com.pid.proyecto.Json.Borrar;

import com.pid.proyecto.entity.CasoPK;
import java.util.LinkedList;
import java.util.List;

public class JsonBorrarCasos {
    
   private List<CasoPK> LCPK = new LinkedList<>();

    public JsonBorrarCasos() {
    }

    public List<CasoPK> getLCPK() {
        return LCPK;
    }

    public void setLCPK(List<CasoPK> LCPK) {
        this.LCPK = LCPK;
    }

    
}
