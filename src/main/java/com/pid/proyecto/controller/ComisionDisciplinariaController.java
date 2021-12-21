package com.pid.proyecto.controller;

import com.pid.proyecto.entity.ComdiscUsuario;
import com.pid.proyecto.entity.ComdiscUsuarioPK;
import com.pid.proyecto.entity.Comisiondisciplinaria;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.seguridad.auxiliares.ConvertidorToListEntity;
import com.pid.proyecto.seguridad.auxiliares.Filtrador;
import com.pid.proyecto.seguridad.auxiliares.Mensaje;
import com.pid.proyecto.seguridad.dto.NuevaComision;
import com.pid.proyecto.seguridad.enums.RolNombre;
import com.pid.proyecto.service.ComDiscUsuarioService;
import com.pid.proyecto.service.ComisionDisciplinariaService;
import com.pid.proyecto.service.RolSistemaService;
import com.pid.proyecto.service.UsuarioService;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Comision")
@CrossOrigin("*")
public class ComisionDisciplinariaController {

    @Autowired
    RolSistemaService rolSistemaService;

    @Autowired
    ComisionDisciplinariaService ComisionDisciplinariaService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ConvertidorToListEntity convertidorStringToList;

    @Autowired
    Filtrador filtrador;

    @Autowired
    ComDiscUsuarioService comDiscUsuarioService;

    @GetMapping("/listarComision")
@PreAuthorize("hasRole('ROLE_ADMIN') "
            + "or hasRole('ROLE_DECANO')")
    public ResponseEntity<List<Comisiondisciplinaria>> list() {
        List<Comisiondisciplinaria> list = ComisionDisciplinariaService.Listar();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PutMapping("/crearComision")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DECANO')")
    public ResponseEntity<?> crear(@Valid @RequestBody NuevaComision nuevaComision, BindingResult bindingResult) {
        // si tiene errores en el binding result
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Mensaje("CAMPOS MAL PUESTOS"), HttpStatus.BAD_REQUEST);
        }

        int idComision;
        List<Integer> idUsuarios;
        List<Usuario> usuarios;
        List<String> rolComision;
        List<ComdiscUsuario> comdiscUsuarios = new LinkedList<>();
        ComdiscUsuario comDiscUsuario;
        ComdiscUsuarioPK comDiscUsuarioPK;

        // si todo esta bien creamos la comision
        Comisiondisciplinaria comisionDisciplinaria
                = new Comisiondisciplinaria(nuevaComision.getTipoComision(), new Date());

        // si ya existen los usuarios
        if (filtrador.todosExisten(nuevaComision.getUsuarios())) {
            usuarios = convertidorStringToList.convertirListaStringToListaUsuario(nuevaComision.getUsuarios());
        } else {
            return new ResponseEntity<>(new Mensaje("ALGUNO DE ESOS USUARIOS NO EXISTEN, INTÉNTELO DE NUEVO"), HttpStatus.BAD_REQUEST);
        }

        ComisionDisciplinariaService.save(comisionDisciplinaria);

        idComision = comisionDisciplinaria.getIdcomision();

        idUsuarios = convertidorStringToList.convertirListaUsuarioToListaIdUsuarios(usuarios);

        rolComision = nuevaComision.getRolComision();

        if (!(idUsuarios.size() == rolComision.size())) {
            ComisionDisciplinariaService.delete(comisionDisciplinaria.getIdcomision());
            return new ResponseEntity<>(new Mensaje("LA LISTA DE ROLES Y LA DE USUARIOS NO COINCIDEN"), HttpStatus.BAD_REQUEST);
        }

        for (int i = 0; i < idUsuarios.size(); i++) {

            comDiscUsuarioPK = new ComdiscUsuarioPK(idComision, idUsuarios.get(i));

            if (nuevaComision.getRolComision().get(i).contains("jefe")) {
                comDiscUsuario = new ComdiscUsuario(comDiscUsuarioPK, RolNombre.ROLE_JEFE.toString());
            } else if (nuevaComision.getRolComision().get(i).contains("integrante")) {
                comDiscUsuario = new ComdiscUsuario(comDiscUsuarioPK, RolNombre.ROLE_INTEGRANTE.toString());
            } else {
                ComisionDisciplinariaService.delete(comisionDisciplinaria.getIdcomision());
                return new ResponseEntity<>(new Mensaje("ERROR AL DEFINIR LOS ROLES, VUELVA A CREAR LA COMISION"), HttpStatus.BAD_REQUEST);
            }
            comdiscUsuarios.add(comDiscUsuario);
        }

        comDiscUsuarioService.saveAll(comdiscUsuarios);

        return new ResponseEntity(new Mensaje("COMISION CREADA"), HttpStatus.CREATED);
    }
}
