package com.pid.proyecto.Json.Modificar;

import com.pid.proyecto.auxiliares.UsuarioRol;
import java.util.LinkedList;
import java.util.List;

public class JsonModificarComision {

    private int idComision = -1;

    private List<String> quitarIntegrantes = new LinkedList<>();

    private List<UsuarioRol> agregarIntegrantes = new LinkedList<>();

    public JsonModificarComision() {
    }

    public List<String> getQuitarIntegrantes() {
        return quitarIntegrantes;
    }

    public void setQuitarIntegrantes(List<String> quitarIntegrantes) {
        this.quitarIntegrantes = quitarIntegrantes;
    }

    public int getIdComision() {
        return idComision;
    }

    public void setIdComision(int idComision) {
        this.idComision = idComision;
    }

    public List<UsuarioRol> getAgregarIntegrantes() {
        return agregarIntegrantes;
    }

    public void setAgregarIntegrantes(List<UsuarioRol> agregarIntegrantes) {
        this.agregarIntegrantes = agregarIntegrantes;
    }

}