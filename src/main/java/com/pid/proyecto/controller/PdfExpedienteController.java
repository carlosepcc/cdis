package com.pid.proyecto.controller;

import com.pid.proyecto.Json.CrearEntidad.JsonNuevoPdfExpediente;
import com.pid.proyecto.Json.ModificarEntidad.JsonModificarPdfExpediente;
import com.pid.proyecto.entity.Declaracion;
import com.pid.proyecto.entity.PdfExpediente;
import com.pid.proyecto.seguridad.auxiliares.ConvertidorToListEntity;
import com.pid.proyecto.seguridad.auxiliares.Filtrador;
import com.pid.proyecto.seguridad.auxiliares.Mensaje;
import com.pid.proyecto.service.DeclaracionService;
import com.pid.proyecto.service.DenunciaService;
import com.pid.proyecto.service.PdfExpedienteService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Expediente")
@CrossOrigin("*")
public class PdfExpedienteController {

    @Autowired
    PdfExpedienteService pdfExpedienteService;

    @Autowired
    DenunciaService denunciaService;

    @Autowired
    RolService rolSistemaService;

    @Autowired
    ConvertidorToListEntity convertidorToListEntity;

    @Autowired
    Filtrador filtrador;

    @Autowired
    DeclaracionService declaracionService;
    
    // CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC
    @PutMapping
    @PreAuthorize("hasRole('ROLE_C_EXPEDIENTE')")
    public ResponseEntity<?> crear(
            @Valid @RequestBody JsonNuevoPdfExpediente NE,
            BindingResult BR
    ) {
        // si tiene errores en el binding result
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
        String descripcion = NE.getDescripcion();
        Declaracion declaracion = declaracionService
                .findById(NE.getIdDeclaracion())
                .get();
        PdfExpediente expediente = new PdfExpediente();
        expediente.setDeclaracion(declaracion);
        expediente.setDescripcion(descripcion);

        pdfExpedienteService.save(expediente);

        return new ResponseEntity<>(
                new Mensaje("EXPEDIENTE CREADO"),
                HttpStatus.CREATED
        );
    }
    
    // RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_EXPEDIENTE')")
    public ResponseEntity<List<PdfExpediente>> list() {
        List<PdfExpediente> list = pdfExpedienteService.Listar();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU
    @PostMapping
    @PreAuthorize("hasRole('ROLE_U_EXPEDIENTE')")
    public ResponseEntity<?> Modificar(@PathVariable("id") int id,
            @Valid @RequestBody JsonModificarPdfExpediente ME,
            BindingResult BR
    ) {
        // si tiene errores en el binding result
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

        // VERIFICAMOS QUE EXISTA EL EXPEDIENTE
        if (pdfExpedienteService.ExistByIdexpediente(id)) {
            PdfExpediente expediente = pdfExpedienteService.getByIdexpediente(id).get();

            // VERIFICAMOS SI SE MANDO ALGUNA DESCRIPCION
            if (!ME.getDescripcion().isBlank()) {
                expediente.setDescripcion(ME.getDescripcion());
            }

            // VERIFICAMOS SI SE ESTA CORRIGIENDO EL ID DE LA DECLARACION
            if (declaracionService.ExistsByIddeclaracion(ME.getIdDeclaracion())) {
                Declaracion declaracion = declaracionService
                        .findById(ME.getIdDeclaracion())
                        .get();
                expediente.setDeclaracion(declaracion);
            }

            pdfExpedienteService.save(expediente);
        } else {
            return new ResponseEntity<>(
                    new Mensaje("ESE EXPEDIENTE NO EXISTE"),
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                new Mensaje("EXPEDIENTE ACTUALIZADO"),
                HttpStatus.CREATED
        );
    }
    
    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_D_EXPEDIENTE')")
    public ResponseEntity<?> Eliminar(@PathVariable("ids") List<Integer> ids,
            @Valid @RequestBody JsonModificarPdfExpediente ME,
            BindingResult BR
    ) {
        List<Integer> ID = new LinkedList<>();

        // VERIFICAMOS QUE TODOS LOS ID EXISTAN
        for (int id : ids) {
            if (!pdfExpedienteService.ExistByIdexpediente(ids.get(id))) {
                return new ResponseEntity<>(new Mensaje("NO EXISTE ALGUNO DE LOS ID ESPECIFICADOS"), HttpStatus.NOT_FOUND);
            }
            ID.add(id);
        }

        // SI TODOS EXISTEN LOS BORRAMOS DE LA BASE DE DATOS
        for (int id : ID) {
            pdfExpedienteService.DeleteByIdExpediente(id);
        }
        return new ResponseEntity<>(new Mensaje(ids.size() + " USUARIOS BORRADOS"), HttpStatus.OK);
    }
}
