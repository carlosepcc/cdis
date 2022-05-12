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
import javax.validation.constraints.Size;

/**
 *
 * @author Angel
 */
@Embeddable
public class ComisionUsuarioPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idc")
    private int idc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "idu")
    private String idu;

    public ComisionUsuarioPK() {
    }

    public ComisionUsuarioPK(int idc, String idu) {
        this.idc = idc;
        this.idu = idu;
    }

    public int getIdc() {
        return idc;
    }

    public void setIdc(int idc) {
        this.idc = idc;
    }

    public String getIdu() {
        return idu;
    }

    public void setIdu(String idu) {
        this.idu = idu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idc;
        hash += (idu != null ? idu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComisionUsuarioPK)) {
            return false;
        }
        ComisionUsuarioPK other = (ComisionUsuarioPK) object;
        if (this.idc != other.idc) {
            return false;
        }
        if ((this.idu == null && other.idu != null) || (this.idu != null && !this.idu.equals(other.idu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.ComisionUsuarioPK[ idc=" + idc + ", idu=" + idu + " ]";
    }
    
}
