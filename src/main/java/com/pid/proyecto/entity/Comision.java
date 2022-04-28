/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pid.proyecto.entity;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Angel
 */
@Entity
@Table(name = "comision")
@NamedQueries({
    @NamedQuery(name = "Comision.findAll", query = "SELECT c FROM Comision c")})
public class Comision implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "pusuario")
    private String pusuario;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "susuario")
    private String susuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comision1")
    private List<Caso> casoList;
    @JoinColumn(name = "resolucion", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Resolucion resolucion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "comision")
    private List<ComisionUsuario> comisionUsuarioList;

    public Comision() {
    }

    public Comision(Integer id) {
        this.id = id;
    }

    public Comision(Integer id, String pusuario, String susuario) {
        this.id = id;
        this.pusuario = pusuario;
        this.susuario = susuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPusuario() {
        return pusuario;
    }

    public void setPusuario(String pusuario) {
        this.pusuario = pusuario;
    }

    public String getSusuario() {
        return susuario;
    }

    public void setSusuario(String susuario) {
        this.susuario = susuario;
    }

    public List<Caso> getCasoList() {
        return casoList;
    }

    public void setCasoList(List<Caso> casoList) {
        this.casoList = casoList;
    }

    public Resolucion getResolucion() {
        return resolucion;
    }

    public void setResolucion(Resolucion resolucion) {
        this.resolucion = resolucion;
    }

    public List<ComisionUsuario> getComisionUsuarioList() {
        return comisionUsuarioList;
    }

    public void setComisionUsuarioList(List<ComisionUsuario> comisionUsuarioList) {
        this.comisionUsuarioList = comisionUsuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comision)) {
            return false;
        }
        Comision other = (Comision) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.Comision[ id=" + id + " ]";
    }
    
}
