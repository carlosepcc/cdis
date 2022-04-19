package com.pid.proyecto.repository;

import com.pid.proyecto.entity.PdfExpediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfExpedienteRepository extends JpaRepository<PdfExpediente, Integer> {

}
