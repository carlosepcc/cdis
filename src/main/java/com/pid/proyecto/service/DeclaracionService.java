package com.pid.proyecto.service;

import com.pid.proyecto.entity.Declaracion;
import com.pid.proyecto.entity.DeclaracionPK;
import com.pid.proyecto.repository.DeclaracionRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeclaracionService {

    @Autowired
    DeclaracionRepo repo;

    public void save(Declaracion declaracion) {
        repo.save(declaracion);
    }

    public List<Declaracion> findAll() {
        return repo.findAll();
    }

    public Declaracion findByDeclaracionPK(DeclaracionPK declaracionPK) {
       return repo.findByDeclaracionPK(declaracionPK).get();
    }

    public void deleteAll(List<Declaracion> LD) {
        repo.deleteAll(LD);
    }

}
