/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pid.proyecto.Json.EntidadesAuxiliares;

/**
 *
 * @author Angel
 */
public class ComisionReducida {

    private String Presidente = "";
    private int idRolP = -1;
    private String Secretario = "";
    private int idRolS = -1;

    public String getPresidente() {
        return Presidente;
    }

    public int getIdRolP() {
        return idRolP;
    }

    public void setIdRolP(int idRolP) {
        this.idRolP = idRolP;
    }

    public int getIdRolS() {
        return idRolS;
    }

    public void setIdRolS(int idRolS) {
        this.idRolS = idRolS;
    }
    

    public void setPresidente(String Presidente) {
        this.Presidente = Presidente;
    }

    public String getSecretario() {
        return Secretario;
    }

    public void setSecretario(String Secretario) {
        this.Secretario = Secretario;
    }

}
