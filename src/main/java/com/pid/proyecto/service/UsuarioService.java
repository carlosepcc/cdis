package com.pid.proyecto.service;

import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.repository.UsuarioRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional //mejora el rendimiento y la coherencia de las consultas
public class UsuarioService {
  //inyectamos UsuarioRepository
  @Autowired
  UsuarioRepository usuarioRepo;

  // Obtenemos el Usuario mediante el nombre de usuario (usuario)
  public Optional<Usuario> getByUsuario(String usuario) {
    return usuarioRepo.findByUsuario(usuario);
  }

  // Comprobamos si existe el usuario con nombre de usuario (usuario)
  public boolean existsByUsuario(String usuario) {
    return usuarioRepo.existsByUsuario(usuario);
  }

  public boolean existsById(int id) {
    return usuarioRepo.existsById(id);
  }

  // salvamos el objeto usuario en la base de datos
  public void save(Usuario usuario) {
    usuarioRepo.save(usuario);
  }

  public Optional<Usuario> getByIdusuario(int id) {
    return usuarioRepo.findById(id);
  }

  public void delete(int id) {
    usuarioRepo.deleteById(id);
  }

  public void deleteByUsuario(Usuario us) {
    usuarioRepo.delete(us);
  }

  public void deleteAll(List<Usuario> us) {
    usuarioRepo.deleteAll(us);
  }

  public List<Usuario> findAll() {
    return usuarioRepo.findAll();
  }
}
