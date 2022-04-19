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
public class PdfExpedienteService {

    @Autowired
    PdfExpedienteRepository pdfExpedienteRepo;

    public List<PdfExpediente> Listar() {

        return pdfExpedienteRepo.findAll();
    }

    public void save(PdfExpediente expediente) {

        pdfExpedienteRepo.save(expediente);
    }

    public Optional<PdfExpediente> getByIdexpediente(int id) {

        return pdfExpedienteRepo.findById(id);
    }

    public boolean ExistByIdexpediente(int id) {

        return pdfExpedienteRepo.existsById(id);
    }

    public void DeleteByIdExpediente(int id) {
        
        pdfExpedienteRepo.deleteById(id);
    }
}
