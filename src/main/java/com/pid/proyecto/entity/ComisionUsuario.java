/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pid.proyecto.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Angel
 */
@Entity
@Table(name = "comision_usuario")
@NamedQueries({
    @NamedQuery(name = "ComisionUsuario.findAll", query = "SELECT c FROM ComisionUsuario c")})
public class ComisionUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComisionUsuarioPK comisionUsuarioPK;
    @JoinColumn(name = "idc", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Comision comision;
    @JoinColumn(name = "rol", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rol rol;
    @JoinColumn(name = "idu", referencedColumnName = "usuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario1;

    public ComisionUsuario() {
    }

    public ComisionUsuario(ComisionUsuarioPK comisionUsuarioPK) {
        this.comisionUsuarioPK = comisionUsuarioPK;
    }

    public ComisionUsuario(int idc, String idu) {
        this.comisionUsuarioPK = new ComisionUsuarioPK(idc, idu);
    }

    public ComisionUsuarioPK getComisionUsuarioPK() {
        return comisionUsuarioPK;
    }

    public void setComisionUsuarioPK(ComisionUsuarioPK comisionUsuarioPK) {
        this.comisionUsuarioPK = comisionUsuarioPK;
    }

    public Comision getComision() {
        return comision;
    }

    public void setComision(Comision comision) {
        this.comision = comision;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Usuario getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(Usuario usuario1) {
        this.usuario1 = usuario1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comisionUsuarioPK != null ? comisionUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComisionUsuario)) {
            return false;
        }
        ComisionUsuario other = (ComisionUsuario) object;
        return !((this.comisionUsuarioPK == null && other.comisionUsuarioPK != null) || (this.comisionUsuarioPK != null && !this.comisionUsuarioPK.equals(other.comisionUsuarioPK)));
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.ComisionUsuario[ comisionUsuarioPK=" + comisionUsuarioPK + " ]";
    }
    
}
