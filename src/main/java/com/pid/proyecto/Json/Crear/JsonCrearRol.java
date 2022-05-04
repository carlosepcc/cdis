package com.pid.proyecto.Json.Crear;

import java.util.LinkedList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class JsonCrearRol {

    @NotBlank(message = "DEBES ESPECIFICAR EL ROL")
    @Pattern(
            regexp = "ROLE_[A-Z][A-Z|_]+",
            message = "FORMATO DE ROL INCORRECTO, EL ROL DEBE COMENZAR CON [ROL_] Y SOLO ADMITE [_] Y [A-Z]"
    )
    private String rol = "";

    private List<Integer> permisos = new LinkedList<>();

    private boolean rolParaComision = false;

    public JsonCrearRol() {
    }

    public boolean isRolParaComision() {
        return rolParaComision;
    }

    public void setRolParaComision(boolean rolParaComision) {
        this.rolParaComision = rolParaComision;
    }

    public List<Integer> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Integer> permisos) {
        this.permisos = permisos;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

}
