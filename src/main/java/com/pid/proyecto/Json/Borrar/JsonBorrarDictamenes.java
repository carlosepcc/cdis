package com.pid.proyecto.Json.Borrar;

import com.pid.proyecto.entity.DictamenPK;
import java.util.LinkedList;
import java.util.List;

public class JsonBorrarDictamenes {
    
    private List<Integer> LD = new LinkedList<>();
    private List<Integer> LC = new LinkedList<>();
    private List<DictamenPK> LPK = new LinkedList();
    
    public JsonBorrarDictamenes() {
    }
    
    public List<DictamenPK> getLPK() {
        
        DictamenPK PK;
        for (int i = 0; i < LD.size(); i++) {
            
            PK = new DictamenPK(LD.get(i), LC.get(i));
            
            LPK.add(PK);
        }
        
        return LPK;
    }
    
    public void setLPK(List<DictamenPK> LPK) {
        this.LPK = LPK;
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
