package com.pid.proyecto.repository;

import com.pid.proyecto.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // obtenemos un Usuario con el username (usuario)
    Optional<Usuario> findByUsuario(String usuario);

    // comprobamos si existe el usuario con username (usuario)
    boolean existsByUsuario(String usuario);

    // comprobamos si existe el usuario con apellido (apellido)
    boolean existsByApellidos(String apellidos);

}
