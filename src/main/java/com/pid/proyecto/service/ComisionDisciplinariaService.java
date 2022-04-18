package com.pid.proyecto.service;

import com.pid.proyecto.entity.ComisionDisciplinaria;
import com.pid.proyecto.entity.ComisionDisciplinaria;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.repository.ComisionDisciplinariaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional //mejora el rendimiento y la coherencia de las consultas
public class ComisionDisciplinariaService {

    @Autowired
    ComisionDisciplinariaRepository comisionDisciplinariaRepository;

    public List<ComisionDisciplinaria> Listar() {

        return comisionDisciplinariaRepository.findAll();
    }

    public void save(ComisionDisciplinaria comisionDisciplinaria) {

        comisionDisciplinariaRepository.save(comisionDisciplinaria);

    }

    public void delete(int id) {

        comisionDisciplinariaRepository.deleteByIdcomision(id);
    }

    public boolean existsById(int id) {
        return comisionDisciplinariaRepository.existsByIdcomision(id);
    }

    public Optional<ComisionDisciplinaria> getByIdcomision(int id) {

        return comisionDisciplinariaRepository.findByIdcomision(id);
    }
}
