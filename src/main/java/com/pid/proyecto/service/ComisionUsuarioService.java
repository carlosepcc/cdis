package com.pid.proyecto.service;

import com.pid.proyecto.entity.Comision;
import com.pid.proyecto.entity.ComisionUsuario;
import com.pid.proyecto.entity.ComisionUsuarioPK;
import com.pid.proyecto.repository.ComisionUsuarioRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ComisionUsuarioService {

    @Autowired
    ComisionUsuarioRepo repo;

    public void save(ComisionUsuario comisionUsuario) {
        repo.save(comisionUsuario);
    }

    public ComisionUsuario findByComisionUsuarioPK(ComisionUsuarioPK comisionUsuarioPK) {
        return repo.findByComisionUsuarioPK(comisionUsuarioPK);
    }

    public void saveAll(List<ComisionUsuario> CU) {
        repo.saveAll(CU);
    }

    public void delete(ComisionUsuario comisionUsuario) {
        repo.delete(comisionUsuario);
    }

    public void deleteAll(List<ComisionUsuario> LCU) {
        repo.deleteAll(LCU);
    }

    public void deleteByComisionUsuarioPK(ComisionUsuarioPK comisionUsuarioPK) {
        
        repo.deleteByComisionUsuarioPK(comisionUsuarioPK);
    }

    public List<ComisionUsuario> findAllByComision(Comision comision) {
        return repo.findAllByComision(comision);
    }


}
