package com.pid.proyecto.controller;

import com.pid.proyecto.Json.JsonObjeto;
import com.pid.proyecto.Json.JsonResolucion;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.entity.Resolucion;
import com.pid.proyecto.service.ResolucionService;
import java.util.LinkedList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            @Valid @RequestBody JsonResolucion JSONR,
            BindingResult BR
    ) {
        // VALIDAR ERRORES DE JSON
        if (BR.hasErrors()) {
            List<String> errores = new LinkedList<>();
            for (FieldError FE : BR.getFieldErrors()) {
                errores.add(FE.getDefaultMessage());
            }
            return new ResponseEntity<>(
                    new Mensaje(errores.toString()),
                    HttpStatus.PRECONDITION_FAILED
            );
        }

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

    @PostMapping("/modificar/{id}")
    @PreAuthorize("hasRole('ROLE_U_RESOLUCION')")
    public ResponseEntity<?> modificar(
            @PathVariable("id") int id,
            @Valid @RequestBody JsonResolucion JSONR,
            BindingResult BR
    ) {
        // VALIDAR ERRORES DE JSON
        if (BR.hasErrors()) {
            List<String> errores = new LinkedList<>();
            for (FieldError FE : BR.getFieldErrors()) {
                errores.add(FE.getDefaultMessage());
            }
            return new ResponseEntity<>(
                    new Mensaje(errores.toString()),
                    HttpStatus.PRECONDITION_FAILED
            );
        }

        // DECLARAMOS VARIABLES
        Resolucion resolucion;
        String descripcion;

        // INICIALIZAMOS VARIABLES
        resolucion = resolucionService.findById(id);
        if (!JSONR.getDescripcion().isBlank()) {
            descripcion = JSONR.getDescripcion();
        } else {
            descripcion = resolucion.getDescripcion();
        }

        resolucion.setDescripcion(descripcion);

        // GUARDAMOS
        resolucionService.save(resolucion);
        return new ResponseEntity<>(
                new Mensaje(" OBJETO ACTUALIZADO"),
                HttpStatus.OK
        );
    }

    // DDD
    @DeleteMapping("/borrar/{ids}")
    @PreAuthorize("hasRole('ROLE_D_RESOLUCION')")
    @ResponseBody
    public ResponseEntity<?> borrar(@PathVariable("ids") List<Integer> ids) {

        List<Integer> LID = new LinkedList<>();

        // VERIFICAMOS QUE TODOS LOS ID EXISTAN
        for (int id : ids) {
            LID.add(id);
        }
        // BORRAMOS LOS ID
        if (!LID.isEmpty()) {
            for (int id : ids) {
                resolucionService.deleteByIdAll(LID);
            }
        }
        return new ResponseEntity<>(
                new Mensaje(" RESOLUCIONES BORRADAS: [ " + ids.size() + " ]"),
                HttpStatus.OK
        );
    }

}
