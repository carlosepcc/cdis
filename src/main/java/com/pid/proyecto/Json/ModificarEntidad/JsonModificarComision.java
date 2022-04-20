package com.pid.proyecto.Json.ModificarEntidad;

import java.util.Date;
import java.util.List;
import javax.validation.constraints.NotBlank;

//Esta es la clase encargada de recibir los datos desde el json
public class JsonModificarComision {

    @NotBlank
    private String tipoComision;
    @NotBlank
    private Date fecha;
    @NotBlank
    private List<String> usuarios;
    @NotBlank
    private List<String> rolComision;
    
    

    public JsonModificarComision() {
    }

    public List<String> getRolComision() {
        return rolComision;
    }

    public void setRolComision(List<String> rolComision) {
        this.rolComision = rolComision;
    }

    
    public List<String> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<String> usuario) {
        this.usuarios = usuario;
    }


    public String getTipoComision() {
        return tipoComision;
    }

    public void setTipoComision(String TipoComision) {
        this.tipoComision = TipoComision;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
