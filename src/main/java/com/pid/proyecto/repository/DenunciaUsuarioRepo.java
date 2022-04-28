package com.pid.proyecto.repository;

import com.pid.proyecto.entity.DenunciaUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DenunciaUsuarioRepo extends JpaRepository<DenunciaUsuario, Integer>{
    
}
