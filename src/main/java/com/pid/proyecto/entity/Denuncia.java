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
@Table(name = "denuncia")
@NamedQueries({
    @NamedQuery(name = "Denuncia.findAll", query = "SELECT d FROM Denuncia d")})
public class Denuncia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddenuncia")
    private Integer iddenuncia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descripcionden")
    private String descripcionden;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @NotNull
    @Column(name = "procesada")
    private boolean procesada;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "denuncia")
    @JsonIgnore
    private List<Caso> casoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "denuncia")
    @JsonIgnore
    private List<DenunciaUsuario> denunciaUsuarioList;

    public Denuncia() {
    }

    public Denuncia(Integer iddenuncia) {
        this.iddenuncia = iddenuncia;
    }

    public Denuncia(Integer iddenuncia, String descripcionden, Date fecha, boolean procesada) {
        this.iddenuncia = iddenuncia;
        this.descripcionden = descripcionden;
        this.fecha = fecha;
        this.procesada = procesada;
    }

    public Integer getIddenuncia() {
        return iddenuncia;
    }

    public void setIddenuncia(Integer iddenuncia) {
        this.iddenuncia = iddenuncia;
    }

    public String getDescripcionden() {
        return descripcionden;
    }

    public void setDescripcionden(String descripcionden) {
        this.descripcionden = descripcionden;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean getProcesada() {
        return procesada;
    }

    public void setProcesada(boolean procesada) {
        this.procesada = procesada;
    }

    public List<Caso> getCasoList() {
        return casoList;
    }

    public void setCasoList(List<Caso> casoList) {
        this.casoList = casoList;
    }

    public List<DenunciaUsuario> getDenunciaUsuarioList() {
        return denunciaUsuarioList;
    }

    public void setDenunciaUsuarioList(List<DenunciaUsuario> denunciaUsuarioList) {
        this.denunciaUsuarioList = denunciaUsuarioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddenuncia != null ? iddenuncia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Denuncia)) {
            return false;
        }
        Denuncia other = (Denuncia) object;
        if ((this.iddenuncia == null && other.iddenuncia != null) || (this.iddenuncia != null && !this.iddenuncia.equals(other.iddenuncia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.Denuncia[ iddenuncia=" + iddenuncia + " ]";
    }
    
}
