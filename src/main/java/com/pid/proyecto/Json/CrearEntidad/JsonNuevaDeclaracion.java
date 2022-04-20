package com.pid.proyecto.Json.CrearEntidad;

import javax.validation.constraints.NotBlank;

public class JsonNuevaDeclaracion {

    private boolean abierta;
    
    private String descripcion;
    @NotBlank
    private String usuario;
    @NotBlank
    private int idCaso;
    @NotBlank
    private int idUsuario;
    
    public JsonNuevaDeclaracion() {
    }

    public boolean isAbierta() {
        return abierta;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }
    
    

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    

    public int getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(int idCaso) {
        this.idCaso = idCaso;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
