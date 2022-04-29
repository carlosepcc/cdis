package com.pid.proyecto.repository;

import com.pid.proyecto.entity.RolPermiso;
import com.pid.proyecto.entity.RolPermisoPK;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolPermisoRepo extends JpaRepository<RolPermiso, Integer>{

    public Optional<RolPermiso> findByRolPermisoPK(RolPermisoPK rolPermisosPK);

    public boolean existsByRolPermisoPK(RolPermisoPK rolPermisosPK);

    public void deleteByRolPermisoPK(RolPermisoPK PK);

    public List<RolPermiso> findAllByRol(String rol);
    
}
