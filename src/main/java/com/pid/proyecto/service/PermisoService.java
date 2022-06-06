package com.pid.proyecto.service;

import com.pid.proyecto.entity.Permiso;
import com.pid.proyecto.repository.PermisoRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermisoService {

    @Autowired
    PermisoRepo repo;

    public boolean existsById(int id) {
        return repo.existsById(id);
    }

    public Permiso findById(int id) {
        return repo.findById(id).get();
    }

    public Permiso findByPermiso(String permiso) {
        return repo.findByPermiso(permiso).get();
    }

    public List<Permiso> findAll() {
        return repo.findAll();
    }

    public List<Permiso> saveAll(List<Permiso> TodosLosPermisos) {
        return repo.saveAll(TodosLosPermisos);
    }

}
