package com.pid.proyecto.repository;

import com.pid.proyecto.entity.Dictamen;
import com.pid.proyecto.entity.DictamenPK;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DictamenRepo extends JpaRepository<Dictamen, Integer>{

    public Optional<Dictamen> findByDictamenPK(DictamenPK PK);

    public boolean existsByDictamenPK(DictamenPK dictamenPK);

    public void deleteByDictamenPK(DictamenPK PK);
    
    
    
}
