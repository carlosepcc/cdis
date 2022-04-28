package com.pid.proyecto.repository;

import com.pid.proyecto.entity.Denuncia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DenunciaRepo extends JpaRepository<Denuncia, Integer>{
    
}
