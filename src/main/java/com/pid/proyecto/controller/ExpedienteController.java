package com.pid.proyecto.controller;

import com.pid.proyecto.entity.Caso;
import com.pid.proyecto.entity.PdfExpediente;
import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.seguridad.auxiliares.ConvertidorToListEntity;
import com.pid.proyecto.seguridad.auxiliares.Filtrador;
import com.pid.proyecto.seguridad.auxiliares.Mensaje;
import com.pid.proyecto.Json.NuevoExpediente;
import com.pid.proyecto.seguridad.enums.RolNombre;
import com.pid.proyecto.service.CasoService;
import com.pid.proyecto.service.DenunciaService;
import com.pid.proyecto.service.ExpedienteService;
import com.pid.proyecto.service.RolService;
import com.pid.proyecto.service.UsuarioService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Expediente")
@CrossOrigin("*")
public class expedienteController {

    @Autowired
    ExpedienteService expedienteService;

    @Autowired
    CasoService casoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    DenunciaService denunciaService;

    @Autowired
    RolService rolSistemaService;

    @Autowired
    ConvertidorToListEntity convertidorToListEntity;

    @Autowired
    Filtrador filtrador;

    @GetMapping()
    public ResponseEntity<List<PdfExpediente>> list() {
        List<PdfExpediente> list = expedienteService.Listar();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PutMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN') "
            + "or hasRole('ROLE_DECANO')")
    public ResponseEntity<?> crear(@Valid @RequestBody NuevoExpediente nuevoExpediente, BindingResult bindingResult) {

        // si tiene errores en el binding result
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Mensaje("CAMPOS MAL PUESTOS"), HttpStatus.BAD_REQUEST);
        }

        // verificamos que exista el caso
        if (!casoService.existByIdcaso(nuevoExpediente.getIdCaso())) {
            return new ResponseEntity<>(new Mensaje("NO EXISTE EL CASO ENUNCIADO " + nuevoExpediente.getIdCaso()), HttpStatus.BAD_REQUEST);
        }

        // guardamos el caso
        Caso caso = casoService.getById(nuevoExpediente.getIdCaso()).get();

        // verificamos que el usuario pasado en el json exista realmente
        if (!usuarioService.existsByUsuario(nuevoExpediente.getUsuario())) {
            return new ResponseEntity<>(new Mensaje("NO EXISTE EL ESTUDIANTE ESPECIFICADO"), HttpStatus.BAD_REQUEST);
        }


        // comprobamos que todos los usuarios pertenezcan al caso en cuestion

        // si no se encuentra el usuario dentro del caso analizado entonces es un error 

        // añadimos el usuario del que vamos a crear el expediente
        Usuario usuario = usuarioService.getByUsuario(nuevoExpediente.getUsuario()).get();

        // sacamos la anterior lista de expedientes del caso en cuestion
//        List<PdfExpediente> Le = caso.getPdfExpedienteList();

        // si todo esta bien creamos el expediente
//        PdfExpediente expediente = new PdfExpediente();
//        expediente.setUsuario(usuario);
//        expediente.setDescripcione(nuevoExpediente.getDescripcion());

        // guardamos el expediente
//        expedienteService.save(expediente);

        // creamos la lista de expedientes
//        Le.add(expedienteService.getByIdexpediente(expediente.getIdexpediente()).get());
        // agregamos al caso del que hablamos el expediente nuevo
//        caso.setPdfExpediente(Le);

        // actualizamos el caso
        casoService.save(caso);

        return new ResponseEntity(new Mensaje("EXPEDIENTE CREADO"), HttpStatus.CREATED);
    }

}
