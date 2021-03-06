package com.pid.proyecto.service;

import com.pid.proyecto.entity.Caso;
import com.pid.proyecto.entity.CasoPK;
import com.pid.proyecto.repository.CasoRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CasoService {

    @Autowired
    CasoRepo repo;

    public Caso save(Caso caso) {
       return repo.save(caso);
    }

    public List<Caso> findAll() {
        return repo.findAll();
    }

    public Caso findByCasoPK(CasoPK id) {
        return repo.findByCasoPK(id).get();
    }

    public void deleteAll(List<Caso> LC) {
        repo.deleteAll(LC);
    }

    public boolean existsByCasoPK(CasoPK casoPK) {

        return repo.existsByCasoPK(casoPK);
    }

    public void deleteByCasoPK(CasoPK PK) {
        repo.deleteByCasoPK(PK);
    }

    List<Caso> findAllByAbierto(boolean abierto) {
       return repo.findAllByAbierto(abierto);
    }

}
