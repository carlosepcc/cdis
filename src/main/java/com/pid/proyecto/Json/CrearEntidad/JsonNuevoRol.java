package com.pid.proyecto.Json.CrearEntidad;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class JsonNuevoRol {
  @NotBlank(message = "DEBES ESPECIFICAR EL ROL")
  @Pattern(
    regexp = "ROLE_[A-Z|_]*",
    message = "FORMATO DE ROL INCORRECTO, EL ROL DEBE COMENZAR CON [ROL_] Y SOLO ADMITE [_] Y [A-Z]"
  )
  private String rol;

  public JsonNuevoRol() {}

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
