package com.pid.proyecto.repository;

import com.pid.proyecto.entity.ComDiscUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ComDiscUsuarioRepository extends JpaRepository<ComDiscUsuario, Integer>{
    
}
