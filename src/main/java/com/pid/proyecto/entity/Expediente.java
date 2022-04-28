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
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Angel
 */
@Entity
@Table(name = "expediente")
@NamedQueries({
    @NamedQuery(name = "Expediente.findAll", query = "SELECT e FROM Expediente e")})
public class Expediente implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ExpedientePK expedientePK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumns({
        @JoinColumn(name = "declaracionusuario", referencedColumnName = "usuario", insertable = false, updatable = false),
        @JoinColumn(name = "declaracioncasodenuncia", referencedColumnName = "casodenuncia", insertable = false, updatable = false),
        @JoinColumn(name = "declaracioncasocomision", referencedColumnName = "casocomision", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Declaracion declaracion;

    public Expediente() {
    }

    public Expediente(ExpedientePK expedientePK) {
        this.expedientePK = expedientePK;
    }

    public Expediente(ExpedientePK expedientePK, String descripcion) {
        this.expedientePK = expedientePK;
        this.descripcion = descripcion;
    }

    public Expediente(int declaracionusuario, int declaracioncasodenuncia, int declaracioncasocomision) {
        this.expedientePK = new ExpedientePK(declaracionusuario, declaracioncasodenuncia, declaracioncasocomision);
    }

    public ExpedientePK getExpedientePK() {
        return expedientePK;
    }

    public void setExpedientePK(ExpedientePK expedientePK) {
        this.expedientePK = expedientePK;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Declaracion getDeclaracion() {
        return declaracion;
    }

    public void setDeclaracion(Declaracion declaracion) {
        this.declaracion = declaracion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (expedientePK != null ? expedientePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Expediente)) {
            return false;
        }
        Expediente other = (Expediente) object;
        if ((this.expedientePK == null && other.expedientePK != null) || (this.expedientePK != null && !this.expedientePK.equals(other.expedientePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.Expediente[ expedientePK=" + expedientePK + " ]";
    }
    
}
