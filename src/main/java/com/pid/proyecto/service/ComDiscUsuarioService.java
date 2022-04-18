package com.pid.proyecto.service;

import com.pid.proyecto.entity.ComDiscUsuario;
import com.pid.proyecto.repository.ComDiscUsuarioRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional //mejora el rendimiento y la coherencia de las consultas
public class ComDiscUsuarioService {

    @Autowired
    ComDiscUsuarioRepository comDiscUsuarioRepository;

    public List<ComDiscUsuario> Listar() {

        return comDiscUsuarioRepository.findAll();
    }

    public void save(ComDiscUsuario comDiscUsuario) {

        comDiscUsuarioRepository.save(comDiscUsuario);

    }

    public void saveAll(List<ComDiscUsuario> comDiscUsuarios) {

        for (ComDiscUsuario comdiscUsuario : comDiscUsuarios) {
            comDiscUsuarioRepository.save(comdiscUsuario);
        }
    }
}
