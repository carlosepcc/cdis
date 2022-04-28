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
    @Column(name = "idd")
    private int idd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idu")
    private int idu;

    public DenunciaUsuarioPK() {
    }

    public DenunciaUsuarioPK(int idd, int idu) {
        this.idd = idd;
        this.idu = idu;
    }

    public int getIdd() {
        return idd;
    }

    public void setIdd(int idd) {
        this.idd = idd;
    }

    public int getIdu() {
        return idu;
    }

    public void setIdu(int idu) {
        this.idu = idu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idd;
        hash += (int) idu;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DenunciaUsuarioPK)) {
            return false;
        }
        DenunciaUsuarioPK other = (DenunciaUsuarioPK) object;
        if (this.idd != other.idd) {
            return false;
        }
        if (this.idu != other.idu) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.DenunciaUsuarioPK[ idd=" + idd + ", idu=" + idu + " ]";
    }
    
}
