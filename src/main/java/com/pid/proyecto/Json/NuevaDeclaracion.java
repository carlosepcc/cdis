package com.pid.proyecto.Json;

import java.util.Date;
import javax.validation.constraints.NotBlank;

public class NuevaDeclaracion {

    @NotBlank
    private String descripcion;
    @NotBlank
    private String estudiante;
    @NotBlank
    private int idCaso;

    public NuevaDeclaracion() {
    }

    public int getIdCaso() {
        return idCaso;
    }

    public void setIdCaso(int idCaso) {
        this.idCaso = idCaso;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
