package com.pid.proyecto.Json.Borrar;

import com.pid.proyecto.entity.CasoPK;
import java.util.LinkedList;
import java.util.List;

public class JsonBorrarCasos {
    
   private List<CasoPK> ids = new LinkedList<>();

    public JsonBorrarCasos() {
    }

    public List<CasoPK> getIds() {
        return ids;
    }

    public void setIds(List<CasoPK> ids) {
        this.ids = ids;
    }

    
}
