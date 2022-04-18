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
public class DenunciaUsuarioPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "iddenuncia")
    private int iddenuncia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idusuario")
    private int idusuario;

    public DenunciaUsuarioPK() {
    }

    public DenunciaUsuarioPK(int iddenuncia, int idusuario) {
        this.iddenuncia = iddenuncia;
        this.idusuario = idusuario;
    }

    public int getIddenuncia() {
        return iddenuncia;
    }

    public void setIddenuncia(int iddenuncia) {
        this.iddenuncia = iddenuncia;
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
        hash += (int) iddenuncia;
        hash += (int) idusuario;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DenunciaUsuarioPK)) {
            return false;
        }
        DenunciaUsuarioPK other = (DenunciaUsuarioPK) object;
        if (this.iddenuncia != other.iddenuncia) {
            return false;
        }
        if (this.idusuario != other.idusuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.DenunciaUsuarioPK[ iddenuncia=" + iddenuncia + ", idusuario=" + idusuario + " ]";
    }
    
}
