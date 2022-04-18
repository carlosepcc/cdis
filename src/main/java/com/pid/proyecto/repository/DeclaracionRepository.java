package com.pid.proyecto.repository;

import com.pid.proyecto.entity.Declaracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DeclaracionRepository extends JpaRepository<Declaracion, Integer>{
    
}
