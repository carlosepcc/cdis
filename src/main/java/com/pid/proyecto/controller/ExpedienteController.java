package com.pid.proyecto.controller;

import com.pid.proyecto.Json.JsonExpediente;
import com.pid.proyecto.Json.JsonObjeto;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.entity.Comision;
import com.pid.proyecto.entity.Denuncia;
import com.pid.proyecto.entity.Expediente;
import com.pid.proyecto.entity.ExpedientePK;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.service.ComisionService;
import com.pid.proyecto.service.DenunciaService;
import com.pid.proyecto.service.ExpedienteService;
import com.pid.proyecto.service.UsuarioService;
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
@RequestMapping("/Entidad")
@CrossOrigin("*")
public class ExpedienteController {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    DenunciaService denunciaService;
    @Autowired
    ComisionService comisionService;
    @Autowired
    ExpedienteService expedienteService;

    @PutMapping("/crear")
    @PreAuthorize("hasRole('ROLE_C_EXPEDIENTE')")
    public ResponseEntity<?> crear(
            @Valid @RequestBody JsonExpediente JSONE,
            BindingResult BR
    ) {

        // DECLARAMOS VARIABLES
        Expediente expediente;
        ExpedientePK PK;
        Usuario usuario;
        Denuncia denuncia;
        Comision comision;
        String descripcion;

        // INICIALIZAMOS VARIABLES
        expediente = new Expediente();
        usuario = usuarioService.findById(JSONE.getIdUsuario());
        denuncia = denunciaService.findById(JSONE.getIdDenuncia());
        comision = comisionService.findById(JSONE.getIdComision());
        descripcion = JSONE.getDescripcion();
        PK = new ExpedientePK(usuario.getId(), denuncia.getId(), comision.getId());

        expediente.setExpedientePK(PK);
        expediente.setDescripcion(descripcion);

        // GUARDAMOS
        expedienteService.save(expediente);

        return new ResponseEntity<>(
                new Mensaje(" EXPEDIENTE CREADO"),
                HttpStatus.CREATED
        );
    }

    // RRR
    // MOSTRAMOS TODOS LOS OBJETOS
    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_EXPEDIENTE')")
    public ResponseEntity<List<Expediente>> listar() {

        List<Expediente> list = expedienteService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/modificar/{idUsuarioDenunciaComision}")
    @PreAuthorize("hasRole('ROLE_U_ENTIDAD')")
    public ResponseEntity<?> modificar(
            @PathVariable("idUsuarioDenunciaComision") List<Integer> id,
            @Valid @RequestBody JsonExpediente JSONE,
            BindingResult BR
    ) {
        // DECLARAMOS VARIABLES
        Expediente expediente;
        ExpedientePK PK;
        Usuario usuario;
        Denuncia denuncia;
        Comision comision;
        String descripcion;

        // INICIALIZAMOS VARIABLES
        expediente = new Expediente();
        usuario = usuarioService.findById(id.get(0));
        denuncia = denunciaService.findById(id.get(1));
        comision = comisionService.findById(id.get(2));
        descripcion = JSONE.getDescripcion();
        PK = new ExpedientePK(usuario.getId(), denuncia.getId(), comision.getId());

        expediente.setExpedientePK(PK);
        expediente.setDescripcion(descripcion);

        // GUARDAMOS
        expedienteService.save(expediente);

        return new ResponseEntity<>(
                new Mensaje(" EXPEDIENTE CREADO"),
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
