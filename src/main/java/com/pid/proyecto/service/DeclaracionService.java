package com.pid.proyecto.service;

import com.pid.proyecto.entity.Declaracion;
import com.pid.proyecto.repository.DeclaracionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional //mejora el rendimiento y la coherencia de las consultas
public class DeclaracionService {

    @Autowired
    DeclaracionRepository declaracionRepo;

    public List<Declaracion> Listar() {
        return declaracionRepo.findAll();
    }

    public void save(Declaracion declaracion) {
        declaracionRepo.save(declaracion);
    }

    public Optional<Declaracion> findById(int id) {
        return declaracionRepo.findById(id);
    }

    public boolean ExistsByIddeclaracion(int id) {
        return declaracionRepo.existsById(id);
    }
}
