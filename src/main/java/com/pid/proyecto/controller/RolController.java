package com.pid.proyecto.controller;

import com.pid.proyecto.CreateRoles;
import com.pid.proyecto.Json.Borrar.JsonBorrarRol;
import com.pid.proyecto.Json.Crear.JsonCrearRol;
import com.pid.proyecto.Json.Modificar.JsonModificarRol;
import com.pid.proyecto.Validator.ValidatorRol;
import com.pid.proyecto.entity.Permiso;
import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.service.PermisoService;
import com.pid.proyecto.service.RolService;
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
@RequestMapping("/rol")
@CrossOrigin("*")
public class RolController {

    @Autowired
    CreateRoles createRoles;

    @Autowired
    PermisoService permisoService;

    @Autowired
    RolService rolService;

    @Autowired
    ValidatorRol validator;

    @PutMapping
    @PreAuthorize("hasRole('ROLE_C_ROL')")
    public ResponseEntity<?> crearRol(
            @Valid @RequestBody JsonCrearRol JSONR,
            BindingResult BR
    ) {
        // VALIDAR ERRORES DENTRO DEL JSON
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
        // VALIDAR JSON ANTES DE USARSE
        ResponseEntity respuesta = validator.ValidarJsonCrearRol(JSONR);
        if (respuesta != null) {
            return respuesta;
        }

        Rol rol = new Rol();
        List<Permiso> LP = new LinkedList<>();

        // ASIGNAR NOMBRE AL ROL
        rol.setRol(JSONR.getRol());
        // GUARDAMOS EL ROL 
        rol = rolService.save(rol);
        // CREAMOS UNA LISTA DE PERMISOS Y LA LLENAMOS
        for (int id : JSONR.getPermisos()) {
            LP.add(permisoService.findById(id));
        }
        rol.setPermisoList(LP);
        return new ResponseEntity<>(
                new Mensaje("ROL CREADO CON: " + LP.size() + " PERMISOS"),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_ROL')")
    public ResponseEntity<List<Rol>> listar() {
        // DEVOLVEMOS TODOS LOS ROLES
        List<Rol> list = rolService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @GetMapping("/mostrarPermisos")
    @PreAuthorize("hasRole('ROLE_R_PERMISO')")
    public ResponseEntity<List<Permiso>> listarPermisos() {
        // DEVOLVEMOS TODOS LOS PERMISOS
        List<Permiso> list = permisoService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_U_ROL')")
    public ResponseEntity<?> actualizar(
            @RequestBody JsonModificarRol JSONR
    ) {
        ResponseEntity respuesta = validator.ValidarJsonModificarRol(JSONR);
        if (respuesta != null) {
            return respuesta;
        }

        Rol rol = rolService.findById(JSONR.getId());

        List<Permiso> permisosM = new LinkedList<>();

        // CAMBIAR NOMBRE
        if (!JSONR.getRol().isBlank()) {
            rol.setRol(JSONR.getRol());
        }

        // CAMBIAR PERMISOS
        if (!JSONR.getPermisos().isEmpty()) {
            rol.setPermisoList(permisosM);
        }

        // SALVAR ROL
        rolService.save(rol);

        return new ResponseEntity<>(
                new Mensaje("ROL ACTUALIZADO"),
                HttpStatus.OK
        );
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_D_ROL')")
    @ResponseBody
    public ResponseEntity<?> borrar(@RequestBody JsonBorrarRol JSONR) {

        ResponseEntity<?> respuesta = validator.ValidarJsonBorrarRol(JSONR);
        if (respuesta != null) {
            return respuesta;
        }

        for (int id : JSONR.getIds()) {
            rolService.deleteById(id);
        }

        return new ResponseEntity<>(
                new Mensaje(" ROLES BORRADOS: [ " + JSONR.getIds() + " ]"),
                HttpStatus.OK
        );
    }
}
