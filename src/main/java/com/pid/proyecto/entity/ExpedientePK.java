/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pid.proyecto.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Angel
 */
@Embeddable
public class ExpedientePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "declaracionusuario")
    private int declaracionusuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "declaracioncasodenuncia")
    private int declaracioncasodenuncia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "declaracioncasocomision")
    private int declaracioncasocomision;

    public ExpedientePK() {
    }

    public ExpedientePK(int declaracionusuario, int declaracioncasodenuncia, int declaracioncasocomision) {
        this.declaracionusuario = declaracionusuario;
        this.declaracioncasodenuncia = declaracioncasodenuncia;
        this.declaracioncasocomision = declaracioncasocomision;
    }

    public int getDeclaracionusuario() {
        return declaracionusuario;
    }

    public void setDeclaracionusuario(int declaracionusuario) {
        this.declaracionusuario = declaracionusuario;
    }

    public int getDeclaracioncasodenuncia() {
        return declaracioncasodenuncia;
    }

    public void setDeclaracioncasodenuncia(int declaracioncasodenuncia) {
        this.declaracioncasodenuncia = declaracioncasodenuncia;
    }

    public int getDeclaracioncasocomision() {
        return declaracioncasocomision;
    }

    public void setDeclaracioncasocomision(int declaracioncasocomision) {
        this.declaracioncasocomision = declaracioncasocomision;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) declaracionusuario;
        hash += (int) declaracioncasodenuncia;
        hash += (int) declaracioncasocomision;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ExpedientePK)) {
            return false;
        }
        ExpedientePK other = (ExpedientePK) object;
        if (this.declaracionusuario != other.declaracionusuario) {
            return false;
        }
        if (this.declaracioncasodenuncia != other.declaracioncasodenuncia) {
            return false;
        }
        if (this.declaracioncasocomision != other.declaracioncasocomision) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.ExpedientePK[ declaracionusuario=" + declaracionusuario + ", declaracioncasodenuncia=" + declaracioncasodenuncia + ", declaracioncasocomision=" + declaracioncasocomision + " ]";
    }
    
}
