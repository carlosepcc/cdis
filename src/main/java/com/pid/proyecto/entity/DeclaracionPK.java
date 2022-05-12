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
public class DeclaracionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @NotNull
    @Column(name = "casocomision")
    private int casocomision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "casodenuncia")
    private int casodenuncia;

    public DeclaracionPK() {
    }

    public DeclaracionPK(String usuario, int casocomision, int casodenuncia) {
        this.usuario = usuario;
        this.casocomision = casocomision;
        this.casodenuncia = casodenuncia;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getCasocomision() {
        return casocomision;
    }

    public void setCasocomision(int casocomision) {
        this.casocomision = casocomision;
    }

    public int getCasodenuncia() {
        return casodenuncia;
    }

    public void setCasodenuncia(int casodenuncia) {
        this.casodenuncia = casodenuncia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuario != null ? usuario.hashCode() : 0);
        hash += (int) casocomision;
        hash += (int) casodenuncia;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DeclaracionPK)) {
            return false;
        }
        DeclaracionPK other = (DeclaracionPK) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        if (this.casocomision != other.casocomision) {
            return false;
        }
        if (this.casodenuncia != other.casodenuncia) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.DeclaracionPK[ usuario=" + usuario + ", casocomision=" + casocomision + ", casodenuncia=" + casodenuncia + " ]";
    }
    
}
