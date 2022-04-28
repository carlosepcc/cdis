package com.pid.proyecto.service;

import com.pid.proyecto.entity.Comision;
import com.pid.proyecto.repository.ComisionRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ComisionService {

    @Autowired
    ComisionRepo repo;

    public Comision save(Comision comision) {
         return repo.save(comision);
    }

    public List<Comision> findAll() {
        return repo.findAll();
    }

    public Comision findById(int id) {
       return repo.findById(id).get();
    }

    public void deleteAll(List<Comision> LO) {
        repo.deleteAll(LO);
    }

}
