package com.pid.proyecto.controller;

import com.pid.proyecto.CreateRoles;
import com.pid.proyecto.Json.CrearEntidad.JsonNuevoRol;
import com.pid.proyecto.Json.ModificarEntidad.JsonModificarRol;
import com.pid.proyecto.entity.Permiso;
import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.seguridad.auxiliares.Mensaje;
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
  RolService rolService;

  @Autowired
  PermisoService permisoService;

  @Autowired
  RolPermisoService rolPermisoService;

  @Autowired
  CreateRoles createRoles;

  // CCC
  // CREAMOS UN NUEVO ROL
  @PutMapping("/crearRol")
  @PreAuthorize("hasRole('ROLE_C_ROL')")
  public ResponseEntity<?> crearRol(
    @Valid @RequestBody JsonNuevoRol NR,
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

    // DECLARAMOS VARIABLES
    //LLENAMOS LAS VARIABLES A USAR
    // LLENAMOS LA ENTIDAD UNA VEZ QUE YA TENEMOS TODAS LAS VARIABLES LISTAS
    List<Integer> LIP = NR.getPermisos();
    List<Permiso> LP = new LinkedList<>();
    for (int idp : LIP) {
      if (permisoService.existsByIdpermiso(idp)) {
        LP.add(permisoService.findByIdPermiso(idp).get());
      } else return new ResponseEntity<>(
        new Mensaje("EL PERMISO CON ID " + idp + " NO EXISTE"),
        HttpStatus.NOT_FOUND
      );
    }

    String Srol = NR.getRol();
    Rol rol = new Rol();
    if (!Srol.isBlank()) {
      if (rolService.existsByRol(Srol)) return new ResponseEntity<>(
        new Mensaje("ESE ROL YA EXISTE"),
        HttpStatus.PRECONDITION_FAILED
      );
      rol.setRol(Srol);
    }

    // SALVAMOS ESA ENTIDAD
    rolService.save(rol);

    // AGREGAMOS LOS PERMISOS A NUESTRO ROL EN EL CASO DE HABERLO CREADO JUNTO A ELLOS
    if (!LP.isEmpty()) {
      // PRIMERO RECUPERAMOS NUESTRO ROL YA GUARDADO
      rol = rolService.getByRol(rol.getRol()).get();
      // GUARDAMOS LA RELACION ENTRE EL ROL Y SUS PERMISOS
      createRoles.GuardarRelaciones(
        rolService.getByRol(rol.getRol()).get(),
        LP
      );

      return new ResponseEntity<>(
        new Mensaje("ROL CREADO CON: " + LP.size() + " PERMISOS"),
        HttpStatus.CREATED
      );
    }

    // RETORNAMOS CONFIRMACION
    return new ResponseEntity<>(
      new Mensaje("ROL CREADO SIN PERMISOS"),
      HttpStatus.CREATED
    );
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
  @PostMapping("/actualizarRol/{id}")
  @PreAuthorize("hasRole('ROLE_U_ROL')")
  public ResponseEntity<?> actualizar(
    @PathVariable("id") int id,
    @Valid @RequestBody JsonModificarRol MR,
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
        HttpStatus.BAD_REQUEST
      );
    }
    // DECLARAMOS VARIABLES
    // LLENAMOS LAS VARIABLES
    // ACTUALIZAMOS CON LAS VARIABLES LLENAS
    String Srol = MR.getRol();
    Rol rol = new Rol();
    if (rolService.existsByIdrol(id)) {
      rol = rolService.getByIdRol(id).get();
    } else {
      return new ResponseEntity<>(
        new Mensaje("NO EXISTE EL ROL CON ID: " + id),
        HttpStatus.PRECONDITION_FAILED
      );
    }

    List<Permiso> permisos = new LinkedList<>();
    List<Integer> agregarPermisos = MR.getAgregarPermisos();
    List<Integer> eliminarPermisos = MR.getEliminarPermisos();

    for (int idp : agregarPermisos) {
      if (!permisoService.existsByIdpermiso(idp)) {
        return new ResponseEntity<>(
          new Mensaje("NO EXISTE EL PERMISO CON ID: " + idp),
          HttpStatus.PRECONDITION_FAILED
        );
      }
    }
    for (int idp : eliminarPermisos) {
      if (!permisoService.existsByIdpermiso(idp)) {
        return new ResponseEntity<>(
          new Mensaje("NO EXISTE EL PERMISO CON ID: " + idp),
          HttpStatus.PRECONDITION_FAILED
        );
      }
    }

    // SALVAMOS ESE ROL
    if (
      !Srol.isBlank() ||
      !agregarPermisos.isEmpty() ||
      !eliminarPermisos.isEmpty()
    ) {
      // SI SE ESTA MODIFICANDO EL NOMBRE DEL ROL
      if (!Srol.isBlank()) {
        if (!Srol.matches("ROLE_[A-Z|_]*")) {
          return new ResponseEntity<>(
            new Mensaje(
              "FORMATO DE ROL INCORRECTO, EL ROL DEBE COMENZAR CON [ROL_] Y SOLO ADMITE [_] Y [A-Z]"
            ),
            HttpStatus.PRECONDITION_FAILED
          );
        }
        if (!rolService.existsByRol(Srol)) {
          rol.setRol(Srol);
        } else {
          return new ResponseEntity<>(
            new Mensaje("YA EXISTE ESE ROL, ASIGNE OTRO DISTINTO"),
            HttpStatus.PRECONDITION_FAILED
          );
        }
      }

      // SI SE ESTAN AGREGANDO MAS PERMISOS AL ROL

      if (!agregarPermisos.isEmpty()) {
        for (int idp : agregarPermisos) {
          permisos.add(permisoService.findByIdPermiso(idp).get());
        }

        createRoles.GuardarRelaciones(rol, permisos);
      }
      permisos.clear();
      // SI SE ESTAN ELIMINANDO PERMISOS DE ESTE ROL
      if (!eliminarPermisos.isEmpty()) {
        boolean seElimino = true;
        for (int idp : eliminarPermisos) {
          permisos.add(permisoService.findByIdPermiso(idp).get());
        }
        seElimino = createRoles.EliminarRelaciones(rol, permisos);
        if (seElimino == false) {
          return new ResponseEntity<>(
            new Mensaje(
              "NO SE ELIMINARON LOS PERMISOS PARA ESTE ROL PORQUE NO EXISTIAN RELACIONES"
            ),
            HttpStatus.NOT_MODIFIED
          );
        }
      }

      rolService.save(rol);
      return new ResponseEntity<>(
        new Mensaje("ROL ACTUALIZADO"),
        HttpStatus.OK
      );
    } else return new ResponseEntity<>(
      new Mensaje("NO SE EFECTUARON CAMBIOS EN ESTE ROL"),
      HttpStatus.NOT_MODIFIED
    );
  }

  // DDD
  @DeleteMapping("/borrarRoles/{ids}")
  @PreAuthorize("hasRole('ROLE_D_ROL')")
  @ResponseBody
  public ResponseEntity<?> borrar(@PathVariable("ids") List<Integer> ids) {
    List<Rol> LR = new LinkedList<>();

    // VERIFICAMOS QUE TODOS LOS ID EXISTAN
    for (int id : ids) {
      if (!rolService.existsByIdrol(id)) {
        return new ResponseEntity<>(
          new Mensaje("NO EXISTE ALGUNO DE LOS ID ESPECIFICADOS"),
          HttpStatus.NOT_FOUND
        );
      }
      LR.add(rolService.getByIdRol(id).get());
    }

    rolService.deleteAll(LR);
    return new ResponseEntity<>(
      new Mensaje(" ROLES BORRADOS: [ " + ids.size() + " ]"),
      HttpStatus.OK
    );
  }
}
