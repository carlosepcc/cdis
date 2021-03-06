/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pid.proyecto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
// @JsonIgnoreProperties(value = {"comision", "rol", "usuario"})
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
    @JsonIgnoreProperties(value = {"casoList", "resolucion", "comisionUsuarioList"})
    private Comision comision;
    @JoinColumn(name = "rol", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonIgnoreProperties(value = {"permisos", "usuarioList", "comisionUsuarioList"})
    private Rol rol;
    @JoinColumn(name = "idu", referencedColumnName = "usuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonIgnoreProperties(value = {"denunciaList","declaracionList","rol","comisionUsuarioList"})
    private Usuario usuario;

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
