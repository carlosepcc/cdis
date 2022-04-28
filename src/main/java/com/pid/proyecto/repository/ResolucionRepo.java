package com.pid.proyecto.repository;

import com.pid.proyecto.entity.Resolucion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResolucionRepo extends JpaRepository<Resolucion, Integer>{
    
}
