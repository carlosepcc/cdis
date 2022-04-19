package com.pid.proyecto.controller;

import com.pid.proyecto.entity.Caso;
import com.pid.proyecto.entity.ComisionDisciplinaria;
import com.pid.proyecto.entity.Denuncia;
import com.pid.proyecto.seguridad.auxiliares.Mensaje;
import com.pid.proyecto.Json.CrearEntidad.NuevoCaso;
import com.pid.proyecto.service.CasoService;
import com.pid.proyecto.service.ComisionDisciplinariaService;
import com.pid.proyecto.service.DenunciaService;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Caso")
//podemos acceder desde cualquier url.
@CrossOrigin("*")
public class casoController {

    @Autowired
    CasoService casoService;

    @Autowired
    ComisionDisciplinariaService comisionDisciplinariaService;

    @Autowired
    DenunciaService denunciaService;

    @GetMapping()
    public ResponseEntity<List<Caso>> list() {
        List<Caso> list = casoService.Listar();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PutMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN') "
            + "or hasRole('ROLE_DECANO')")
    public ResponseEntity<?> crear(@Valid @RequestBody NuevoCaso nuevoCaso, BindingResult bindingResult) {
        // si tiene errores en el binding result
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Mensaje("CAMPOS MAL PUESTOS"), HttpStatus.BAD_REQUEST);
        }
        // verificamos que exista la comision que intentamos asignar
        if (!comisionDisciplinariaService.existsById(nuevoCaso.getIdComision())) {
            return new ResponseEntity(new Mensaje("NO EXISTE LA COMISION"), HttpStatus.BAD_REQUEST);
        }
        // verificamos que exista la denuncia que intentamos asignar
        if (!denunciaService.existsById(nuevoCaso.getIdDenuncia())) {
            return new ResponseEntity(new Mensaje("NO EXISTE LA DENUNCIA"), HttpStatus.BAD_REQUEST);
        }
        // verificamos que esta denuncia no este ya siendo atendida 
        if (casoService.existsByDenuncia(denunciaService.getByIddenuncia(nuevoCaso.getIdDenuncia()).get())) {
            return new ResponseEntity(new Mensaje("YA EXISTE UNA COMISION ASIGNADA PARA ESTA DENUNCIA"), HttpStatus.BAD_REQUEST);
        }

// si todo esta bien creamos el caso
        Caso caso = new Caso();
        caso.setAbierto(true);
        caso.setFechaapertura(new Date());

        Denuncia denuncia = denunciaService.getByIddenuncia(nuevoCaso.getIdDenuncia()).get();
        caso.setDenuncia(denuncia);

        ComisionDisciplinaria comision = comisionDisciplinariaService.getByIdcomision(nuevoCaso.getIdComision()).get();
        caso.setComision(comision);

        casoService.save(caso);
        return new ResponseEntity(new Mensaje("CASO GUARDADO"), HttpStatus.CREATED);
    }

    @PutMapping("/actualizarCaso/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> actualizar(@PathVariable("id") int id, @RequestBody NuevoCaso nuevoCaso) {

        // si todo esta bien creamos el usuario
        Caso caso = casoService.getById(id).get();

        caso.setAbierto(nuevoCaso.isAbierto());
        caso.setFechaapertura(new Date());
        caso.setFechaexpiracion(nuevoCaso.getFechaExpiracion());
        casoService.save(caso);
        
        return new ResponseEntity(new Mensaje("CASO ACTUALIZADO"), HttpStatus.CREATED);
    }

}
