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
@Table(name = "rol_permiso")
@NamedQueries({
    @NamedQuery(name = "RolPermiso.findAll", query = "SELECT r FROM RolPermiso r")})
public class RolPermiso implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RolPermisoPK rolPermisoPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "rol")
    private String rol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "permiso")
    private String permiso;
    @JoinColumn(name = "idpermiso", referencedColumnName = "idpermiso", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Permiso permiso1;
    @JoinColumn(name = "idrol", referencedColumnName = "idrol", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Rol rol1;

    public RolPermiso() {
    }

    public RolPermiso(RolPermisoPK rolPermisoPK) {
        this.rolPermisoPK = rolPermisoPK;
    }

    public RolPermiso(RolPermisoPK rolPermisoPK, String rol, String permiso) {
        this.rolPermisoPK = rolPermisoPK;
        this.rol = rol;
        this.permiso = permiso;
    }

    public RolPermiso(int idrol, int idpermiso) {
        this.rolPermisoPK = new RolPermisoPK(idrol, idpermiso);
    }

    public RolPermisoPK getRolPermisoPK() {
        return rolPermisoPK;
    }

    public void setRolPermisoPK(RolPermisoPK rolPermisoPK) {
        this.rolPermisoPK = rolPermisoPK;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    public Permiso getPermiso1() {
        return permiso1;
    }

    public void setPermiso1(Permiso permiso1) {
        this.permiso1 = permiso1;
    }

    public Rol getRol1() {
        return rol1;
    }

    public void setRol1(Rol rol1) {
        this.rol1 = rol1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rolPermisoPK != null ? rolPermisoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RolPermiso)) {
            return false;
        }
        RolPermiso other = (RolPermiso) object;
        if ((this.rolPermisoPK == null && other.rolPermisoPK != null) || (this.rolPermisoPK != null && !this.rolPermisoPK.equals(other.rolPermisoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.RolPermiso[ rolPermisoPK=" + rolPermisoPK + " ]";
    }
    
}
