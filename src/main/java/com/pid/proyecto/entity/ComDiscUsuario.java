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
@Table(name = "com_disc_usuario")
@NamedQueries({
    @NamedQuery(name = "ComDiscUsuario.findAll", query = "SELECT c FROM ComDiscUsuario c")})
public class ComDiscUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComDiscUsuarioPK comDiscUsuarioPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "rolcomision")
    private String rolcomision;
    @JoinColumn(name = "idcomision", referencedColumnName = "idcomision", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private ComisionDisciplinaria comisionDisciplinaria;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Usuario usuario;

    public ComDiscUsuario() {
    }

    public ComDiscUsuario(ComDiscUsuarioPK comDiscUsuarioPK) {
        this.comDiscUsuarioPK = comDiscUsuarioPK;
    }

    public ComDiscUsuario(ComDiscUsuarioPK comDiscUsuarioPK, String rolcomision) {
        this.comDiscUsuarioPK = comDiscUsuarioPK;
        this.rolcomision = rolcomision;
    }

    public ComDiscUsuario(int idcomision, int idusuario) {
        this.comDiscUsuarioPK = new ComDiscUsuarioPK(idcomision, idusuario);
    }

    public ComDiscUsuarioPK getComDiscUsuarioPK() {
        return comDiscUsuarioPK;
    }

    public void setComDiscUsuarioPK(ComDiscUsuarioPK comDiscUsuarioPK) {
        this.comDiscUsuarioPK = comDiscUsuarioPK;
    }

    public String getRolcomision() {
        return rolcomision;
    }

    public void setRolcomision(String rolcomision) {
        this.rolcomision = rolcomision;
    }

    public ComisionDisciplinaria getComisionDisciplinaria() {
        return comisionDisciplinaria;
    }

    public void setComisionDisciplinaria(ComisionDisciplinaria comisionDisciplinaria) {
        this.comisionDisciplinaria = comisionDisciplinaria;
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
        hash += (comDiscUsuarioPK != null ? comDiscUsuarioPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComDiscUsuario)) {
            return false;
        }
        ComDiscUsuario other = (ComDiscUsuario) object;
        if ((this.comDiscUsuarioPK == null && other.comDiscUsuarioPK != null) || (this.comDiscUsuarioPK != null && !this.comDiscUsuarioPK.equals(other.comDiscUsuarioPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.ComDiscUsuario[ comDiscUsuarioPK=" + comDiscUsuarioPK + " ]";
    }
    
}
