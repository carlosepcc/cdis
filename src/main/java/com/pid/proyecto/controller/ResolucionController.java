package com.pid.proyecto.controller;

import com.pid.proyecto.Json.Borrar.JsonBorrarResolucion;
import com.pid.proyecto.Json.Crear.JsonCrearResolucion;
import com.pid.proyecto.Json.EntidadesAuxiliares.ComisionReducida;
import com.pid.proyecto.Validator.ValidatorResolucion;
import com.pid.proyecto.auxiliares.FileService;
import com.pid.proyecto.auxiliares.GestionarFicheros;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.entity.Comision;
import com.pid.proyecto.entity.ComisionUsuario;
import com.pid.proyecto.entity.Resolucion;
import com.pid.proyecto.service.ComisionService;
import com.pid.proyecto.service.ComisionUsuarioService;
import com.pid.proyecto.service.ResolucionService;
import com.pid.proyecto.service.RolService;
import com.pid.proyecto.service.UsuarioService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/resolucion")
@CrossOrigin("*")
public class ResolucionController {
    
    @Autowired
    ResolucionService resolucionService;
    
    @Autowired
    ValidatorResolucion validator;
    
    @Autowired
    FileService fileService;
    @Autowired
    GestionarFicheros gestionarFicheros;
    @Autowired
    ComisionUsuarioService comisionUsuarioService;
    @Autowired
    ComisionService comisionService;
    @Autowired
    RolService rolService;
    @Autowired
    UsuarioService usuarioService;
    
    @PutMapping
    @PreAuthorize("hasRole('ROLE_C_RESOLUCION')")
    public ResponseEntity crear(
            @RequestBody JsonCrearResolucion JSONR
    ) {
        ResponseEntity respuesta = validator.ValidarJsonCrearResolucion(JSONR);
        if (respuesta != null) {
            return respuesta;
        }
        
        Resolucion resolucion = new Resolucion();
        resolucion.setUrl("ARCHIVOS/RESOLUCIONES/" + JSONR.getAno());
        resolucion.setComisionList(new LinkedList<Comision>());
        resolucion = resolucionService.save(resolucion);
        List<ComisionUsuario> cuL = new LinkedList<>();
        
        for (ComisionReducida c : JSONR.getComisiones()) {
            
            
            Comision co = new Comision();
            co.setResolucion(resolucion);
            co = comisionService.save(co);
            resolucion.getComisionList().add(co);
            
            ComisionUsuario cuP = new ComisionUsuario(co.getId(), c.getPresidente());
            cuP.setRol(rolService.findById(4));
            cuP.setUsuario(usuarioService.findByUsuario(c.getPresidente()));
            cuP.setComision(co);
            cuL.add(cuP);
            ComisionUsuario cuS = new ComisionUsuario(co.getId(), c.getSecretario());
            cuS.setRol(rolService.findById(5));
            cuS.setUsuario(usuarioService.findByUsuario(c.getSecretario()));
            cuS.setComision(co);
            cuL.add(cuS);
            
            co.setComisionUsuarioList(cuL);
            comisionService.save(co);
            comisionUsuarioService.save(cuP);
            comisionUsuarioService.save(cuS);
            cuL.clear();
        }
        resolucion = resolucionService.save(resolucion);
        
        return new ResponseEntity<>(
                resolucion,
                HttpStatus.CREATED
        );
    }
    
    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_RESOLUCION')")
    public ResponseEntity<List<Resolucion>> listar() {
        List<Resolucion> list = resolucionService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
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
            Path root = Paths.get(resolucionService.findById(id).getUrl());
            fileService.deleteAll(root);
            resolucionService.deleteById(id);
        }
        
        return new ResponseEntity<>(
                new Mensaje(" RESOLUCIONES BORRADAS: [ " + JSONR.getIds().size() + " ]"),
                HttpStatus.OK
        );
    }

    //    @PostMapping
//    @PreAuthorize("hasRole('ROLE_U_RESOLUCION')")
//    public ResponseEntity modificar(
//            @RequestBody JsonModificarResolucion JSONR
//    ) {
//        ResponseEntity respuesta = validator.ValidarJsonModificarResolucion(JSONR);
//        if (respuesta != null) {
//            return respuesta;
//        }
//        // BUSCAMOS LA RESOLUCION POR EL ID
//        Resolucion resolucion = resolucionService.findById(JSONR.getID());
//
//        if (!JSONR.getDescripcion().isBlank()) {
//            resolucion.setUrl(JSONR.getDescripcion());
//        }
//        // GUARDAMOS
//        resolucionService.save(resolucion);
//
//        return new ResponseEntity<>(
//                new Mensaje("RESOLUCION ACTUALIZADA"),
//                HttpStatus.OK
//        );
//    }
}
