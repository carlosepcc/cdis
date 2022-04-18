package com.pid.proyecto.service;

import com.pid.proyecto.entity.PdfExpediente;
import com.pid.proyecto.repository.PdfExpedienteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional //mejora el rendimiento y la coherencia de las consultas
public class ExpedienteService {

    @Autowired
    PdfExpedienteRepository pdfExpedienteRepo;

    public List<PdfExpediente> Listar() {

        return pdfExpedienteRepo.findAll();
    }

    public void save(PdfExpediente expediente) {
        
        pdfExpedienteRepo.save(expediente);
    }
    
     public Optional<PdfExpediente> getByIdexpediente(int id) {

        return pdfExpedienteRepo.findByIdexpediente(id);
    }
}
