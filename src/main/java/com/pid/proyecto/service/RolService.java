package com.pid.proyecto.service;

import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.repository.RolRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RolService {

    @Autowired
    RolRepo repo;

    public Rol save(Rol rol) {
       return repo.save(rol);
    }

    public Rol findByRol(String rol) {
        return repo.findByRol(rol).get();
    }

    public List<Rol> findAll() {
        return repo.findAll();
    }

    public Rol findById(int id) {
        return repo.findById(id).get();
    }

    public boolean existsByRol(String rol) {
        return repo.existsByRol(rol);
    }

    public boolean existsById(int id) {
        return repo.existsById(id);
    }

    public void deleteAll(List<Rol> LR) {
        repo.deleteAll(LR);
    }

    public void saveAll(List<Rol> TodosLosRoles) {
        repo.saveAll(TodosLosRoles);
    }

}
