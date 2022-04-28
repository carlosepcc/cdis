package com.pid.proyecto.Json.Borrar;

import com.pid.proyecto.entity.CasoPK;
import java.util.LinkedList;
import java.util.List;

public class JsonBorrarCasos {
    
   private List<CasoPK> LCPK = new LinkedList<>();
   private List<Integer> LD = new LinkedList<>();
   private List<Integer> LC = new LinkedList<>();

    public JsonBorrarCasos() {
    }

    public List<CasoPK> getLCPK() {
        
        CasoPK PK;
        for (int i = 0; i < LD.size(); i++) {

            PK = new CasoPK(LD.get(i), LC.get(i));

            LCPK.add(PK);
        }

        return LCPK;
    }

    public void setLCPK(List<CasoPK> LCPK) {
        this.LCPK = LCPK;
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
