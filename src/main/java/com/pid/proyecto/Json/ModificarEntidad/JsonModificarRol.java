package com.pid.proyecto.Json.ModificarEntidad;

import java.util.LinkedList;
import java.util.List;

public class JsonModificarRol {

    private String rol = "";

    List<Integer> agregarPermisos = new LinkedList<>();
    
    List<Integer> eliminarPermisos = new LinkedList<>();
  
    public JsonModificarRol() {}

    
  
    public List<Integer> getAgregarPermisos() {
      return agregarPermisos;
    }



    public void setAgregarPermisos(List<Integer> agregarPermisos) {
      this.agregarPermisos = agregarPermisos;
    }



    public List<Integer> getEliminarPermisos() {
      return eliminarPermisos;
    }



    public void setEliminarPermisos(List<Integer> eliminarPermisos) {
      this.eliminarPermisos = eliminarPermisos;
    }



    public String getRol() {
      return rol;
    }
  
    public void setRol(String rol) {
      this.rol = rol;
    }
    
}
