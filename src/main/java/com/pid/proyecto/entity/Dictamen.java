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
@Table(name = "dictamen")
@NamedQueries({
    @NamedQuery(name = "Dictamen.findAll", query = "SELECT d FROM Dictamen d")})
public class Dictamen implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DictamenPK dictamenPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcion")
    private String descripcion;
    @JoinColumns({
        @JoinColumn(name = "casodenuncia", referencedColumnName = "denuncia", insertable = false, updatable = false),
        @JoinColumn(name = "casocomision", referencedColumnName = "comision", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Caso caso;

    public Dictamen() {
    }

    public Dictamen(DictamenPK dictamenPK) {
        this.dictamenPK = dictamenPK;
    }

    public Dictamen(DictamenPK dictamenPK, String descripcion) {
        this.dictamenPK = dictamenPK;
        this.descripcion = descripcion;
    }

    public Dictamen(int casodenuncia, int casocomision) {
        this.dictamenPK = new DictamenPK(casodenuncia, casocomision);
    }

    public DictamenPK getDictamenPK() {
        return dictamenPK;
    }

    public void setDictamenPK(DictamenPK dictamenPK) {
        this.dictamenPK = dictamenPK;
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
        hash += (dictamenPK != null ? dictamenPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dictamen)) {
            return false;
        }
        Dictamen other = (Dictamen) object;
        if ((this.dictamenPK == null && other.dictamenPK != null) || (this.dictamenPK != null && !this.dictamenPK.equals(other.dictamenPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.Dictamen[ dictamenPK=" + dictamenPK + " ]";
    }
    
}
