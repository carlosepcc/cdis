package com.pid.proyecto.repository;

import com.pid.proyecto.entity.Caso;
import com.pid.proyecto.entity.CasoPK;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasoRepo extends JpaRepository<Caso, Integer>{

    public Optional<Caso> findByCasoPK(CasoPK id);

    public boolean existsByCasoPK(CasoPK casoPK);

    public void deleteByCasoPK(CasoPK PK);

    public List<Caso> findAllByAbierto(boolean b);

    
}
