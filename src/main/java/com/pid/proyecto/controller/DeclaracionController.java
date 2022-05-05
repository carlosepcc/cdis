package com.pid.proyecto.controller;

import com.pid.proyecto.Json.Borrar.JsonBorrarDeclaraciones;
import com.pid.proyecto.Json.Crear.JsonCrearDeclaracion;
import com.pid.proyecto.Json.Modificar.JsonModificarDeclaracion;
import com.pid.proyecto.Validator.ValidatorDeclaracion;
import com.pid.proyecto.auxiliares.Convertidor;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.auxiliares.SesionDetails;
import com.pid.proyecto.entity.Declaracion;
import com.pid.proyecto.entity.DeclaracionPK;
import com.pid.proyecto.service.CasoService;
import com.pid.proyecto.service.DeclaracionService;
import com.pid.proyecto.service.UsuarioService;
import java.time.LocalDate;
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
@RequestMapping("/Declaracion")
@CrossOrigin("*")
public class DeclaracionController {

    @Autowired
    DeclaracionService declaracionService;

    @Autowired
    CasoService casoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    SesionDetails sesionDetails;

    @Autowired
    Convertidor convertidor;

    @Autowired
    ValidatorDeclaracion validator;

    @PutMapping("/crear")
    @PreAuthorize("hasRole('ROLE_C_DECLARACION')")
    public ResponseEntity<?> crear(
            @Valid @RequestBody JsonCrearDeclaracion JSOND,
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

        ResponseEntity respuesta = validator.ValidarJsonCrearDeclaracion(JSOND);
        if (respuesta != null) {
            return respuesta;
        }

        // DECLARAMOS VARIABLES
        Declaracion declaracion;

        // INICIALIZAMOS VARIABLES
        declaracion = new Declaracion();

        declaracion.setDeclaracionPK(new DeclaracionPK(usuarioService.findByUsuario(sesionDetails.getUsuario()).getId(),
                JSOND.getIdDenuncia(),
                JSOND.getIdComision()));

        declaracion.setAbierta(true);
        declaracion.setFecha(convertidor.LocalDateToSqlDate(LocalDate.now()));
        declaracion.setDescripcion(JSOND.getDescripcion());

        // GUARDAMOS
        declaracionService.save(declaracion);

        return new ResponseEntity<>(
                new Mensaje("DECLARACION CREADA"),
                HttpStatus.CREATED
        );
    }

    // RRR
    // MOSTRAMOS TODOS LOS OBJETOS
    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_DECLARACION')")
    public ResponseEntity<List<Declaracion>> listar() {

        List<Declaracion> list = declaracionService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasRole('ROLE_U_DECLARACION')")
    public ResponseEntity<?> modificar(
            @Valid @RequestBody JsonModificarDeclaracion JSOND
    ) {
        ResponseEntity respuesta = validator.ValidarJsonModificarDeclaracion(JSOND);
        if (respuesta != null) {
            return respuesta;
        }

        // DECLARAMOS VARIABLES
        Declaracion declaracion;

        // INICIALIZAMOS VARIABLES
        declaracion = declaracionService.findByDeclaracionPK(new DeclaracionPK(JSOND.getIdUsuario(), JSOND.getIdDenuncia(), JSOND.getIdComision()));

        if (JSOND.isAbierta()) {
            declaracion.setAbierta(JSOND.isAbierta());
        }

        if (!JSOND.getDescripcion().isBlank()) {
            declaracion.setDescripcion(JSOND.getDescripcion());
        }

        declaracion.setFecha(convertidor.LocalDateToSqlDate(LocalDate.now()));

        // GUARDAMOS
        declaracionService.save(declaracion);

        return new ResponseEntity<>(
                new Mensaje("DECLARACION MODIFICADA"),
                HttpStatus.CREATED
        );
    }

    // DDD
    @DeleteMapping("/borrar")
    @PreAuthorize("hasRole('ROLE_D_DECLARACION')")
    @ResponseBody
    public ResponseEntity<?> borrar(@RequestBody JsonBorrarDeclaraciones JSOND) {
        
        ResponseEntity respuesta = validator.ValidarJsonBorrarDeclaracion(JSOND);
        if (respuesta != null) {
            return respuesta;
        }
        
        List<Declaracion> LD = new LinkedList<>();
        List<DeclaracionPK> LDPK = JSOND.getLDPK();

        for (int i = 0; i < LDPK.size(); i++) {
            LD.add(declaracionService.findByDeclaracionPK(LDPK.get(i)));
        }

        declaracionService.deleteAll(LD);
        // VERIFICAMOS QUE TODOS LOS ID EXISTAN
        // BORRAMOS LOS ID
        return new ResponseEntity<>(
                new Mensaje(" OBJETOS BORRADOS: [ " + LD.size() + " ]"),
                HttpStatus.OK
        );
    }

}
