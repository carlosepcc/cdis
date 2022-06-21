package com.pid.proyecto.service;

import com.pid.proyecto.entity.Resolucion;
import com.pid.proyecto.repository.ResolucionRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResolucionService {

    @Autowired
    ResolucionRepo repo;

    public Resolucion save(Resolucion resolucion) {
       return repo.save(resolucion);
    }

    public Resolucion findById(int id) {
        return repo.findById(id).get();
    }

    public void deleteAll(List<Resolucion> LR) {
        repo.deleteAll();
    }

    public List<Resolucion> findAll() {
       return repo.findAll();
    }

    public boolean existsById(int id) {
       return repo.existsById(id);
    }

    public void deleteById(int id) {
        repo.deleteById(id);
    }

}
