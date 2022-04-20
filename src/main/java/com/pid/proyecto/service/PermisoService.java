/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pid.proyecto.service;

import com.pid.proyecto.entity.Permiso;
import com.pid.proyecto.repository.PermisoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermisoService {
  // inyectamos
  @Autowired
  PermisoRepository permisosRepository;

  public List<Permiso> findAll() {
    return permisosRepository.findAll();
  }

  // devolvemos el permiso
  public Optional<Permiso> findByPermiso(String permisoNombre) {
    return permisosRepository.findByPermiso(permisoNombre);
  }
  public Optional<Permiso> findByIdPermiso(int id) {
    return permisosRepository.findById(id);
  }

  public void save(Permiso permiso) {
    permisosRepository.save(permiso);
  }

  public void saveAll(List<Permiso> permisos) {
    permisosRepository.saveAll(permisos);
  }

  public boolean existsByPermiso(String permiso) {
    return permisosRepository.existsByPermiso(permiso);
  }

  public boolean existsByIdpermiso(int idPermiso) {
    return permisosRepository.existsByIdpermiso(idPermiso);
  }
}
