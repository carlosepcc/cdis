/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pid.proyecto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
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

/**
 *
 * @author Angel
 */
@Entity
@Table(name = "comision_disciplinaria")
@NamedQueries({
    @NamedQuery(name = "ComisionDisciplinaria.findAll", query = "SELECT c FROM ComisionDisciplinaria c")})
public class ComisionDisciplinaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcomision")
    private Integer idcomision;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comisionDisciplinaria")
    @JsonIgnore
    private List<ComDiscUsuario> comDiscUsuarioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comision")
    @JsonIgnore
    private List<Caso> casoList;
    @JoinColumn(name = "resolucion", referencedColumnName = "idresolucion")
    @ManyToOne(optional = false)
    private PdfResolucionComisiones resolucion;

    public ComisionDisciplinaria() {
    }

    public ComisionDisciplinaria(Integer idcomision) {
        this.idcomision = idcomision;
    }

    public Integer getIdcomision() {
        return idcomision;
    }

    public void setIdcomision(Integer idcomision) {
        this.idcomision = idcomision;
    }

    public List<ComDiscUsuario> getComDiscUsuarioList() {
        return comDiscUsuarioList;
    }

    public void setComDiscUsuarioList(List<ComDiscUsuario> comDiscUsuarioList) {
        this.comDiscUsuarioList = comDiscUsuarioList;
    }

    public List<Caso> getCasoList() {
        return casoList;
    }

    public void setCasoList(List<Caso> casoList) {
        this.casoList = casoList;
    }

    public PdfResolucionComisiones getResolucion() {
        return resolucion;
    }

    public void setResolucion(PdfResolucionComisiones resolucion) {
        this.resolucion = resolucion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcomision != null ? idcomision.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComisionDisciplinaria)) {
            return false;
        }
        ComisionDisciplinaria other = (ComisionDisciplinaria) object;
        if ((this.idcomision == null && other.idcomision != null) || (this.idcomision != null && !this.idcomision.equals(other.idcomision))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.ComisionDisciplinaria[ idcomision=" + idcomision + " ]";
    }
    
}
