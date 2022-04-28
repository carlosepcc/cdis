package com.pid.proyecto.controller;

import com.pid.proyecto.Json.JsonObjeto;
import com.pid.proyecto.auxiliares.Mensaje;
import java.util.LinkedList;
import java.util.List;
import javax.validation.Valid;
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
@RequestMapping("/Entidad")
@CrossOrigin("*")
public class basicController {

    @PutMapping("/crear")
    @PreAuthorize("hasRole('ROLE_C_ENTIDAD')")
    public ResponseEntity<?> crear(
            @Valid @RequestBody JsonObjeto JSONO,
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

        // DECLARAMOS VARIABLES
        Object entidad;
        String propiedad;

        // INICIALIZAMOS VARIABLES
        propiedad = JSONO.getPropiedad();
        entidad = propiedad;

        // GUARDAMOS
        return new ResponseEntity<>(
                new Mensaje(" OBJETO CREADO"),
                HttpStatus.CREATED
        );
    }

    // RRR
    // MOSTRAMOS TODOS LOS OBJETOS
    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_ENTIDAD')")
    public ResponseEntity<List<Object>> listar() {

        List<Object> list = new LinkedList<>();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/modificar/{id}")
    @PreAuthorize("hasRole('ROLE_U_ENTIDAD')")
    public ResponseEntity<?> modificar(
            @PathVariable("id") int id,
            @Valid @RequestBody JsonObjeto JSONO,
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

        // DECLARAMOS VARIABLES
        Object entidad = "objeto llamado de la base de datos mediante el id";
        String propiedad;

        // INICIALIZAMOS VARIABLES
        propiedad = entidad.toString();

        if (!JSONO.getPropiedad().isEmpty()) {
            propiedad = JSONO.getPropiedad();
        } else {
            propiedad = entidad.toString();
        }

        entidad = propiedad;

        // GUARDAMOS
        return new ResponseEntity<>(
                new Mensaje(" OBJETO ACTUALIZADO"),
                HttpStatus.CREATED
        );
    }
    
    // DDD
  @DeleteMapping("/borrar/{ids}")
  @PreAuthorize("hasRole('ROLE_D_ENTIDAD')")
  @ResponseBody
  public ResponseEntity<?> borrar(@PathVariable("ids") List<Integer> ids) {
      
    List<Object> LO = new LinkedList<>();

    // VERIFICAMOS QUE TODOS LOS ID EXISTAN
    
    // BORRAMOS LOS ID

    return new ResponseEntity<>(
      new Mensaje(" OBJETOS BORRADOS: [ " + ids.size() + " ]"),
      HttpStatus.OK
    );
  }

}
