package com.pid.proyecto.controller;

import com.pid.proyecto.Json.Borrar.JsonBorrarComision;
import com.pid.proyecto.Json.Crear.JsonCrearComision;
import com.pid.proyecto.Json.Modificar.JsonModificarComision;
import com.pid.proyecto.Validator.ValidatorComision;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.auxiliares.UsuarioRol;
import com.pid.proyecto.entity.Comision;
import com.pid.proyecto.entity.ComisionUsuario;
import com.pid.proyecto.entity.ComisionUsuarioPK;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.service.ComisionService;
import com.pid.proyecto.service.ComisionUsuarioService;
import com.pid.proyecto.service.ResolucionService;
import com.pid.proyecto.service.RolService;
import com.pid.proyecto.service.UsuarioService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comision")
@CrossOrigin("*")
public class ComisionController {

    @Autowired
    ResolucionService resolucionService;

    @Autowired
    ComisionService comisionService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    ComisionUsuarioService comisionUsuarioService;

    @Autowired
    ValidatorComision validator;

    // RRR
    // MOSTRAMOS TODOS LOS OBJETOS
    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_COMISION')")
    public ResponseEntity<List<Comision>> listar() {

        List<Comision> list = comisionService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @PostMapping
    @PreAuthorize("hasRole('ROLE_U_COMISION')")
    public ResponseEntity<?> modificar(
            @RequestBody JsonModificarComision JSONC
    ) {

        ResponseEntity respuesta = validator.ValidarJsonModificarComision(JSONC);
        if (respuesta != null) {
            return respuesta;
        }

        // DECLARAMOS VARIABLES
        Comision comision;

        // INICIALIZAMOS
        comision = comisionService.findById(JSONC.getIdComision());

        if (!JSONC.getIntegrantes().isEmpty()) {

            // DEBEMOS BORRAR LOS EMPAREJAMIENTOS CON ESTA COMISION ANTES
            List<ComisionUsuario> LCU_actual = comisionUsuarioService.findAllByComision(comision);
            List<ComisionUsuario> LCU_modificada = new LinkedList<>();

            for (UsuarioRol ur : JSONC.getIntegrantes()) {
                ComisionUsuario cu = new ComisionUsuario(new ComisionUsuarioPK(comision.getId(), ur.getUsuario()));
                cu.setRol(rolService.findById(ur.getIdRol()));
                LCU_modificada.add(cu);
            }
            comisionUsuarioService.deleteAll(LCU_actual);
            comisionUsuarioService.saveAll(LCU_modificada);
        }

        return new ResponseEntity<>(
                new Mensaje("COMISION ACTUALIZADA"),
                HttpStatus.CREATED
        );
    }

    // DDD
    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_D_COMISION')")
    @ResponseBody
    public ResponseEntity<?> borrar(@RequestBody JsonBorrarComision JSONC) {

        ResponseEntity respuesta = validator.ValidarJsonBorrarComision(JSONC);
        if (respuesta != null) {
            return respuesta;
        }

        for (int id : JSONC.getIds()) {
            comisionService.deleteById(id);
        }

        return new ResponseEntity<>(
                new Mensaje("COMISIONES BORRADAS: [ " + JSONC.getIds().size() + " ]"),
                HttpStatus.OK
        );
    }

}
