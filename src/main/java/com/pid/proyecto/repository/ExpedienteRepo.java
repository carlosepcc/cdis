package com.pid.proyecto.repository;

import com.pid.proyecto.entity.Expediente;
import com.pid.proyecto.entity.ExpedientePK;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpedienteRepo extends JpaRepository<Expediente, Integer> {

    public Optional<Expediente> findByExpedientePK(ExpedientePK PK);

    public boolean existsByExpedientePK(ExpedientePK EPK);

    public void deleteByExpedientePK(ExpedientePK PK);

}
