
package com.pid.proyecto.repository;

import com.pid.proyecto.entity.Comision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComisionRepo extends JpaRepository<Comision, Integer>{
    
}
