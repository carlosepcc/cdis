package com.pid.proyecto.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class DictamenPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "casodenuncia")
    private int casodenuncia;
    @Basic(optional = false)
    @NotNull
    @Column(name = "casocomision")
    private int casocomision;

    public DictamenPK() {
    }

    public DictamenPK(int casodenuncia, int casocomision) {
        this.casodenuncia = casodenuncia;
        this.casocomision = casocomision;
    }

    public int getCasodenuncia() {
        return casodenuncia;
    }

    public void setCasodenuncia(int casodenuncia) {
        this.casodenuncia = casodenuncia;
    }

    public int getCasocomision() {
        return casocomision;
    }

    public void setCasocomision(int casocomision) {
        this.casocomision = casocomision;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) casodenuncia;
        hash += (int) casocomision;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DictamenPK)) {
            return false;
        }
        DictamenPK other = (DictamenPK) object;
        if (this.casodenuncia != other.casodenuncia) {
            return false;
        }
        if (this.casocomision != other.casocomision) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pid.proyecto.entity.DictamenPK[ casodenuncia=" + casodenuncia + ", casocomision=" + casocomision + " ]";
    }
    
}
