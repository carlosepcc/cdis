/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pid.proyecto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Angel
 */

// @JsonIgnoreProperties(value = {"caso", "usuario1"})
@Entity
@Table(name = "declaracion")
@NamedQueries({
    @NamedQuery(name = "Declaracion.findAll", query = "SELECT d FROM Declaracion d")})
public class Declaracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DeclaracionPK declaracionPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "abierta")
    private boolean abierta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "declaracion")
    private String declaracion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "expediente")
    private String expediente;
    @JoinColumns({
        @JoinColumn(name = "casocomision", referencedColumnName = "comision", insertable = false, updatable = false),
        @JoinColumn(name = "casodenuncia", referencedColumnName = "denuncia", insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    @JsonIgnoreProperties(value = {"comision1", "denuncia1", "declaracionList"})
    private Caso caso;
    @JoinColumn(name = "usuario", referencedColumnName = "usuario", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    @JsonIgnoreProperties(value = {"denunciaList","declaracionList","rol","comisionUsuarioList"})
    private Usuario usuario1;

    public Declaracion() {
    }

    public Declaracion(DeclaracionPK declaracionPK) {
        this.declaracionPK = declaracionPK;
    }

    public Declaracion(DeclaracionPK declaracionPK, boolean abierta, Date fecha, String declaracion, String expediente) {
        this.declaracionPK = declaracionPK;
        this.abierta = abierta;
        this.fecha = fecha;
        this.declaracion = declaracion;
        this.expediente = expediente;
    }

    public Declaracion(String usuario, int casocomision, int casodenuncia) {
        this.declaracionPK = new DeclaracionPK(usuario, casocomision, casodenuncia);
    }

    public DeclaracionPK getDeclaracionPK() {
        return declaracionPK;
    }

    public void setDeclaracionPK(DeclaracionPK declaracionPK) {
        this.declaracionPK = declaracionPK;
    }

    public boolean getAbierta() {
        return abierta;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDeclaracion() {
        return declaracion;
    }

    public void setDeclaracion(String declaracion) {
        this.declaracion = declaracion;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public Caso getCaso() {
        return caso;
    }

    public void setCaso(Caso caso) {
        this.caso = caso;
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
        hash += (declaracionPK != null ? declaracionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Declaracion)) {
            return false;
        }
        Declaracion other = (Declaracion) object;
        if ((this.declaracionPK == null && other.declaracionPK != null) || (this.declaracionPK != null && !this.declaracionPK.equals(other.declaracionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.Declaracion[ declaracionPK=" + declaracionPK + " ]";
    }
    
}
