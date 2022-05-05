package com.pid.proyecto.Json.Modificar;

import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class JsonModificarDeclaracion {
    
    private int idDenuncia = -1;
    
    private int idComision = -1;
    
    private int idUsuario = -1;
    
    private boolean abierta = false;
    
    private String descripcion = "";

    public JsonModificarDeclaracion() {
    }

    public int getIdComision() {
        return idComision;
    }

    public void setIdComision(int idComision) {
        this.idComision = idComision;
    }

    
    
    public int getIdDenuncia() {
        return idDenuncia;
    }

    public void setIdDenuncia(int idDenuncia) {
        this.idDenuncia = idDenuncia;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public boolean isAbierta() {
        return abierta;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
