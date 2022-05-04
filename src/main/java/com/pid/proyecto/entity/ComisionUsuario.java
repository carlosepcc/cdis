/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pid.proyecto.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "usuario")
    private String usuario;
    @JoinColumn(name = "idc", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Comision comision;
    @JoinColumn(name = "rol", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Rol rol;
    @JoinColumn(name = "idu", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario1;

    public ComisionUsuario() {
    }

    public ComisionUsuario(ComisionUsuarioPK comisionUsuarioPK) {
        this.comisionUsuarioPK = comisionUsuarioPK;
    }

    public ComisionUsuario(ComisionUsuarioPK comisionUsuarioPK, String usuario) {
        this.comisionUsuarioPK = comisionUsuarioPK;
        this.usuario = usuario;
    }

    public ComisionUsuario(int idc, int idu) {
        this.comisionUsuarioPK = new ComisionUsuarioPK(idc, idu);
    }

    public ComisionUsuarioPK getComisionUsuarioPK() {
        return comisionUsuarioPK;
    }

    public void setComisionUsuarioPK(ComisionUsuarioPK comisionUsuarioPK) {
        this.comisionUsuarioPK = comisionUsuarioPK;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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
        if ((this.comisionUsuarioPK == null && other.comisionUsuarioPK != null) || (this.comisionUsuarioPK != null && !this.comisionUsuarioPK.equals(other.comisionUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.ComisionUsuario[ comisionUsuarioPK=" + comisionUsuarioPK + " ]";
    }
    
}
