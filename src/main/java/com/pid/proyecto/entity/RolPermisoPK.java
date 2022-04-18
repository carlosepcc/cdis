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
public class RolPermisoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idrol")
    private int idrol;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idpermiso")
    private int idpermiso;

    public RolPermisoPK() {
    }

    public RolPermisoPK(int idrol, int idpermiso) {
        this.idrol = idrol;
        this.idpermiso = idpermiso;
    }

    public int getIdrol() {
        return idrol;
    }

    public void setIdrol(int idrol) {
        this.idrol = idrol;
    }

    public int getIdpermiso() {
        return idpermiso;
    }

    public void setIdpermiso(int idpermiso) {
        this.idpermiso = idpermiso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idrol;
        hash += (int) idpermiso;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolPermisoPK)) {
            return false;
        }
        RolPermisoPK other = (RolPermisoPK) object;
        if (this.idrol != other.idrol) {
            return false;
        }
        if (this.idpermiso != other.idpermiso) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.RolPermisoPK[ idrol=" + idrol + ", idpermiso=" + idpermiso + " ]";
    }
    
}
