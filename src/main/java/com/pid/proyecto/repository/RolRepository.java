package com.pid.proyecto.repository;

import com.pid.proyecto.entity.Rol;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

    // buscamos el rol por el nombre del rol (rol)
    Optional<Rol> findByRol(String rol);

    public boolean existsByRol(String rol);
    public boolean existsByIdrol(int idrol);

}
