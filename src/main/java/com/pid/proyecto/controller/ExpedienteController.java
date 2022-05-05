package com.pid.proyecto.controller;

import com.pid.proyecto.Json.Borrar.JsonBorrarExpedientes;
import com.pid.proyecto.Json.Crear.JsonCrearExpediente;
import com.pid.proyecto.Json.Modificar.JsonModificarExpediente;
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

    @PutMapping("/crear")
    @PreAuthorize("hasRole('ROLE_C_EXPEDIENTE')")
    public ResponseEntity<?> crear(
            @RequestBody JsonCrearExpediente JSONE
    ) {

        // DECLARAMOS VARIABLES
        int idU;
        int idD;
        int idC;

        DeclaracionPK DPK;
        ExpedientePK EPK;
        Expediente expediente;

        String descripcion;

        // INICIALIZAMOS VARIABLES
        if (usuarioService.existsById(JSONE.getIdUsuario())) {
            idU = JSONE.getIdUsuario();
        } else {
            return new ResponseEntity<>(
                    new Mensaje("INTRODUJO UN ID DE USUARIO INEXISTENTE: " + JSONE.getIdUsuario()),
                    HttpStatus.NOT_FOUND
            );
        }

        if (denunciaService.existsById(JSONE.getIdDenuncia())) {
            idD = JSONE.getIdDenuncia();
        } else {
            return new ResponseEntity<>(
                    new Mensaje("INTRODUJO UN ID DE DENUNCIA INEXISTENTE: " + JSONE.getIdUsuario()),
                    HttpStatus.NOT_FOUND
            );
        }

        if (comisionService.existsById(JSONE.getIdComision())) {
            idC = JSONE.getIdComision();
        } else {
            return new ResponseEntity<>(
                    new Mensaje("INTRODUJO UN ID DE COMISION INEXISTENTE: " + JSONE.getIdUsuario()),
                    HttpStatus.NOT_FOUND
            );
        }

        DPK = new DeclaracionPK(idU, idD, idC);
        if (declaracionService.existsByDeclaracionPK(DPK)) {
            EPK = new ExpedientePK(idU, idD, idC);
        } else {
            return new ResponseEntity<>(
                    new Mensaje("LA DECLARACION A LA CUAL HACE REFERENCIA NO EXISTE: " + DPK.toString()),
                    HttpStatus.NOT_FOUND
            );
        }
        descripcion = JSONE.getDescripcion();
        expediente = new Expediente();
        expediente.setExpedientePK(EPK);
        expediente.setDescripcion(descripcion);

        // GUARDAMOS
        expedienteService.save(expediente);

        return new ResponseEntity<>(
                new Mensaje(" EXPEDIENTE CREADO"),
                HttpStatus.CREATED
        );
    }

    // RRR
    // MOSTRAMOS TODOS LOS OBJETOS
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
        // DECLARAMOS VARIABLES
        int idU;
        int idD;
        int idC;

        ExpedientePK EPK;
        Expediente expediente;

        String descripcion;

        // INICIALIZAMOS VARIABLES
        if (usuarioService.existsById(JSONE.getIdUsuario())) {
            idU = JSONE.getIdUsuario();
        } else {
            return new ResponseEntity<>(
                    new Mensaje("INTRODUJO UN ID DE USUARIO INEXISTENTE: " + JSONE.getIdUsuario()),
                    HttpStatus.NOT_FOUND
            );
        }

        if (denunciaService.existsById(JSONE.getIdDenuncia())) {
            idD = JSONE.getIdDenuncia();
        } else {
            return new ResponseEntity<>(
                    new Mensaje("INTRODUJO UN ID DE DENUNCIA INEXISTENTE: " + JSONE.getIdUsuario()),
                    HttpStatus.NOT_FOUND
            );
        }

        if (comisionService.existsById(JSONE.getIdComision())) {
            idC = JSONE.getIdComision();
        } else {
            return new ResponseEntity<>(
                    new Mensaje("INTRODUJO UN ID DE COMISION INEXISTENTE: " + JSONE.getIdUsuario()),
                    HttpStatus.NOT_FOUND
            );
        }

        EPK = new ExpedientePK(idU, idD, idC);

        if (expedienteService.existsByExpedientePK(EPK)) {
            expediente = expedienteService.findByExpedientePK(EPK);
        } else {
            return new ResponseEntity<>(
                    new Mensaje("EL EXPEDIENTE AL CUAL HACE REFERENCIA NO EXISTE: " + EPK.toString()),
                    HttpStatus.NOT_FOUND
            );
        }
        if (!JSONE.getDescripcion().isBlank()) {
            descripcion = JSONE.getDescripcion();
        } else {
            descripcion = expediente.getDescripcion();
        }

        expediente.setDescripcion(descripcion);

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
    public ResponseEntity<?> borrar(@RequestBody JsonBorrarExpedientes JSONBE) {

        List<Expediente> LE = new LinkedList<>();
        List<ExpedientePK> LPK;

        LPK = JSONBE.getLPK();

        for (int i = 0; i < LPK.size(); i++) {
            LE.add(expedienteService.findByExpedientePK(LPK.get(i)));
        }

        // VERIFICAMOS QUE TODOS LOS ID EXISTAN
        // BORRAMOS LOS ID
        expedienteService.deleteAll(LE);
        return new ResponseEntity<>(
                new Mensaje(" OBJETOS BORRADOS: [ " + LE.size() + " ]"),
                HttpStatus.OK
        );
    }

}
