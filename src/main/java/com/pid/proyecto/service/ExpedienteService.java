package com.pid.proyecto.service;

import com.pid.proyecto.entity.Expediente;
import com.pid.proyecto.entity.ExpedientePK;
import com.pid.proyecto.repository.ExpedienteRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExpedienteService {

    @Autowired
    ExpedienteRepo repo;

    public List<Expediente> findAll() {
        return repo.findAll();
    }

    public void save(Expediente expediente) {
        repo.save(expediente);
    }

    public Expediente findByExpedientePK(ExpedientePK PK) {
        return repo.findByExpedientePK(PK).get();
    }

    public void deleteAll(List<Expediente> LE) {
        repo.deleteAll(LE);
    }

    public boolean existsByExpedientePK(ExpedientePK EPK) {
        return repo.existsByExpedientePK(EPK);
    }

}
