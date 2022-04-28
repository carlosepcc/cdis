package com.pid.proyecto.repository;

import com.pid.proyecto.entity.Declaracion;
import com.pid.proyecto.entity.DeclaracionPK;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeclaracionRepo extends JpaRepository<Declaracion, Integer>{

    public Optional<Declaracion> findByDeclaracionPK(DeclaracionPK declaracionPK);
    
}
