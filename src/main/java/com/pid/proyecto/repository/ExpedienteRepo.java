package com.pid.proyecto.repository;

import com.pid.proyecto.entity.Expediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpedienteRepo extends JpaRepository<Expediente, Integer> {

}
