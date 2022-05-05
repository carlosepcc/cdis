package com.pid.proyecto.controller;

import com.pid.proyecto.Json.Borrar.JsonBorrarExpedientes;
import com.pid.proyecto.Json.Crear.JsonCrearExpediente;
import com.pid.proyecto.Json.Modificar.JsonModificarExpediente;
import com.pid.proyecto.Validator.ValidatorExpediente;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.entity.DeclaracionPK;
import com.pid.proyecto.entity.Expediente;
import com.pid.proyecto.entity.ExpedientePK;
import com.pid.proyecto.service.ComisionService;
import com.pid.proyecto.service.DeclaracionService;
import com.pid.proyecto.service.DenunciaService;
import com.pid.proyecto.service.ExpedienteService;
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
@RequestMapping("/Expediente")
@CrossOrigin("*")
public class ExpedienteController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    DenunciaService denunciaService;
    @Autowired
    ComisionService comisionService;
    @Autowired
    ExpedienteService expedienteService;
    @Autowired
    DeclaracionService declaracionService;
    @Autowired
    ValidatorExpediente validator;

    @PutMapping("/crear")
    @PreAuthorize("hasRole('ROLE_C_EXPEDIENTE')")
    public ResponseEntity<?> crear(
            @RequestBody JsonCrearExpediente JSONE
    ) {

        // VALIDAR JSON ANTES DE USARSE
        ResponseEntity respuesta = validator.ValidarJsonCrearExpediente(JSONE);
        if (respuesta != null) {
            return respuesta;
        }

        Expediente expediente = new Expediente();

        expediente.setExpedientePK(new ExpedientePK(JSONE.getIdUsuario(), JSONE.getIdDenuncia(), JSONE.getIdComision()));
        expediente.setDescripcion(JSONE.getDescripcion());

        // GUARDAMOS
        expedienteService.save(expediente);

        return new ResponseEntity<>(
                new Mensaje(" EXPEDIENTE CREADO"),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_EXPEDIENTE')")
    public ResponseEntity<List<Expediente>> listar() {

        List<Expediente> list = expedienteService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasRole('ROLE_U_EXPEDIENTE')")
    public ResponseEntity<?> modificar(
            @RequestBody JsonModificarExpediente JSONE
    ) {

        // VALIDAR JSON ANTES DE USARSE
        ResponseEntity respuesta = validator.ValidarJsonModificarExpediente(JSONE);
        if (respuesta != null) {
            return respuesta;
        }

        Expediente expediente = expedienteService.findByExpedientePK(
                new ExpedientePK(JSONE.getIdUsuario(),
                        JSONE.getIdDenuncia(),
                        JSONE.getIdComision()));

        if (!JSONE.getDescripcion().isBlank()) {
            expediente.setDescripcion(JSONE.getDescripcion());
        } 

        // GUARDAMOS
        expedienteService.save(expediente);

        return new ResponseEntity<>(
                new Mensaje(" EXPEDIENTE MODIFICADO"),
                HttpStatus.CREATED
        );
    }

    // DDD
    @DeleteMapping("/borrar")
    @PreAuthorize("hasRole('ROLE_D_EXPEDIENTE')")
    @ResponseBody
    public ResponseEntity<?> borrar(@RequestBody JsonBorrarExpedientes JSONE) {
        
        // VALIDAR JSON ANTES DE USARSE
        ResponseEntity respuesta = validator.ValidarJsonBorrarExpediente(JSONE);
        if (respuesta != null) {
            return respuesta;
        }

        for (ExpedientePK PK: JSONE.getLPK()) {
            expedienteService.deleteByExpedientePK(PK);
        }

        return new ResponseEntity<>(
                new Mensaje(" OBJETOS BORRADOS: [ " + JSONE.getLPK().size() + " ]"),
                HttpStatus.OK
        );
    }

}
