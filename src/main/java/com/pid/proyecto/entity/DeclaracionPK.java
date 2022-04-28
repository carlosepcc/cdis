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
public class DeclaracionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "usuario")
    private int usuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "casodenuncia")
    private int casodenuncia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "casocomision")
    private int casocomision;

    public DeclaracionPK() {
    }

    public DeclaracionPK(int usuario, int casodenuncia, int casocomision) {
        this.usuario = usuario;
        this.casodenuncia = casodenuncia;
        this.casocomision = casocomision;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public int getCasodenuncia() {
        return casodenuncia;
    }

    public void setCasodenuncia(int casodenuncia) {
        this.casodenuncia = casodenuncia;
    }

    public int getCasocomision() {
        return casocomision;
    }

    public void setCasocomision(int casocomision) {
        this.casocomision = casocomision;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) usuario;
        hash += (int) casodenuncia;
        hash += (int) casocomision;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeclaracionPK)) {
            return false;
        }
        DeclaracionPK other = (DeclaracionPK) object;
        if (this.usuario != other.usuario) {
            return false;
        }
        if (this.casodenuncia != other.casodenuncia) {
            return false;
        }
        if (this.casocomision != other.casocomision) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.DeclaracionPK[ usuario=" + usuario + ", casodenuncia=" + casodenuncia + ", casocomision=" + casocomision + " ]";
    }
    
}
