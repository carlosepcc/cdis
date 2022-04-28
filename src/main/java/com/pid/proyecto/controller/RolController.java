package com.pid.proyecto.controller;

import com.pid.proyecto.CreateRoles;
import com.pid.proyecto.Json.JsonRol;
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
            @Valid @RequestBody JsonRol JSONR,
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

        // DECLARAMOS VARIABLES E INICIALIZAMOS DE SER NECESARIO
        Rol rol = new Rol();
        List<Permiso> LP = new LinkedList<>();
        List<Integer> LIP;
        String NombreRol;

        //LLENAMOS LAS VARIABLES A USAR
        LIP = JSONR.getPermisos();
        NombreRol = JSONR.getRol();

        // LLENAMOS LA ENTIDAD UNA VEZ QUE YA TENEMOS TODAS LAS VARIABLES LISTAS Y VERIFICAMOS QUE LO QUE ESTAMOS CREANDO NO EXISTA YA
        for (int id : LIP) {
            LP.add(permisoService.findById(id));
        }
        rol.setRol(NombreRol);

        // SALVAMOS ESA ENTIDAD
        rolService.save(rol);

        // AGREGAMOS LOS PERMISOS A NUESTRO ROL EN EL CASO DE HABERLO CREADO JUNTO A ELLOS
        if (!LP.isEmpty()) {
            // PRIMERO RECUPERAMOS NUESTRO ROL YA GUARDADO
            rol = rolService.findByRol(rol.getRol());
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
    @PostMapping("/actualizar/{id}")
    @PreAuthorize("hasRole('ROLE_U_ROL')")
    public ResponseEntity<?> actualizar(
            @PathVariable("id") int id,
            @RequestBody JsonRol JSONR,
            BindingResult BR
    ) {
        // DECLARAMOS VARIABLES
        Rol rol;
        List<RolPermiso> LRP;
        String NombreRol;
        List<Integer> agregarPermisos;
        List<Integer> eliminarPermisos;
        List<Permiso> permisos = new LinkedList<>(); // ESTA VARIABLE NO SE LLENA AHORA MISMO

        // LLENAMOS LAS VARIABLES
        rol = rolService.findById(id);
        LRP = rolPermisoService.findAll();

        if (!JSONR.getRol().isBlank()) {
            NombreRol = JSONR.getRol();
        } else {
            NombreRol = rol.getRol();
        }
        agregarPermisos = JSONR.getAgregarPermisos();
        eliminarPermisos = JSONR.getEliminarPermisos();

        // ACTUALIZAMOS CON LAS VARIABLES LLENAS
        rol.setRol(NombreRol);

        // SI SE ESTAN AGREGANDO MAS PERMISOS AL ROL
        if (!agregarPermisos.isEmpty()) {
            for (int idp : agregarPermisos) {
                permisos.add(permisoService.findById(idp));
            }
            createRoles.GuardarRelaciones(rol, permisos);
        }

        permisos.clear();
        // SI SE ESTAN ELIMINANDO PERMISOS DE ESTE ROL
        if (!eliminarPermisos.isEmpty()) {
            for (int idp : eliminarPermisos) {
                permisos.add(permisoService.findById(idp));
            }
            createRoles.EliminarRelaciones(rol, permisos);
        }

        // SI SE ESTA MODIFICANDO EL NOMBRE DEL ROL
        // CAMBIAMOS LOS NOMBRES DEL ROL TAMBIEN EN LA TABLA Q RELACIONA ROLES CON PERMISOS
        for (RolPermiso RP : LRP) {
            if (RP.getRolPermisoPK().getIdr() == rol.getId()) {
                RP.setRol(NombreRol);
            }
        }
        rolPermisoService.saveAll(LRP);
        rolService.save(rol);
        return new ResponseEntity<>(
                new Mensaje("ROL ACTUALIZADO"),
                HttpStatus.OK
        );

    }

    // DDD
    @DeleteMapping("/borrar/{ids}")
    @PreAuthorize("hasRole('ROLE_D_ROL')")
    @ResponseBody
    public ResponseEntity<?> borrar(@PathVariable("ids") List<Integer> ids) {
        List<Rol> LR = new LinkedList<>();

        // VERIFICAMOS QUE TODOS LOS ID EXISTAN
        for (int id : ids) {
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
                new Mensaje(" ROLES BORRADOS: [ " + ids.size() + " ]"),
                HttpStatus.OK
        );
    }
}
