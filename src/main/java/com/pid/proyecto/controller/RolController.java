package com.pid.proyecto.controller;

import com.pid.proyecto.CreateRoles;
import com.pid.proyecto.Json.Borrar.JsonBorrarRol;
import com.pid.proyecto.Json.Crear.JsonCrearRol;
import com.pid.proyecto.Json.Modificar.JsonModificarRol;
import com.pid.proyecto.entity.Permiso;
import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.entity.RolPermiso;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.service.PermisoService;
import com.pid.proyecto.service.RolPermisoService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Rol")
@CrossOrigin("*")
public class RolController {

    @Autowired
    CreateRoles createRoles;

    @Autowired
    PermisoService permisoService;

    @Autowired
    RolService rolService;

    @Autowired
    RolPermisoService rolPermisoService;

    // CCC
    // CREAMOS UN NUEVO ROL
    @PutMapping("/crear")
    @PreAuthorize("hasRole('ROLE_C_ROL')")
    public ResponseEntity<?> crearRol(
            @Valid @RequestBody JsonCrearRol JSONR,
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

        // DECLARAMOS 
        Rol rol;
        List<Permiso> LP;
        List<Integer> LIP;

        // INICIALIZAMOS 
        rol = new Rol();
        LP = new LinkedList<>();
        LIP = JSONR.getPermisos();

        for (int id : LIP) {
            if (permisoService.existsById(id)) {
                LP.add(permisoService.findById(id));
            } else {
                return new ResponseEntity<>(
                        new Mensaje("EL PERMISO CON ID " + id + " NO EXISTE"),
                        HttpStatus.NOT_FOUND
                );
            }
        }
        if (!rolService.existsByRol(JSONR.getRol())) {
            rol.setRol(JSONR.getRol());
        } else {
            return new ResponseEntity<>(
                    new Mensaje("YA EXISTE UN ROL CON EL NOMBRE " + JSONR.getRol()),
                    HttpStatus.CONFLICT
            );
        }

        // GUARDAMOS
        rol = rolService.save(rol);

        // AGREGAMOS LOS PERMISOS A NUESTRO ROL EN EL CASO DE ESTARLO CREANDO JUNTO A ELLOS
        if (!LP.isEmpty()) {
            // GUARDAMOS LA RELACION ENTRE EL ROL Y SUS PERMISOS
            createRoles.GuardarRelaciones(rol, LP);
            return new ResponseEntity<>(
                    new Mensaje("ROL CREADO CON: " + LP.size() + " PERMISOS"),
                    HttpStatus.CREATED
            );
        } else // RETORNAMOS CONFIRMACION
        {
            return new ResponseEntity<>(
                    new Mensaje("ROL CREADO SIN PERMISOS"),
                    HttpStatus.CREATED
            );
        }
    }

    // RRR
    // MOSTRAMOS TODOS LOS ROLES
    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_ROL')")
    public ResponseEntity<List<Rol>> listar() {
        List<Rol> list = rolService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // UUU
    @PostMapping("/modificar")
    @PreAuthorize("hasRole('ROLE_U_ROL')")
    public ResponseEntity<?> actualizar(
            @RequestBody JsonModificarRol JSONR
    ) {
        // DECLARAMOS VARIABLES
        Rol rol;
        List<RolPermiso> LRP;
        String NombreRol;
        List<Integer> agregarPermisos;
        List<Integer> eliminarPermisos;
        List<Permiso> permisos = new LinkedList<>(); // ESTA VARIABLE NO SE LLENA AHORA MISMO
        List<Integer> PermisosExistentes;

        // LLENAMOS LAS VARIABLES
        PermisosExistentes = new LinkedList<>();
        if (rolService.existsById(JSONR.getId())) {
            rol = rolService.findById(JSONR.getId());
        } else {
            return new ResponseEntity<>(
                    new Mensaje("EL ROL CON ID: " + JSONR.getId() + " NO EXISTE"),
                    HttpStatus.NOT_FOUND
            );
        }

        LRP = rolPermisoService.findAllByRol(rol.getRol());

        if (!JSONR.getRol().isBlank()) {
            if (JSONR.getRol().matches("ROLE_[A-Z][A-Z|_]+")) {
                NombreRol = JSONR.getRol();
            } else {
                return new ResponseEntity<>(
                        new Mensaje("FORMATO DE ROL INCORRECTO, EL ROL DEBE COMENZAR CON [ROL_] Y SOLO ADMITE [_] Y [A-Z]"),
                        HttpStatus.PRECONDITION_FAILED
                );
            }
        } else {
            NombreRol = rol.getRol();
        }

        agregarPermisos = JSONR.getAgregarPermisos();
        eliminarPermisos = JSONR.getEliminarPermisos();

        // ACTUALIZAMOS CON LAS VARIABLES LLENAS
        rol.setRol(NombreRol);
        // SI SE ESTA MODIFICANDO EL NOMBRE DEL ROL
        // CAMBIAMOS LOS NOMBRES DEL ROL TAMBIEN EN LA TABLA Q RELACIONA ROLES CON PERMISOS
        for (RolPermiso RP : LRP) {
            RP.setRol(NombreRol);
        }
        rolPermisoService.saveAll(LRP);

        // SI SE ESTAN ELIMINANDO PERMISOS DE ESTE ROL
        if (!eliminarPermisos.isEmpty()) {
            int respuesta;
            for (int idp : eliminarPermisos) {
                if (permisoService.existsById(idp)) {
                    permisos.add(permisoService.findById(idp));
                } else {
                    return new ResponseEntity<>(
                            new Mensaje("NO EXISTE EL PERMISO CON ID: " + idp),
                            HttpStatus.NOT_FOUND
                    );
                }

            }

            respuesta = createRoles.EliminarRelaciones(rol, permisos);
            if (respuesta != 0) {
                return new ResponseEntity<>(
                        new Mensaje("NO EXISTE RELACION ENTRE EL ROL CON ID: "
                                + rol.getId() + ", Y EL PERMISO CON ID: " + respuesta),
                        HttpStatus.NOT_FOUND
                );
            }
        }

        permisos.clear();

        // SI SE ESTAN AGREGANDO MAS PERMISOS AL ROL
        if (!agregarPermisos.isEmpty()) {

            for (int idp : agregarPermisos) {
                if (permisoService.existsById(idp)) {
                    permisos.add(permisoService.findById(idp));
                } else {
                    return new ResponseEntity<>(
                            new Mensaje("NO EXISTE EL PERMISO CON ID: " + idp),
                            HttpStatus.NOT_FOUND
                    );
                }
            }
            PermisosExistentes = createRoles.GuardarRelaciones(rol, permisos);
        }

        rolService.save(rol);

         if (PermisosExistentes.isEmpty()) {
            return new ResponseEntity<>(
                    new Mensaje("ROL ACTUALIZADO"),
                    HttpStatus.OK
            );
        } else {
            return new ResponseEntity<>(
                    new Mensaje("SE ACTUALIZÓ EL ROL, LOS PERMISOS: " + PermisosExistentes.toString() + " YA SE ENCONTRABAN Y FUERON SOBREESCRITOS"),
                    HttpStatus.OK
            );
        }

    }

    // DDD
    @DeleteMapping("/borrar")
    @PreAuthorize("hasRole('ROLE_D_ROL')")
    @ResponseBody
    public ResponseEntity<?> borrar(@RequestBody JsonBorrarRol JSONBR) {
        List<Rol> LR = new LinkedList<>();

        // VERIFICAMOS QUE TODOS LOS ID EXISTAN
        for (int id : JSONBR.getIDS()) {
            if (!rolService.existsById(id)) {
                return new ResponseEntity<>(
                        new Mensaje("NO EXISTE ALGUNO DE LOS ID ESPECIFICADOS"),
                        HttpStatus.NOT_FOUND
                );
            }
            LR.add(rolService.findById(id));
        }

        rolService.deleteAll(LR);
        return new ResponseEntity<>(
                new Mensaje(" ROLES BORRADOS: [ " + JSONBR.getIDS() + " ]"),
                HttpStatus.OK
        );
    }
}
