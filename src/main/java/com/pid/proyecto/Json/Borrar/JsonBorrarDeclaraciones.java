package com.pid.proyecto.Json.Borrar;

import com.pid.proyecto.entity.DeclaracionPK;
import java.util.LinkedList;
import java.util.List;

public class JsonBorrarDeclaraciones {

    private List<DeclaracionPK> ids = new LinkedList<>();

    public List<DeclaracionPK> getIds() {
        return ids;
    }

    public void setIds(List<DeclaracionPK> ids) {
        this.ids = ids;
    }

    public JsonBorrarDeclaraciones() {
    }


}
