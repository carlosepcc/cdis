package com.pid.proyecto.service;

import com.pid.proyecto.entity.RolPermiso;
import com.pid.proyecto.entity.RolPermisoPK;
import com.pid.proyecto.repository.RolPermisoRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RolPermisoService {
    
    @Autowired
    RolPermisoRepo repo;

    public List<RolPermiso> findAll() {
        return repo.findAll();
    }
    public List<RolPermiso> findAllByRol(String rol) {
        return repo.findAllByRol(rol);
    }

    public void saveAll(List<RolPermiso> LRP) {
        repo.saveAll(LRP);
    }

    public void deleteAll(List<RolPermiso> LRP) {
        repo.deleteAll(LRP);
    }

    public RolPermiso findByRolPermisoPK(RolPermisoPK rolPermisosPK) {
        return repo.findByRolPermisoPK(rolPermisosPK).get();
    }

    public boolean existByRolPermisoPK(RolPermisoPK rolPermisosPK) {
        return repo.existsByRolPermisoPK(rolPermisosPK);
    }


    public void delete(RolPermiso RP) {
        repo.delete(RP);
    }
    
    
    
}
