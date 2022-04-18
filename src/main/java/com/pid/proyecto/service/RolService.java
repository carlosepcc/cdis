package com.pid.proyecto.service;

import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.seguridad.enums.RolNombre;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pid.proyecto.repository.RolRepository;

@Service
@Transactional //mejora el rendimiento de las consultas
public class RolService {

    // inyectamos
    @Autowired
    RolRepository rolSistemaRepository;

    public List<Rol> Listar() {

        return rolSistemaRepository.findAll();
    }

    // devolvemos el rol con nombre (rolNombre)
    public Optional<Rol> getByRol(String rolNombre) {
        return rolSistemaRepository.findByRol(rolNombre);
    }

    public void save(Rol rol) {

        rolSistemaRepository.save(rol);

    }

    public void saveAll(List<Rol> roles) {

        rolSistemaRepository.saveAll(roles);

    }

    public List<Rol> findAll() {
        return rolSistemaRepository.findAll();
    }

    public boolean existsByRol(String rol) {

        return rolSistemaRepository.existsByRol(rol);

    }

    public boolean existsByIdrol(int idrol) {

        return rolSistemaRepository.existsByIdrol(idrol);

    }
}
