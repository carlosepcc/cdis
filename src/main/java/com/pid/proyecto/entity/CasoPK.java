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
public class CasoPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "comision")
    private int comision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "denuncia")
    private int denuncia;

    public CasoPK() {
    }

    public CasoPK(int comision, int denuncia) {
        this.comision = comision;
        this.denuncia = denuncia;
    }

    public int getComision() {
        return comision;
    }

    public void setComision(int comision) {
        this.comision = comision;
    }

    public int getDenuncia() {
        return denuncia;
    }

    public void setDenuncia(int denuncia) {
        this.denuncia = denuncia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) comision;
        hash += (int) denuncia;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CasoPK)) {
            return false;
        }
        CasoPK other = (CasoPK) object;
        if (this.comision != other.comision) {
            return false;
        }
        if (this.denuncia != other.denuncia) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.CasoPK[ comision=" + comision + ", denuncia=" + denuncia + " ]";
    }
    
}
