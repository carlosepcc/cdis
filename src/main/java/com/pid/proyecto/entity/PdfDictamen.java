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
@Table(name = "pdf_dictamen")
@NamedQueries({
    @NamedQuery(name = "PdfDictamen.findAll", query = "SELECT p FROM PdfDictamen p")})
public class PdfDictamen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddictamen")
    private Integer iddictamen;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumn(name = "caso", referencedColumnName = "idcaso")
    @ManyToOne(optional = false)
    private Caso caso;

    public PdfDictamen() {
    }

    public PdfDictamen(Integer iddictamen) {
        this.iddictamen = iddictamen;
    }

    public PdfDictamen(Integer iddictamen, String descripcion) {
        this.iddictamen = iddictamen;
        this.descripcion = descripcion;
    }

    public Integer getIddictamen() {
        return iddictamen;
    }

    public void setIddictamen(Integer iddictamen) {
        this.iddictamen = iddictamen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Caso getCaso() {
        return caso;
    }

    public void setCaso(Caso caso) {
        this.caso = caso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddictamen != null ? iddictamen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PdfDictamen)) {
            return false;
        }
        PdfDictamen other = (PdfDictamen) object;
        if ((this.iddictamen == null && other.iddictamen != null) || (this.iddictamen != null && !this.iddictamen.equals(other.iddictamen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.PdfDictamen[ iddictamen=" + iddictamen + " ]";
    }
    
}
