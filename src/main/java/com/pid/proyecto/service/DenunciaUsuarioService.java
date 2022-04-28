package com.pid.proyecto.service;

import com.pid.proyecto.entity.DenunciaUsuario;
import com.pid.proyecto.repository.DenunciaUsuarioRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DenunciaUsuarioService {

    @Autowired
    DenunciaUsuarioRepo repo;

    public void save(DenunciaUsuario denunciaUsuario) {
        repo.save(denunciaUsuario);

    }

}
