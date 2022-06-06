package com.pid.proyecto.controller;

import com.pid.proyecto.Json.Borrar.JsonBorrarDenuncia;
import com.pid.proyecto.Json.Crear.JsonCrearDenuncia;
import com.pid.proyecto.Json.Modificar.JsonModificarDenuncia;
import com.pid.proyecto.Validator.ValidatorDenuncia;
import com.pid.proyecto.auxiliares.Convertidor;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.auxiliares.SesionDetails;
import com.pid.proyecto.entity.Denuncia;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.service.DenunciaService;
import com.pid.proyecto.service.UsuarioService;
import java.time.LocalDate;
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
@RequestMapping("/denuncia")
@CrossOrigin("*")
public class DenunciaController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    DenunciaService denunciaService;

    @Autowired
    Convertidor convertidor;

    @Autowired
    SesionDetails SesionDetails;

    @Autowired
    ValidatorDenuncia validator;

    @PutMapping
    @PreAuthorize("hasRole('ROLE_C_DENUNCIA')")
    public ResponseEntity<?> crear(
            @Valid @RequestBody JsonCrearDenuncia JSOND,
            BindingResult BR
    ) {

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

        ResponseEntity respuesta = validator.ValidarJsonCrearDenuncia(JSOND);
        if (respuesta != null) {
            return respuesta;
        }
        List<Usuario> acusados = new LinkedList<>();
        for (String u : JSOND.getAcusados()) {
            acusados.add(usuarioService.findByUsuario(u));
        }

        Denuncia denuncia = new Denuncia();

        // SE AGREGAN LOS ACUSADOS A ESTA DENUNCIA
        denuncia.setDenunciante(SesionDetails.getUsuario());
        denuncia.setFecha(convertidor.LocalDateToSqlDate(LocalDate.now()));
        denuncia.setProcesada(false);
        denuncia.setDescripcion(JSOND.getDescripcion());
        denuncia.setUsuarioList(acusados);

        // GUARDAMOS
        denunciaService.save(denuncia);

        return new ResponseEntity<>(
                new Mensaje(" DENUNCIA CREADA"),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_DENUNCIA')")
    public ResponseEntity<List<Denuncia>> listar() {

        List<Denuncia> list = denunciaService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_U_DENUNCIA')")
    public ResponseEntity<?> modificar(
            @RequestBody JsonModificarDenuncia JSOND
    ) {
        ResponseEntity respuesta = validator.ValidarJsonModificarDenuncia(JSOND);
        if (respuesta != null) {
            return respuesta;
        }

        Denuncia denuncia = denunciaService.findById(JSOND.getIdDenuncia());

        if (!JSOND.getDescripcion().isBlank()) {
            denuncia.setDescripcion(JSOND.getDescripcion());
        }

        if (!JSOND.getAcusado().isEmpty()) {
            List<Usuario> acusados = new LinkedList<>();
            for (String u : JSOND.getAcusado()) {
                acusados.add(usuarioService.findByUsuario(u));
            }
            denuncia.setUsuarioList(acusados);
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

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_D_DENUNCIA')")
    @ResponseBody
    public ResponseEntity<?> borrar(@RequestBody JsonBorrarDenuncia JSOND) {

        ResponseEntity respuesta = validator.ValidarJsonBorrarDenuncia(JSOND);
        if (respuesta != null) {
            return respuesta;
        }

        for (int id : JSOND.getIdDenuncias()) {
            denunciaService.deleteById(id);
        }

        return new ResponseEntity<>(
                new Mensaje(" DENUNCIAS BORRADAS: [ " + JSOND.getIdDenuncias().size() + " ]"),
                HttpStatus.OK
        );
    }

}
