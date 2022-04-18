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
public class ComDiscUsuarioPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idcomision")
    private int idcomision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idusuario")
    private int idusuario;

    public ComDiscUsuarioPK() {
    }

    public ComDiscUsuarioPK(int idcomision, int idusuario) {
        this.idcomision = idcomision;
        this.idusuario = idusuario;
    }

    public int getIdcomision() {
        return idcomision;
    }

    public void setIdcomision(int idcomision) {
        this.idcomision = idcomision;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idcomision;
        hash += (int) idusuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComDiscUsuarioPK)) {
            return false;
        }
        ComDiscUsuarioPK other = (ComDiscUsuarioPK) object;
        if (this.idcomision != other.idcomision) {
            return false;
        }
        if (this.idusuario != other.idusuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.ComDiscUsuarioPK[ idcomision=" + idcomision + ", idusuario=" + idusuario + " ]";
    }
    
}
