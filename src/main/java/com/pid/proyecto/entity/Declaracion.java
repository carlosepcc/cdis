/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pid.proyecto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Angel
 */
@Entity
@Table(name = "declaracion")
@NamedQueries({
    @NamedQuery(name = "Declaracion.findAll", query = "SELECT d FROM Declaracion d")})
public class Declaracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddeclaracion")
    private Integer iddeclaracion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "abierta")
    private boolean abierta;
    @Size(max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @JoinColumn(name = "caso", referencedColumnName = "idcaso")
    @ManyToOne(optional = false)
    private Caso caso;
    @JoinColumn(name = "usuario", referencedColumnName = "idusuario")
    @ManyToOne(optional = false)
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "declaracion")
    @JsonIgnore
    private List<PdfExpediente> pdfExpedienteList;

    public Declaracion() {
    }

    public Declaracion(Integer iddeclaracion) {
        this.iddeclaracion = iddeclaracion;
    }

    public Declaracion(Integer iddeclaracion, boolean abierta) {
        this.iddeclaracion = iddeclaracion;
        this.abierta = abierta;
    }

    public Integer getIddeclaracion() {
        return iddeclaracion;
    }

    public void setIddeclaracion(Integer iddeclaracion) {
        this.iddeclaracion = iddeclaracion;
    }

    public boolean getAbierta() {
        return abierta;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Caso getCaso() {
        return caso;
    }

    public void setCaso(Caso caso) {
        this.caso = caso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<PdfExpediente> getPdfExpedienteList() {
        return pdfExpedienteList;
    }

    public void setPdfExpedienteList(List<PdfExpediente> pdfExpedienteList) {
        this.pdfExpedienteList = pdfExpedienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddeclaracion != null ? iddeclaracion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Declaracion)) {
            return false;
        }
        Declaracion other = (Declaracion) object;
        if ((this.iddeclaracion == null && other.iddeclaracion != null) || (this.iddeclaracion != null && !this.iddeclaracion.equals(other.iddeclaracion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.Declaracion[ iddeclaracion=" + iddeclaracion + " ]";
    }
    
}
