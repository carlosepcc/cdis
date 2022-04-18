package com.pid.proyecto.repository;

import com.pid.proyecto.entity.PdfExpediente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PdfExpedienteRepository extends JpaRepository<PdfExpediente, Integer>{

    public Optional<PdfExpediente> findByIdexpediente(int id);
    
}
