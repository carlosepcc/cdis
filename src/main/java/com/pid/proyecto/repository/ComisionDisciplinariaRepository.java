package com.pid.proyecto.repository;

import com.pid.proyecto.entity.ComisionDisciplinaria;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ComisionDisciplinariaRepository extends JpaRepository<ComisionDisciplinaria, Integer>{

    public void deleteByIdcomision(int id);

    public boolean existsByIdcomision(int id);

    public Optional<ComisionDisciplinaria> findByIdcomision(int id);
    
}
