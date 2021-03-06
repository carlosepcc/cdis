package com.pid.proyecto.service;

import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.repository.UsuarioRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    UsuarioRepo repo;

    public void save(Usuario usuario) {
        repo.save(usuario);
    }

    public List<Usuario> findAll() {
        return repo.findAll();
    }

    public void deleteAll(List<Usuario> LU) {
        repo.deleteAll(LU);
    }

    public boolean existsByUsuario(String n) {
        return repo.existsByUsuario(n);
    }

    public Usuario findByUsuario(String n) {
        return repo.findByUsuario(n).get();
    }

}
