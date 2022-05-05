package com.pid.proyecto.Json.Borrar;

import com.pid.proyecto.entity.DictamenPK;
import java.util.LinkedList;
import java.util.List;

public class JsonBorrarDictamen {

    private List<DictamenPK> LPK = new LinkedList();

    public JsonBorrarDictamen() {
    }

    public List<DictamenPK> getLPK() {
        return LPK;
    }

    public void setLPK(List<DictamenPK> LPK) {
        this.LPK = LPK;
    }

}
