package com.pid.proyecto.controller;

import com.pid.proyecto.entity.Declaracion;
import com.pid.proyecto.entity.Permiso;
import com.pid.proyecto.seguridad.auxiliares.ConvertidorToListEntity;
import com.pid.proyecto.seguridad.auxiliares.Mensaje;
import com.pid.proyecto.seguridad.auxiliares.SesionDetails;
import com.pid.proyecto.Json.CrearEntidad.NuevaDeclaracion;
import com.pid.proyecto.service.CasoService;
import com.pid.proyecto.service.DeclaracionService;
import com.pid.proyecto.service.UserDetailsServiceImpl;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Declaracion")
//podemos acceder desde cualquier url
@CrossOrigin("*")
public class declaracionController {
    
    @Autowired
    DeclaracionService declaracionService;
    
    @Autowired
    SesionDetails sesionDetails;
    
    @Autowired
    UsuarioService usuarioService;
    
    @Autowired
    ConvertidorToListEntity convertidor;
    
    @Autowired
    CasoService casoService;
    
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
    
    @PutMapping()
    @PreAuthorize("hasRole('ROLE_C_DECLARACION')")
    public ResponseEntity<?> crear(@Valid @RequestBody NuevaDeclaracion ND, BindingResult bindingResult) {
        
        Declaracion declaracion = new Declaracion();
        
        // PODEMOS DEJAR LA DECLARACION ABIERTA O CERRADA AL CREARLA
        if (ND.isAbierta() == true) {
            declaracion.setAbierta(true);
        } else {
            declaracion.setAbierta(false);
        }
        
        if (!ND.getDescripcion().isBlank()) {
            declaracion.setDescripcion(ND.getDescripcion());
        }
        
        if (casoService.existByIdcaso(ND.getIdCaso())) {
            declaracion.setCaso(casoService.getById(ND.getIdCaso()).get());
        } else {
            return new ResponseEntity(new Mensaje("NO EXISTE EL CASO"), HttpStatus.BAD_REQUEST);
        }
        if (usuarioService.existsById(ND.getIdUsuario())) {
            declaracion.setUsuario(usuarioService.getByIdusuario(ND.getIdUsuario()).get());
        } else {
            return new ResponseEntity(new Mensaje("NO EXISTE EL USUARIO"), HttpStatus.BAD_REQUEST);
        }

        // salvamos la declaracion
        declaracionService.save(declaracion);
        return new ResponseEntity(new Mensaje("DECLARACION GUARDADA"), HttpStatus.CREATED);
    }
    
    @GetMapping()
    @PreAuthorize("hasrole('ROLE_R_DECLARACION')")
    public ResponseEntity<List<Declaracion>> list() {
        List<Declaracion> list = declaracionService.Listar();
        return new ResponseEntity(list, HttpStatus.OK);
    }
    
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_U_DECLARACION')")
    public ResponseEntity<?> Modificar(@Valid @RequestBody NuevaDeclaracion ND, BindingResult bindingResult) {
        
        Declaracion declaracion = new Declaracion();
        
        // PODEMOS DEJAR LA DECLARACION ABIERTA O CERRADA AL CREARLA
        if (ND.isAbierta() == true) {
            declaracion.setAbierta(true);
        } else {
            declaracion.setAbierta(false);
        }
        
        if (!ND.getDescripcion().isBlank()) {
            declaracion.setDescripcion(ND.getDescripcion());
        }
        
        if (casoService.existByIdcaso(ND.getIdCaso())) {
            declaracion.setCaso(casoService.getById(ND.getIdCaso()).get());
        } else {
            return new ResponseEntity(new Mensaje("NO EXISTE EL CASO"), HttpStatus.BAD_REQUEST);
        }
        if (usuarioService.existsById(ND.getIdUsuario())) {
            declaracion.setUsuario(usuarioService.getByIdusuario(ND.getIdUsuario()).get());
        } else {
            return new ResponseEntity(new Mensaje("NO EXISTE EL USUARIO"), HttpStatus.BAD_REQUEST);
        }

        // salvamos la declaracion
        declaracionService.save(declaracion);
        return new ResponseEntity(new Mensaje("DECLARACION GUARDADA"), HttpStatus.CREATED);
    }
}
