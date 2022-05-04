
package com.pid.proyecto.repository;

import com.pid.proyecto.entity.Comision;
import com.pid.proyecto.entity.Resolucion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComisionRepo extends JpaRepository<Comision, Integer>{

    public List<Comision> findAllByResolucion(Resolucion resolucion);
    
}
