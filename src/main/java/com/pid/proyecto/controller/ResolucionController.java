package com.pid.proyecto.controller;

import com.pid.proyecto.Json.Borrar.JsonBorrarResoluciones;
import com.pid.proyecto.Json.Crear.JsonCrearResolucion;
import com.pid.proyecto.Json.Modificar.JsonModificarResolucion;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.entity.Resolucion;
import com.pid.proyecto.service.ResolucionService;
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
@RequestMapping("/Resolucion")
@CrossOrigin("*")
public class ResolucionController {

    @Autowired
    ResolucionService resolucionService;

    @PutMapping("/crear")
    @PreAuthorize("hasRole('ROLE_C_RESOLUCION')")
    public ResponseEntity<?> crear(
            @RequestBody JsonCrearResolucion JSONR
    ) {
        // DECLARAMOS VARIABLES
        Resolucion resolucion;
        String descripcion;

        // INICIALIZAMOS VARIABLES
        resolucion = new Resolucion();
        descripcion = JSONR.getDescripcion();

        resolucion.setDescripcion(descripcion);

        // GUARDAMOS
        resolucionService.save(resolucion);

        return new ResponseEntity<>(
                new Mensaje("RESOLUCION CREADA"),
                HttpStatus.CREATED
        );
    }

    // RRR
    // MOSTRAMOS TODOS LOS OBJETOS
    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_RESOLUCION')")
    public ResponseEntity<List<Resolucion>> listar() {

        List<Resolucion> list = resolucionService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasRole('ROLE_U_RESOLUCION')")
    public ResponseEntity<?> modificar(
            @RequestBody JsonModificarResolucion JSONR
    ) {
        // DECLARAMOS VARIABLES
        Resolucion resolucion;
        String descripcion;

        // INICIALIZAMOS VARIABLES
        if (resolucionService.existsById(JSONR.getID())) {
            resolucion = resolucionService.findById(JSONR.getID());
        } else {
            return new ResponseEntity<>(
                    new Mensaje("NO EXISTE LA RESOLUCION CON ID: " + JSONR.getID()),
                    HttpStatus.OK
            );
        }

        if (!JSONR.getDescripcion().isBlank()) {
            descripcion = JSONR.getDescripcion();
        } else {
            descripcion = resolucion.getDescripcion();
        }

        resolucion.setDescripcion(descripcion);

        // GUARDAMOS
        resolucionService.save(resolucion);
        return new ResponseEntity<>(
                new Mensaje("RESOLUCION ACTUALIZADA"),
                HttpStatus.OK
        );
    }

    // DDD
    @DeleteMapping("/borrar")
    @PreAuthorize("hasRole('ROLE_D_RESOLUCION')")
    @ResponseBody
    public ResponseEntity<?> borrar(@RequestBody JsonBorrarResoluciones JSONR) {

        List<Integer> LID = new LinkedList<>();

        // VERIFICAMOS QUE TODOS LOS ID EXISTAN
        for (int id : JSONR.getIdR()) {
            if (resolucionService.existsById(id)) {
                LID.add(id);
            } else {
                return new ResponseEntity<>(
                        new Mensaje("NO EXISTE LA RESOLUCION CON ID: " + id),
                        HttpStatus.OK
                );
            }
        }
        // BORRAMOS LOS ID
        if (!LID.isEmpty()) {
            resolucionService.deleteByIdAll(LID);
        } else {
            return new ResponseEntity<>(
                    new Mensaje("NO BORRÓ NINGUNA RESOLUCION "),
                    HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
                new Mensaje(" RESOLUCIONES BORRADAS: [ " + LID.size() + " ]"),
                HttpStatus.OK
        );
    }
}
