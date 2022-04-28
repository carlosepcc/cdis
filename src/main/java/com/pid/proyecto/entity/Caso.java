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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    @EmbeddedId
    protected CasoPK casoPK;
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
    private List<Declaracion> declaracionList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "caso")
    private Dictamen dictamen;
    @JoinColumn(name = "comision", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Comision comision1;
    @JoinColumn(name = "denuncia", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Denuncia denuncia1;

    public Caso() {
    }

    public Caso(CasoPK casoPK) {
        this.casoPK = casoPK;
    }

    public Caso(CasoPK casoPK, boolean abierto, Date fechaapertura, Date fechaexpiracion) {
        this.casoPK = casoPK;
        this.abierto = abierto;
        this.fechaapertura = fechaapertura;
        this.fechaexpiracion = fechaexpiracion;
    }

    public Caso(int denuncia, int comision) {
        this.casoPK = new CasoPK(denuncia, comision);
    }

    public CasoPK getCasoPK() {
        return casoPK;
    }

    public void setCasoPK(CasoPK casoPK) {
        this.casoPK = casoPK;
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

    public List<Declaracion> getDeclaracionList() {
        return declaracionList;
    }

    public void setDeclaracionList(List<Declaracion> declaracionList) {
        this.declaracionList = declaracionList;
    }

    public Dictamen getDictamen() {
        return dictamen;
    }

    public void setDictamen(Dictamen dictamen) {
        this.dictamen = dictamen;
    }

    public Comision getComision1() {
        return comision1;
    }

    public void setComision1(Comision comision1) {
        this.comision1 = comision1;
    }

    public Denuncia getDenuncia1() {
        return denuncia1;
    }

    public void setDenuncia1(Denuncia denuncia1) {
        this.denuncia1 = denuncia1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (casoPK != null ? casoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Caso)) {
            return false;
        }
        Caso other = (Caso) object;
        if ((this.casoPK == null && other.casoPK != null) || (this.casoPK != null && !this.casoPK.equals(other.casoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.Caso[ casoPK=" + casoPK + " ]";
    }
    
}
