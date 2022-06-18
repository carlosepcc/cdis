package com.pid.proyecto.controller;

import com.pid.proyecto.Json.Borrar.JsonBorrarResolucion;
import com.pid.proyecto.Json.Crear.JsonCrearResolucion;
import com.pid.proyecto.Json.Modificar.JsonModificarResolucion;
import com.pid.proyecto.Validator.ValidatorResolucion;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resolucion")
@CrossOrigin("*")
public class ResolucionController {

    @Autowired
    ResolucionService resolucionService;

    @Autowired
    ValidatorResolucion validator;

    @PutMapping
    @PreAuthorize("hasRole('ROLE_C_RESOLUCION')")
    public ResponseEntity crear(
            @Valid @RequestBody JsonCrearResolucion JSONR,
            BindingResult BR
    ) {
        // VALIDAR ERRORES
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

        ResponseEntity respuesta = validator.ValidarJsonCrearResolucion(JSONR);
        if (respuesta != null) {
            return respuesta;
        }

        Resolucion resolucion;
        resolucion = new Resolucion();
        resolucion.setDescripcion(JSONR.getDescripcion());
        // GUARDAMOS
        resolucionService.save(resolucion);

        return new ResponseEntity<>(
                new Mensaje("RESOLUCION CREADA"),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_RESOLUCION')")
    public ResponseEntity<List<Resolucion>> listar() {
        List<Resolucion> list = resolucionService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_U_RESOLUCION')")
    public ResponseEntity modificar(
            @RequestBody JsonModificarResolucion JSONR
    ) {
        ResponseEntity respuesta = validator.ValidarJsonModificarResolucion(JSONR);
        if (respuesta != null) {
            return respuesta;
        }
        // BUSCAMOS LA RESOLUCION POR EL ID
        Resolucion resolucion = resolucionService.findById(JSONR.getID());

        if (!JSONR.getDescripcion().isBlank()) {
            resolucion.setDescripcion(JSONR.getDescripcion());
        }
        // GUARDAMOS
        resolucionService.save(resolucion);

        return new ResponseEntity<>(
                new Mensaje("RESOLUCION ACTUALIZADA"),
                HttpStatus.OK
        );
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_D_RESOLUCION')")
    @ResponseBody
    public ResponseEntity borrar(
            @RequestBody JsonBorrarResolucion JSONR) {

        ResponseEntity respuesta = validator.ValidarJsonBorrarResolucion(JSONR);
        if (respuesta != null) {
            return respuesta;
        }

        for (int id : JSONR.getIds()) {
            resolucionService.deleteById(id);
        }
        
        return new ResponseEntity<>(
                new Mensaje(" RESOLUCIONES BORRADAS: [ " + JSONR.getIds().size() + " ]"),
                HttpStatus.OK
        );
    }
}
