package com.pid.proyecto.service;

import com.pid.proyecto.entity.Caso;
import com.pid.proyecto.entity.Denuncia;
import com.pid.proyecto.repository.CasoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional //mejora el rendimiento y la coherencia de las consultas
public class CasoService {
    
    @Autowired
    CasoRepository casoRepo;
    
     public List<Caso> Listar() {
        
        return casoRepo.findAll();
    }
    
     // salvamos el objeto caso en la base de datos
    public void save(Caso caso) {

        casoRepo.save(caso);
    }
    
    public boolean existsByDenuncia(Denuncia iddenuncia) {
        return casoRepo.existsByDenuncia(iddenuncia);
    }
    
    public Optional<Caso> getById(int id) {

        return casoRepo.findByIdcaso(id);
    }

    public boolean existByIdcaso(int i) {
       return casoRepo.existsByIdcaso(i);
    }
}
