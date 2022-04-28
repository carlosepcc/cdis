package com.pid.proyecto.controller;

import com.pid.proyecto.Json.JsonDenuncia;
import com.pid.proyecto.Json.JsonObjeto;
import com.pid.proyecto.auxiliares.Convertidor;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.auxiliares.SesionDetails;
import com.pid.proyecto.entity.Denuncia;
import com.pid.proyecto.entity.DenunciaUsuario;
import com.pid.proyecto.entity.DenunciaUsuarioPK;
import com.pid.proyecto.service.DenunciaService;
import com.pid.proyecto.service.DenunciaUsuarioService;
import com.pid.proyecto.service.UsuarioService;
import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
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

    @PutMapping("/crear")
    @PreAuthorize("hasRole('ROLE_C_DENUNCIA')")
    public ResponseEntity<?> crear(
            @Valid @RequestBody JsonDenuncia JSOND,
            BindingResult BR
    ) {

        // DECLARAMOS VARIABLES
        Denuncia denuncia;
        String descripcion;
        String acusado;
        LocalDate fecha;
        boolean procesada;
        int idU;
        DenunciaUsuario denunciaUsuario;
        DenunciaUsuarioPK PK;
        String usuario;

        // INICIALIZAMOS VARIABLES
        denuncia = new Denuncia();
        descripcion = JSOND.getDescripcion();
        acusado = JSOND.getAcusado();
        fecha = JSOND.getFecha();
        procesada = JSOND.isProcesada();
        idU = usuarioService.findByUsuario(SesionDetails.getUsuario()).getId();
        denunciaUsuario = new DenunciaUsuario();
        usuario = SesionDetails.getUsuario();

        denuncia.setDescripcion(descripcion);
        denuncia.setAcusado(acusado);
        denuncia.setFecha(convertidor.LocalDateToSqlDate(fecha));
        denuncia.setProcesada(procesada);

        // GUARDAMOS
        denuncia = denunciaService.save(denuncia);
        PK = new DenunciaUsuarioPK(denuncia.getId(), idU);

        // RELACIONAMOS
        denunciaUsuario.setDenunciaUsuarioPK(PK);
        denunciaUsuario.setUser(usuario);
        denunciaUsuarioService.save(denunciaUsuario);

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

    @PostMapping("/modificar/{id}")
    @PreAuthorize("hasRole('ROLE_U_DENUNCIA')")
    public ResponseEntity<?> modificar(
            @PathVariable("id") int id,
            @Valid @RequestBody JsonDenuncia JSOND,
            BindingResult BR
    ) {
        // DECLARAMOS VARIABLES
        Denuncia denuncia;
        String descripcion;
        String acusado;
        boolean procesada;

        // INICIALIZAMOS VARIABLES
        denuncia = denunciaService.findById(id);
        if (!JSOND.getDescripcion().isBlank()) {
            descripcion = JSOND.getDescripcion();
        } else {
            descripcion = denuncia.getDescripcion();
        }
        if (!JSOND.getAcusado().isBlank()) {
            acusado = JSOND.getAcusado();
        } else {
            acusado = denuncia.getAcusado();
        }
        if (JSOND.isProcesada()) {
            procesada = JSOND.isProcesada();
        } else {
            procesada = denuncia.getProcesada();
        }

        denuncia.setDescripcion(descripcion);
        denuncia.setProcesada(procesada);
        denuncia.setAcusado(acusado);

        // GUARDAMOS
        denunciaService.save(denuncia);

        return new ResponseEntity<>(
                new Mensaje(" DENUNCIA MODIFICADA"),
                HttpStatus.CREATED
        );
    }

    // DDD
    @DeleteMapping("/borrar/{ids}")
    @PreAuthorize("hasRole('ROLE_D_DENUNCIA')")
    @ResponseBody
    public ResponseEntity<?> borrar(@PathVariable("ids") List<Integer> ids) {

        List<Denuncia> LD = new LinkedList<>();

        for (int id : ids) {
            LD.add(denunciaService.findById(id));
        }

        denunciaService.deleteAll(LD);

        // VERIFICAMOS QUE TODOS LOS ID EXISTAN
        // BORRAMOS LOS ID
        return new ResponseEntity<>(
                new Mensaje(" DENUNCIAS BORRADAS: [ " + ids.size() + " ]"),
                HttpStatus.OK
        );
    }

}
