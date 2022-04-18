package com.pid.proyecto.repository;

import com.pid.proyecto.entity.Denuncia;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface DenunciaRepository extends JpaRepository<Denuncia, Integer>{

    public boolean existsByIddenuncia(int id);

    public Optional<Denuncia> findByIddenuncia(int id);
    
}
