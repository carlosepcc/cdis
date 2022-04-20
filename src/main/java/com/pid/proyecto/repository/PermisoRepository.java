package com.pid.proyecto.repository;

import com.pid.proyecto.entity.Permiso;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermisoRepository extends JpaRepository<Permiso, Integer> {

    // buscamos el rol por el nombre del rol (rol)
    Optional<Permiso> findByPermiso(String rol);

    public boolean existsByPermiso(String rol);
    public boolean existsByIdpermiso(int idPermiso);


}
