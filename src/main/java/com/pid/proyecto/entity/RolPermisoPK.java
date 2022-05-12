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
    @Column(name = "idr")
    private int idr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idp")
    private int idp;

    public RolPermisoPK() {
    }

    public RolPermisoPK(int idr, int idp) {
        this.idr = idr;
        this.idp = idp;
    }

    public int getIdr() {
        return idr;
    }

    public void setIdr(int idr) {
        this.idr = idr;
    }

    public int getIdp() {
        return idp;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idr;
        hash += (int) idp;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolPermisoPK)) {
            return false;
        }
        RolPermisoPK other = (RolPermisoPK) object;
        if (this.idr != other.idr) {
            return false;
        }
        if (this.idp != other.idp) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.RolPermisoPK[ idr=" + idr + ", idp=" + idp + " ]";
    }
    
}
