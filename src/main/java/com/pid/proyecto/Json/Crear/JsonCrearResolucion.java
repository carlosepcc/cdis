package com.pid.proyecto.Json.Crear;

import com.pid.proyecto.Json.EntidadesAuxiliares.ComisionReducida;
import java.util.LinkedList;
import java.util.List;

public class JsonCrearResolucion {
    
    private String ano = "";
    
    private List<ComisionReducida> comisiones = new LinkedList<>();

    public JsonCrearResolucion() {
    }

    public List<ComisionReducida> getComisiones() {
        return comisiones;
    }

    public void setComisiones(List<ComisionReducida> comisiones) {
        this.comisiones = comisiones;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }
}
