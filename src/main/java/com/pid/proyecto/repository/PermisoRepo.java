package com.pid.proyecto.repository;

import com.pid.proyecto.entity.Permiso;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermisoRepo extends JpaRepository<Permiso, Integer>{

    public Optional<Permiso> findByPermiso(String permiso);
    
}
