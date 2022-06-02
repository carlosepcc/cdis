package com.pid.proyecto.controller;

import com.pid.proyecto.Json.Borrar.JsonBorrarDeclaraciones;
import com.pid.proyecto.Json.Crear.JsonCrearDeclaracion;
import com.pid.proyecto.Json.Crear.JsonCrearExpediente;
import com.pid.proyecto.Json.Modificar.JsonModificarDeclaracion;
import com.pid.proyecto.Validator.ValidatorDeclaracion;
import com.pid.proyecto.auxiliares.Convertidor;
import com.pid.proyecto.auxiliares.FileService;
import com.pid.proyecto.auxiliares.GestionarFicheros;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.auxiliares.SesionDetails;
import com.pid.proyecto.entity.Declaracion;
import com.pid.proyecto.entity.DeclaracionPK;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.service.CasoService;
import com.pid.proyecto.service.DeclaracionService;
import com.pid.proyecto.service.RolService;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/declaracion")
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

    @Autowired
    FileService fileService;

    @Autowired
    GestionarFicheros gestionarFicheros;

    @Autowired
    RolService rolService;

    @PutMapping
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
        Declaracion declaracion = new Declaracion();
        Usuario declarante = usuarioService.findByUsuario(JSOND.getUsuario());

        // INICIALIZAMOS VARIABLES
        declaracion.setDeclaracionPK(new DeclaracionPK(declarante.getUsuario(),
                JSOND.getIdDenuncia(),
                JSOND.getIdComision()));

        declaracion.setAbierta(true);
        declaracion.setFecha(convertidor.LocalDateToSqlDate(LocalDate.now()));

        // GUARDAMOS
        declaracion = declaracionService.save(declaracion);

        // CREAMOS LAS CARPETAS PARA GUARDAR LAS DECLARACIONES Y LOS EXPEDIENTES
        gestionarFicheros.CrearCarpetaDeclaracionesExpedientes(declaracion);

        return new ResponseEntity<>(
                new Mensaje("DECLARACION CREADA"),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/expediente")
    @PreAuthorize("hasRole('ROLE_C_EXPEDIENTE')")
    public ResponseEntity<?> crear(
            @RequestPart JsonCrearExpediente JSONE,
            @RequestPart List<MultipartFile> files
    ) {

        // VALIDAR JSON ANTES DE USARSE
        ResponseEntity respuesta = validator.ValidarJsonCrearExpediente(JSONE, files);
        if (respuesta != null) {
            return respuesta;
        }

        Declaracion declaracion = declaracionService.findByDeclaracionPK(JSONE.getDeclaracionPK());

        // CREAMOS LA CARPETA ASOCIADA A ESTE EXPEDIENTE Y LO GUARDAMOS EN ELLA
        declaracion = gestionarFicheros.GestionarCrearExpediente(declaracion, files);
        declaracion.setAbierta(false);
        declaracionService.save(declaracion);

        // GUARDAMOS
        return new ResponseEntity<>(
                new Mensaje(" EXPEDIENTE CREADO"),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_DECLARACION')")
    public ResponseEntity<List<Declaracion>> listar() {

        List<Declaracion> list = declaracionService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_U_DECLARACION')")
    public ResponseEntity<?> modificar(
            @RequestPart JsonModificarDeclaracion JSOND,
            @RequestPart List<MultipartFile> files
    ) {
        ResponseEntity respuesta = validator.ValidarJsonModificarDeclaracion(JSOND, files);
        if (respuesta != null) {
            return respuesta;
        }

        Usuario usuario = usuarioService.findByUsuario(JSOND.getUsuario());

        // DECLARAMOS VARIABLES
        Declaracion declaracion;

//        Usuario usuario = usuarioService.findByUsuario(JSOND.getUsuario());
        DeclaracionPK DPK = new DeclaracionPK(usuario.getUsuario(), JSOND.getIdDenuncia(), JSOND.getIdComision());

        // INICIALIZAMOS VARIABLES
        declaracion = declaracionService.findByDeclaracionPK(DPK);

        // SI EL USUARIO QUE ESTA EN SESION ES EL MISMO
        // QUE DEBE HACER LA DECLARACION ENTONCES ESTA SE CIERRA EN CUANTO LA TERMINE
        if (sesionDetails.getUsuario().equals(declaracion.getDeclaracionPK().getUsuario())) {
            declaracion.setAbierta(false);
        }

        if (JSOND.isAbierta()) {
            declaracion.setAbierta(true);
        }

        if (sesionDetails.getUsuario().equals(declaracion.getDeclaracionPK().getUsuario())) {
            declaracion.setFecha(convertidor.LocalDateToSqlDate(LocalDate.now()));
        }

        // CREAMOS LA CARPETA DE DECLARACION Y GUARDAMOS LOS FICHEROS DENTRO
        if (!files.isEmpty()) {
            declaracion = gestionarFicheros.GestionarModificarDeclaracion(declaracion, files);
        }

        declaracionService.save(declaracion);

        return new ResponseEntity<>(
                new Mensaje("DECLARACION MODIFICADA"),
                HttpStatus.CREATED
        );
    }

    // DDD
    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_D_DECLARACION')")
    @ResponseBody
    public ResponseEntity<?> borrar(@RequestBody JsonBorrarDeclaraciones JSOND) {

        ResponseEntity respuesta = validator.ValidarJsonBorrarDeclaracion(JSOND);
        if (respuesta != null) {
            return respuesta;
        }

        for (DeclaracionPK PK : JSOND.getLDPK()) {
            declaracionService.deleteByDeclaracionPK(PK);
        }

        return new ResponseEntity<>(
                new Mensaje(" DECLARACIONES BORRADAS: [ " + JSOND.getLDPK().size() + " ]"),
                HttpStatus.OK
        );
    }

}
