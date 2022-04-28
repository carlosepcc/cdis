package com.pid.proyecto.repository;

import com.pid.proyecto.entity.Rol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepo extends JpaRepository<Rol, Integer>{

    public Optional<Rol> findByRol(String rol);

    public boolean existsByRol(String rol);
    
}
