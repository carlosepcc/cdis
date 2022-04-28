package com.pid.proyecto.Json;

import javax.validation.constraints.NotEmpty;


public class JsonObjeto {
    
    @NotEmpty(message = " no debe estar en blanco")
    private String propiedad;

    public JsonObjeto() {
    }

    public String getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(String propiedad) {
        this.propiedad = propiedad;
    }
    
    
}
