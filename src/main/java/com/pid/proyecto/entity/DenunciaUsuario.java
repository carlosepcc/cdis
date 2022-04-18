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
@Table(name = "denuncia_usuario")
@NamedQueries({
    @NamedQuery(name = "DenunciaUsuario.findAll", query = "SELECT d FROM DenunciaUsuario d")})
public class DenunciaUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DenunciaUsuarioPK denunciaUsuarioPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "rolcomision")
    private String rolcomision;
    @JoinColumn(name = "iddenuncia", referencedColumnName = "iddenuncia", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Denuncia denuncia;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public DenunciaUsuario() {
    }

    public DenunciaUsuario(DenunciaUsuarioPK denunciaUsuarioPK) {
        this.denunciaUsuarioPK = denunciaUsuarioPK;
    }

    public DenunciaUsuario(DenunciaUsuarioPK denunciaUsuarioPK, String rolcomision) {
        this.denunciaUsuarioPK = denunciaUsuarioPK;
        this.rolcomision = rolcomision;
    }

    public DenunciaUsuario(int iddenuncia, int idusuario) {
        this.denunciaUsuarioPK = new DenunciaUsuarioPK(iddenuncia, idusuario);
    }

    public DenunciaUsuarioPK getDenunciaUsuarioPK() {
        return denunciaUsuarioPK;
    }

    public void setDenunciaUsuarioPK(DenunciaUsuarioPK denunciaUsuarioPK) {
        this.denunciaUsuarioPK = denunciaUsuarioPK;
    }

    public String getRolcomision() {
        return rolcomision;
    }

    public void setRolcomision(String rolcomision) {
        this.rolcomision = rolcomision;
    }

    public Denuncia getDenuncia() {
        return denuncia;
    }

    public void setDenuncia(Denuncia denuncia) {
        this.denuncia = denuncia;
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
        hash += (denunciaUsuarioPK != null ? denunciaUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DenunciaUsuario)) {
            return false;
        }
        DenunciaUsuario other = (DenunciaUsuario) object;
        if ((this.denunciaUsuarioPK == null && other.denunciaUsuarioPK != null) || (this.denunciaUsuarioPK != null && !this.denunciaUsuarioPK.equals(other.denunciaUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.DenunciaUsuario[ denunciaUsuarioPK=" + denunciaUsuarioPK + " ]";
    }
    
}
