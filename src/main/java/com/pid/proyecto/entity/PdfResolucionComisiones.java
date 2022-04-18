/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pid.proyecto.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Angel
 */
@Entity
@Table(name = "pdf_resolucion_comisiones")
@NamedQueries({
    @NamedQuery(name = "PdfResolucionComisiones.findAll", query = "SELECT p FROM PdfResolucionComisiones p")})
public class PdfResolucionComisiones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idresolucion")
    private Integer idresolucion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechacreacion")
    @Temporal(TemporalType.DATE)
    private Date fechacreacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaexpiracion")
    @Temporal(TemporalType.DATE)
    private Date fechaexpiracion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resolucion")
    private List<ComisionDisciplinaria> comisionDisciplinariaList;

    public PdfResolucionComisiones() {
    }

    public PdfResolucionComisiones(Integer idresolucion) {
        this.idresolucion = idresolucion;
    }

    public PdfResolucionComisiones(Integer idresolucion, Date fechacreacion, Date fechaexpiracion) {
        this.idresolucion = idresolucion;
        this.fechacreacion = fechacreacion;
        this.fechaexpiracion = fechaexpiracion;
    }

    public Integer getIdresolucion() {
        return idresolucion;
    }

    public void setIdresolucion(Integer idresolucion) {
        this.idresolucion = idresolucion;
    }

    public Date getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(Date fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public Date getFechaexpiracion() {
        return fechaexpiracion;
    }

    public void setFechaexpiracion(Date fechaexpiracion) {
        this.fechaexpiracion = fechaexpiracion;
    }

    public List<ComisionDisciplinaria> getComisionDisciplinariaList() {
        return comisionDisciplinariaList;
    }

    public void setComisionDisciplinariaList(List<ComisionDisciplinaria> comisionDisciplinariaList) {
        this.comisionDisciplinariaList = comisionDisciplinariaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idresolucion != null ? idresolucion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PdfResolucionComisiones)) {
            return false;
        }
        PdfResolucionComisiones other = (PdfResolucionComisiones) object;
        if ((this.idresolucion == null && other.idresolucion != null) || (this.idresolucion != null && !this.idresolucion.equals(other.idresolucion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.PdfResolucionComisiones[ idresolucion=" + idresolucion + " ]";
    }
    
}
