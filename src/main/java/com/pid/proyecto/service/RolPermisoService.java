package com.pid.proyecto.service;

import com.pid.proyecto.entity.RolPermiso;
import com.pid.proyecto.repository.RolPermisoRepo;
import java.util.List;
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

    public void saveAll(List<RolPermiso> LRP) {
        repo.saveAll(LRP);
    }

    public void deleteAll(List<RolPermiso> LRP) {
        repo.deleteAll(LRP);
    }
    
    
    
}
