package com.pid.proyecto.Json.CrearEntidad;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class JsonRegistrarUsuario {
  @NotBlank(message = "DEBES ESPECIFICAR EL NOMBRE")
  @Pattern(
    regexp = "([A-Z|Á|É|Í|Ó|Ú|Ñ][a-z|á|é|í|ó|ú|ñ|ű]*)|([A-Z|Á|É|Í|Ó|Ú|Ñ][a-z|á|é|í|ó|ú|ñ|ű]* [A-Z|Á|É|Í|Ó|Ú|Ñ][a-z|á|é|í|ó|ú|ñ|ű]*)",
    message = "FORMATO DE NOMBRE INCORRECTO, DEBE COMENZAR CON MAYÚSCULA SEGUIDO DE MINÚSCULAS Y SOLO ADMITE DOS ENTRADAS"
  )
  private String nombre;

  @NotBlank(message = "DEBES ESPECIFICAR EL APELLIDO")
  @Pattern(
    regexp = "([A-Z|Á|É|Í|Ó|Ú|Ñ][a-z|á|é|í|ó|ú|ñ|ű]*)|([A-Z|Á|É|Í|Ó|Ú|Ñ][a-z|á|é|í|ó|ú|ñ|ű]* [A-Z|Á|É|Í|Ó|Ú|Ñ][a-z|á|é|í|ó|ú|ñ|ű]*)",
    message = "FORMATO DE APELLIDO INCORRECTO, DEBE COMENZAR CON MAYÚSCULA SEGUIDO DE MINÚSCULAS Y SOLO ADMITE DOS ENTRADAS"
  )
  private String apellidos;

  @NotBlank(message = "DEBES ESPECIFICAR EL USUARIO")
  @Pattern(
    regexp = "[a-z]*",
    message = "FORMATO DE USUARIO INCORRECTO, SOLO LETRAS MINÚSCULAS"
  )
  @Size(
    min = 4,
    max = 10,
    message = "EL USUARIO DEBE CONTENER DE 4 - 10 CARACTERES"
  )
  private String usuario;

  @Size(
    min = 4,
    max = 10,
    message = "LA CONTRASEÑA DEBE CONTENER DE 4 - 10 CARACTERES"
  )
  @NotBlank(message = "DEBES ESPECIFICAR UNA CONTRASEÑA")
  private String contrasena;


  public JsonRegistrarUsuario() {}

  public JsonRegistrarUsuario(
    String nombre,
    String apellidos,
    String usuario,
    String contrasena
  ) {
    this.nombre = nombre;
    this.apellidos = apellidos;
    this.usuario = usuario;
    this.contrasena = contrasena;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getContrasena() {
    return contrasena;
  }

  public void setContrasena(String contrasena) {
    this.contrasena = contrasena;
  }
}
