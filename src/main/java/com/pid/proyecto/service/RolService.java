package com.pid.proyecto.service;

import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.repository.RolRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional //mejora el rendimiento de las consultas
public class RolService {
  // inyectamos
  @Autowired
  RolRepository rolRepo;

  // devolvemos el rol con nombre (rolNombre)
  public Optional<Rol> getByRol(String rol) {
    return rolRepo.findByRol(rol);
  }

  public Optional<Rol> getByIdRol(int id) {
    return rolRepo.findById(id);
  }

  public void save(Rol rol) {
    rolRepo.save(rol);
  }

  public void saveAll(List<Rol> roles) {
    rolRepo.saveAll(roles);
  }

  public List<Rol> findAll() {
    return rolRepo.findAll();
  }

  public boolean existsByIdrol(int idrol) {
    return rolRepo.existsByIdrol(idrol);
  }
  public boolean existsByRol(String rol) {
    return rolRepo.existsByRol(rol);
  }

public void deleteAll(List<Rol> lista) {
  rolRepo.deleteAll(lista);
}
}
