/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pid.proyecto.service;

import com.pid.proyecto.entity.RolPermiso;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.pid.proyecto.repository.RolPermisoRepository;

/**
 *
 * @author Angel
 */
@Service
@Transactional
public class RolPermisoService {
    
    @Autowired
    RolPermisoRepository rolPermisosRepository;

    public List<RolPermiso> Listar() {

        return rolPermisosRepository.findAll();
    }

    public void save(RolPermiso rolPermisos) {

        rolPermisosRepository.save(rolPermisos);

    }

    public void saveAll(List<RolPermiso> rolPermisos) {

        for (RolPermiso rolPermiso : rolPermisos) {
            rolPermisosRepository.save(rolPermiso);
        }
    }
    
}
