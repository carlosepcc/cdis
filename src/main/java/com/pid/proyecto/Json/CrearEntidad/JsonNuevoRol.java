package com.pid.proyecto.Json.CrearEntidad;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class JsonNuevoRol {
  @NotBlank(message = "DEBES ESPECIFICAR EL ROL")
  @Pattern(
    regexp = "ROLE_[A-Z|_]*",
    message = "FORMATO DE ROL INCORRECTO, EL ROL DEBE COMENZAR CON [ROL_] Y SOLO ADMITE [_] Y [A-Z]"
  )
  private String rol;

  private List<Integer> permisos;

  public JsonNuevoRol() {}

  public List<Integer> getPermisos() {
    return permisos;
  }

  public void setPermisos(List<Integer> permisos) {
    this.permisos = permisos;
  }

  public JsonNuevoRol(String rol) {
    this.rol = rol;
  }

  public void setRol(String rol) {
    this.rol = rol;
  }

  public String getRol() {
    return rol;
  }
}
