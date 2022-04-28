package com.pid.proyecto.Json;

import java.time.LocalDate;

public class JsonDenuncia {

    String descripcion = "";
    
    String acusado = "";

    LocalDate fecha = LocalDate.now();

    boolean procesada = false;

    int idUsuario = -1;

    public JsonDenuncia() {
    }

    public String getAcusado() {
        return acusado;
    }

    public void setAcusado(String acusado) {
        this.acusado = acusado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public boolean isProcesada() {
        return procesada;
    }

    public void setProcesada(boolean procesada) {
        this.procesada = procesada;
    }

}
