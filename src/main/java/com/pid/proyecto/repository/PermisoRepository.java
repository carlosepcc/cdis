package com.pid.proyecto.repository;

import com.pid.proyecto.entity.Permiso;
import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.enums.PermisoNombre;
import java.util.List;
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
