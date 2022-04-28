package com.pid.proyecto.service;

import com.pid.proyecto.entity.Denuncia;
import com.pid.proyecto.repository.DenunciaRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DenunciaService {
    
    @Autowired
    DenunciaRepo repo;
    
    public Denuncia save(Denuncia denuncia) {
        return repo.save(denuncia);
    }
    
    public List<Denuncia> findAll() {
        return repo.findAll();
    }
    
    public Denuncia findById(int id) {
        return repo.findById(id).get();
    }
    
    public void deleteAll(List<Denuncia> LD) {
        repo.deleteAll(LD);
    }
    
}
