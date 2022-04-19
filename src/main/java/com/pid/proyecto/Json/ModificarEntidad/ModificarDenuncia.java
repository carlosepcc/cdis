package com.pid.proyecto.Json.ModificarEntidad;

import java.util.List;
import javax.validation.constraints.NotBlank;

public class ModificarDenuncia {

    private String descripcion;
    @NotBlank
    private List<String> estudiantes;

    public ModificarDenuncia() {
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
