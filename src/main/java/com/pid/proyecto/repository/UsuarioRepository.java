package com.pid.proyecto.repository;

import com.pid.proyecto.entity.Usuario;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // obtenemos un Usuario con el username (usuario)
    Optional<Usuario> findByUsuario(String usuario);
    
    // obtenemos un Usuario con el username (usuario)
    Optional<Usuario> findByIdusuario(int id);
    
    // comprobamos si existe el usuario con username (usuario)
    boolean existsByUsuario(String usuario);

    // comprobamos si existe el usuario con apellido (apellido)
    boolean existsByApellidos(String apellidos);
    
    boolean existsByIdusuario(int id);
    
    void deleteByIdusuario(int id);
    
}
