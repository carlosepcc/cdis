package com.pid.proyecto.Json;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;

public class NuevaDenuncia {

    private String descripcion;
    @NotBlank
    private List<String> estudiantes;

    public NuevaDenuncia() {
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<String> estudiantes) {
        this.estudiantes = estudiantes;
    }
}
