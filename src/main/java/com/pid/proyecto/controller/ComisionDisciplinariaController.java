package com.pid.proyecto.controller;

import com.pid.proyecto.entity.ComDiscUsuario;
import com.pid.proyecto.entity.ComDiscUsuarioPK;
import com.pid.proyecto.entity.ComisionDisciplinaria;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.seguridad.auxiliares.ConvertidorToListEntity;
import com.pid.proyecto.seguridad.auxiliares.Filtrador;
import com.pid.proyecto.seguridad.auxiliares.Mensaje;
import com.pid.proyecto.Json.CrearEntidad.JsonNuevaComision;
import com.pid.proyecto.enums.RolNombre;
import com.pid.proyecto.service.ComDiscUsuarioService;
import com.pid.proyecto.service.ComisionDisciplinariaService;
import com.pid.proyecto.service.RolService;
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
public class comisionDisciplinariaController {

    @Autowired
    RolService rolSistemaService;

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

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN') "
            + "or hasRole('ROLE_DECANO')")
    public ResponseEntity<List<ComisionDisciplinaria>> list() {
        List<ComisionDisciplinaria> list = ComisionDisciplinariaService.Listar();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PutMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_DECANO')")
    public ResponseEntity<?> crear(@Valid @RequestBody JsonNuevaComision nuevaComision, BindingResult bindingResult) {
        // si tiene errores en el binding result
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Mensaje("CAMPOS MAL PUESTOS"), HttpStatus.BAD_REQUEST);
        }

        int idComision;
        List<Integer> idUsuarios;
        List<Usuario> usuarios;
        List<String> rolComision;
        List<ComDiscUsuario> comdiscUsuarios = new LinkedList<>();
        ComDiscUsuario comDiscUsuario = null;
        ComDiscUsuarioPK comDiscUsuarioPK;
        boolean presidente = false;
        boolean secretario = false;

        // si todo esta bien creamos la comision
        ComisionDisciplinaria comisionDisciplinaria = new ComisionDisciplinaria();

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

            comDiscUsuarioPK = new ComDiscUsuarioPK(idComision, idUsuarios.get(i));

            if (!nuevaComision.getRolComision().get(i).contains("presidente") && !nuevaComision.getRolComision().get(i).contains("secretario") && !nuevaComision.getRolComision().get(i).contains("integrante")) {
                ComisionDisciplinariaService.delete(comisionDisciplinaria.getIdcomision());
                return new ResponseEntity<>(new Mensaje("INTRODUJO UN ROL NO VÁLIDO"), HttpStatus.BAD_REQUEST);
            }

            if (nuevaComision.getRolComision().get(i).contains("presidente")) {

                if (presidente == true) {
                    ComisionDisciplinariaService.delete(comisionDisciplinaria.getIdcomision());
                    return new ResponseEntity<>(new Mensaje("SOLO PUEDE HABER UN PRESIDENTE, VUELVA A CREAR LA COMISION"), HttpStatus.BAD_REQUEST);
                }

                if (presidente == false) {
                    comDiscUsuario = new ComDiscUsuario(comDiscUsuarioPK, RolNombre.ROLE_PRESIDENTE_COMISION.toString());
                    presidente = true;
                }
            } else if (nuevaComision.getRolComision().get(i).contains("secretario")) {

                if (secretario == true) {
                    ComisionDisciplinariaService.delete(comisionDisciplinaria.getIdcomision());
                    return new ResponseEntity<>(new Mensaje("SOLO PUEDE HABER UN SECRETARIO, VUELVA A CREAR LA COMISION"), HttpStatus.BAD_REQUEST);
                }

                if (secretario == false) {
                    comDiscUsuario = new ComDiscUsuario(comDiscUsuarioPK, RolNombre.ROLE_SECRETARIO_COMISION.toString());
                    secretario = true;
                }
            } else if (nuevaComision.getRolComision().get(i).contains("integrante")) {
                comDiscUsuario = new ComDiscUsuario(comDiscUsuarioPK, RolNombre.ROLE_DECLARANTE_COMISION.toString());
            } else {
                ComisionDisciplinariaService.delete(comisionDisciplinaria.getIdcomision());
                return new ResponseEntity<>(new Mensaje("ERROR AL DEFINIR LOS ROLES, VUELVA A CREAR LA COMISION"), HttpStatus.BAD_REQUEST);
            }
            comdiscUsuarios.add(comDiscUsuario);
        }

        if (presidente == false || secretario == false) {
            ComisionDisciplinariaService.delete(comisionDisciplinaria.getIdcomision());
            return new ResponseEntity<>(new Mensaje("DEBE EXISTIR 1 PRESIDENTE Y 1 SECRETARIO EN SU COMISIÓN, VUELVA A CREAR LA COMISION"), HttpStatus.BAD_REQUEST);
        }

        comDiscUsuarioService.saveAll(comdiscUsuarios);

        return new ResponseEntity(new Mensaje("COMISION CREADA"), HttpStatus.CREATED);
    }
}
