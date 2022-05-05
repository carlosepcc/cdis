package com.pid.proyecto.service;

import com.pid.proyecto.entity.Dictamen;
import com.pid.proyecto.entity.DictamenPK;
import com.pid.proyecto.repository.DictamenRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DictamenService {

    @Autowired
    DictamenRepo repo;

    public void save(Dictamen dictamen) {
        repo.save(dictamen);
    }

    public List<Dictamen> findAll() {
        return repo.findAll();
    }

    public Dictamen findByDictamenPK(DictamenPK PK) {
        return repo.findByDictamenPK(PK).get();
    }

    public void deleteAll(List<Dictamen> LD) {
        repo.deleteAll(LD);
    }

    public boolean existsByDictamenPK(DictamenPK dictamenPK) {
        return repo.existsByDictamenPK(dictamenPK);
    }

    public void deleteByDictamenPK(DictamenPK PK) {
        repo.deleteByDictamenPK(PK);
    }

}
