package com.pid.proyecto.controller;

import com.pid.proyecto.entity.ComDiscUsuario;
import com.pid.proyecto.entity.ComDiscUsuarioPK;
import com.pid.proyecto.seguridad.auxiliares.Mensaje;
import com.pid.proyecto.Json.NuevoComDiscUsuario;
import com.pid.proyecto.seguridad.enums.RolNombre;
import com.pid.proyecto.service.ComDiscUsuarioService;
import com.pid.proyecto.service.RolService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ComDiscUsuario")
//podemos acceder desde cualquier url
@CrossOrigin("*")
public class comDiscUsuarioController {

    @Autowired
    RolService rolSistemaService;

    @Autowired
    ComDiscUsuarioService comDiscUsuarioService;

    @GetMapping()
    public ResponseEntity<List<ComDiscUsuario>> list() {
        List<ComDiscUsuario> list = comDiscUsuarioService.Listar();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PutMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN') "
            + "or hasRole('ROLE_DECANO')")
    public ResponseEntity<?> crear(@Valid @RequestBody NuevoComDiscUsuario nuevoComDiscUsuario, BindingResult bindingResult) {

        // si tiene errores en el binding result
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Mensaje("CAMPOS MAL PUESTOS"), HttpStatus.BAD_REQUEST);
        }

// si todo esta bien creamos la comision
        ComDiscUsuario comDiscUsuario = null;

        ComDiscUsuarioPK comDiscUsuarioPK = new ComDiscUsuarioPK(nuevoComDiscUsuario.getIdComision(), nuevoComDiscUsuario.getIdComision());

        if (nuevoComDiscUsuario.getRol().contains("presidente")) {
            comDiscUsuario = new ComDiscUsuario(comDiscUsuarioPK, RolNombre.ROLE_PRESIDENTE_COMISION.toString());
        } else if (nuevoComDiscUsuario.getRol().contains("integrante")) {
            comDiscUsuario = new ComDiscUsuario(comDiscUsuarioPK, RolNombre.ROLE_DECLARANTE_COMISION.toString());
        } else if (nuevoComDiscUsuario.getRol().contains("secretario")) {
            comDiscUsuario = new ComDiscUsuario(comDiscUsuarioPK, RolNombre.ROLE_SECRETARIO_COMISION.toString());
        }
        comDiscUsuarioService.save(comDiscUsuario);

        return new ResponseEntity(new Mensaje("INSTANCIA CREADA"), HttpStatus.CREATED);
    }

}
