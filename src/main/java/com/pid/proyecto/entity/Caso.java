/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pid.proyecto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

/**
 *
 * @author Angel
 */
@Entity
@Table(name = "caso")
@NamedQueries({
    @NamedQuery(name = "Caso.findAll", query = "SELECT c FROM Caso c")})
public class Caso implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcaso")
    private Integer idcaso;
    @Basic(optional = false)
    @NotNull
    @Column(name = "abierto")
    private boolean abierto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaapertura")
    @Temporal(TemporalType.DATE)
    private Date fechaapertura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechaexpiracion")
    @Temporal(TemporalType.DATE)
    private Date fechaexpiracion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "caso")
    @JsonIgnore
    private List<PdfDictamen> pdfDictamenList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "caso")
    @JsonIgnore
    private List<Declaracion> declaracionList;
    @JoinColumn(name = "comision", referencedColumnName = "idcomision")
    @ManyToOne(optional = false)
    private ComisionDisciplinaria comision;
    @JoinColumn(name = "denuncia", referencedColumnName = "iddenuncia")
    @ManyToOne(optional = false)
    private Denuncia denuncia;

    public Caso() {
    }

    public Caso(Integer idcaso) {
        this.idcaso = idcaso;
    }

    public Caso(Integer idcaso, boolean abierto, Date fechaapertura, Date fechaexpiracion) {
        this.idcaso = idcaso;
        this.abierto = abierto;
        this.fechaapertura = fechaapertura;
        this.fechaexpiracion = fechaexpiracion;
    }

    public Integer getIdcaso() {
        return idcaso;
    }

    public void setIdcaso(Integer idcaso) {
        this.idcaso = idcaso;
    }

    public boolean getAbierto() {
        return abierto;
    }

    public void setAbierto(boolean abierto) {
        this.abierto = abierto;
    }

    public Date getFechaapertura() {
        return fechaapertura;
    }

    public void setFechaapertura(Date fechaapertura) {
        this.fechaapertura = fechaapertura;
    }

    public Date getFechaexpiracion() {
        return fechaexpiracion;
    }

    public void setFechaexpiracion(Date fechaexpiracion) {
        this.fechaexpiracion = fechaexpiracion;
    }

    public List<PdfDictamen> getPdfDictamenList() {
        return pdfDictamenList;
    }

    public void setPdfDictamenList(List<PdfDictamen> pdfDictamenList) {
        this.pdfDictamenList = pdfDictamenList;
    }

    public List<Declaracion> getDeclaracionList() {
        return declaracionList;
    }

    public void setDeclaracionList(List<Declaracion> declaracionList) {
        this.declaracionList = declaracionList;
    }

    public ComisionDisciplinaria getComision() {
        return comision;
    }

    public void setComision(ComisionDisciplinaria comision) {
        this.comision = comision;
    }

    public Denuncia getDenuncia() {
        return denuncia;
    }

    public void setDenuncia(Denuncia denuncia) {
        this.denuncia = denuncia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcaso != null ? idcaso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caso)) {
            return false;
        }
        Caso other = (Caso) object;
        if ((this.idcaso == null && other.idcaso != null) || (this.idcaso != null && !this.idcaso.equals(other.idcaso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.Caso[ idcaso=" + idcaso + " ]";
    }
    
}
