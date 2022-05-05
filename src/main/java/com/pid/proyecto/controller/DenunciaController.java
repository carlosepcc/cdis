package com.pid.proyecto.controller;

import com.pid.proyecto.Json.Borrar.JsonBorrarDenuncia;
import com.pid.proyecto.Json.Crear.JsonCrearDenuncia;
import com.pid.proyecto.Json.Modificar.JsonModificarDenuncia;
import com.pid.proyecto.Validator.ValidatorDenuncia;
import com.pid.proyecto.auxiliares.Convertidor;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.auxiliares.SesionDetails;
import com.pid.proyecto.entity.Denuncia;
import com.pid.proyecto.entity.DenunciaUsuario;
import com.pid.proyecto.entity.DenunciaUsuarioPK;
import com.pid.proyecto.service.DenunciaService;
import com.pid.proyecto.service.DenunciaUsuarioService;
import com.pid.proyecto.service.UsuarioService;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/Denuncia")
@CrossOrigin("*")
public class DenunciaController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    DenunciaService denunciaService;

    @Autowired
    DenunciaUsuarioService denunciaUsuarioService;

    @Autowired
    Convertidor convertidor;

    @Autowired
    SesionDetails SesionDetails;

    @Autowired
    ValidatorDenuncia validator;

    @PutMapping("/crear")
    @PreAuthorize("hasRole('ROLE_C_DENUNCIA')")
    public ResponseEntity<?> crear(
            @RequestBody JsonCrearDenuncia JSOND
    ) {
        ResponseEntity respuesta = validator.ValidarJsonCrearDenuncia(JSOND);
        if (respuesta != null) {
            return respuesta;
        }

        // DECLARAMOS VARIABLES
        Denuncia denuncia;

        DenunciaUsuario DU;

        // INICIALIZAMOS VARIABLES
        denuncia = new Denuncia();

        denuncia.setAcusado(JSOND.getAcusado());
        denuncia.setDescripcion(JSOND.getDescripcion());
        denuncia.setFecha(convertidor.LocalDateToSqlDate(LocalDate.now()));
        denuncia.setProcesada(false);

        // GUARDAMOS
        denuncia = denunciaService.save(denuncia);

        // RELACIONAMOS
        DU = new DenunciaUsuario(
                new DenunciaUsuarioPK(denuncia.getId(),
                        usuarioService.findByUsuario(SesionDetails.getUsuario()).getId()),
                SesionDetails.getUsuario()
        );

        denunciaUsuarioService.save(DU);

        return new ResponseEntity<>(
                new Mensaje(" DENUNCIA CREADA"),
                HttpStatus.CREATED
        );
    }

    // RRR
    // MOSTRAMOS TODOS LOS OBJETOS
    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_DENUNCIA')")
    public ResponseEntity<List<Denuncia>> listar() {

        List<Denuncia> list = denunciaService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasRole('ROLE_U_DENUNCIA')")
    public ResponseEntity<?> modificar(
            @RequestBody JsonModificarDenuncia JSOND
    ) {
        ResponseEntity respuesta = validator.ValidarJsonModificarDenuncia(JSOND);
        if (respuesta != null) {
            return respuesta;
        }

        // DECLARAMOS VARIABLES
        Denuncia denuncia;

        // INICIALIZAMOS VARIABLES
        denuncia = denunciaService.findById(JSOND.getIdDenuncia());

        if (!JSOND.getDescripcion().isBlank()) {
            denuncia.setDescripcion(JSOND.getDescripcion());
        }
        if (!JSOND.getAcusado().isBlank()) {
            denuncia.setAcusado(JSOND.getAcusado());
        }
        // ACTUALIZAMOS LA FECHA DE NUESTRA DENUNCIA
        denuncia.setFecha(convertidor.LocalDateToSqlDate(LocalDate.now()));

        // GUARDAMOS
        denunciaService.save(denuncia);

        return new ResponseEntity<>(
                new Mensaje(" DENUNCIA MODIFICADA"),
                HttpStatus.CREATED
        );
    }

    // DDD
    @DeleteMapping("/borrar")
    @PreAuthorize("hasRole('ROLE_D_DENUNCIA')")
    @ResponseBody
    public ResponseEntity<?> borrar(@RequestBody JsonBorrarDenuncia JSOND) {

        ResponseEntity respuesta = validator.ValidarJsonBorrarDenuncia(JSOND);
        if (respuesta != null) {
            return respuesta;
        }

        List<Denuncia> LD = new LinkedList<>();

        for (int id : JSOND.getIdDenuncias()) {
            LD.add(denunciaService.findById(id));
        }

        denunciaService.deleteAll(LD);

        // VERIFICAMOS QUE TODOS LOS ID EXISTAN
        // BORRAMOS LOS ID
        return new ResponseEntity<>(
                new Mensaje(" DENUNCIAS BORRADAS: [ " + LD.size() + " ]"),
                HttpStatus.OK
        );
    }

}
