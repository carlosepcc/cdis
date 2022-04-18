/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pid.proyecto.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "pdf_expediente")
@NamedQueries({
    @NamedQuery(name = "PdfExpediente.findAll", query = "SELECT p FROM PdfExpediente p")})
public class PdfExpediente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idexpediente")
    private Integer idexpediente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "declaracion", referencedColumnName = "iddeclaracion")
    @ManyToOne(optional = false)
    private Declaracion declaracion;

    public PdfExpediente() {
    }

    public PdfExpediente(Integer idexpediente) {
        this.idexpediente = idexpediente;
    }

    public PdfExpediente(Integer idexpediente, String descripcion) {
        this.idexpediente = idexpediente;
        this.descripcion = descripcion;
    }

    public Integer getIdexpediente() {
        return idexpediente;
    }

    public void setIdexpediente(Integer idexpediente) {
        this.idexpediente = idexpediente;
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
        hash += (idexpediente != null ? idexpediente.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PdfExpediente)) {
            return false;
        }
        PdfExpediente other = (PdfExpediente) object;
        if ((this.idexpediente == null && other.idexpediente != null) || (this.idexpediente != null && !this.idexpediente.equals(other.idexpediente))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.PdfExpediente[ idexpediente=" + idexpediente + " ]";
    }
    
}
