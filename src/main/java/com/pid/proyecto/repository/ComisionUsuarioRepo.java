package com.pid.proyecto.repository;

import com.pid.proyecto.entity.ComisionUsuario;
import com.pid.proyecto.entity.ComisionUsuarioPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComisionUsuarioRepo extends JpaRepository<ComisionUsuario, Integer>{

    public ComisionUsuario findByComisionUsuarioPK(ComisionUsuarioPK comisionUsuarioPK);

    public void deleteByComisionUsuarioPK(ComisionUsuarioPK comisionUsuarioPK);
    
}
