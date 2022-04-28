package com.pid.proyecto.controller;

import com.pid.proyecto.Json.Borrar.JsonBorrarDeclaraciones;
import com.pid.proyecto.Json.Login.JsonDeclaracion;
import com.pid.proyecto.auxiliares.Convertidor;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.auxiliares.SesionDetails;
import com.pid.proyecto.entity.Caso;
import com.pid.proyecto.entity.CasoPK;
import com.pid.proyecto.entity.Comision;
import com.pid.proyecto.entity.Declaracion;
import com.pid.proyecto.entity.DeclaracionPK;
import com.pid.proyecto.entity.Denuncia;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.service.CasoService;
import com.pid.proyecto.service.DeclaracionService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Declaracion")
@CrossOrigin("*")
public class DeclaracionController {

    @Autowired
    DeclaracionService declaracionService;

    @Autowired
    CasoService casoService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    SesionDetails sesionDetails;

    @Autowired
    Convertidor convertidor;

    @PutMapping("/crear")
    @PreAuthorize("hasRole('ROLE_C_DECLARACION')")
    public ResponseEntity<?> crear(
            @Valid @RequestBody JsonDeclaracion JSOND,
            BindingResult BR
    ) {

        // DECLARAMOS VARIABLES
        Declaracion declaracion;

        Usuario usuario;
        Caso caso;

        DeclaracionPK declaracionPK;
        CasoPK casoPK;

        boolean abierta;
        LocalDate fecha;
        String descripcion;

        // INICIALIZAMOS VARIABLES
        declaracion = new Declaracion();

        usuario = usuarioService.findByUsuario(sesionDetails.getUsuario());

        casoPK = new CasoPK(JSOND.getIdDenuncia(), JSOND.getIdComision());
        caso = casoService.findByCasoPK(casoPK);

        declaracionPK = new DeclaracionPK(usuario.getId(),
                caso.getCasoPK().getDenuncia(),
                caso.getCasoPK().getComision());

        abierta = JSOND.isAbierta();
        fecha = JSOND.getFecha();
        descripcion = JSOND.getDescripcion();

        declaracion.setDeclaracionPK(declaracionPK);
        declaracion.setAbierta(abierta);
        declaracion.setFecha(convertidor.LocalDateToSqlDate(fecha));
        declaracion.setDescripcion(descripcion);

        // GUARDAMOS
        declaracionService.save(declaracion);

        return new ResponseEntity<>(
                new Mensaje("DECLARACION CREADA"),
                HttpStatus.CREATED
        );
    }

    // RRR
    // MOSTRAMOS TODOS LOS OBJETOS
    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_DECLARACION')")
    public ResponseEntity<List<Declaracion>> listar() {

        List<Declaracion> list = declaracionService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/modificar/{id}")
    @PreAuthorize("hasRole('ROLE_U_DECLARACION')")
    public ResponseEntity<?> modificar(
            @Valid @RequestBody JsonDeclaracion JSOND,
            BindingResult BR
    ) {
        // DECLARAMOS VARIABLES
        Declaracion declaracion;
        DeclaracionPK declaracionPK;
        boolean abierta;
        LocalDate fecha;
        String descripcion;

        // INICIALIZAMOS VARIABLES
        declaracionPK = new DeclaracionPK(JSOND.getIdUsuario(), JSOND.getIdDenuncia(), JSOND.getIdComision());
        declaracion = declaracionService.findByDeclaracionPK(declaracionPK);

        if (JSOND.isAbierta()) {
            abierta = JSOND.isAbierta();
        } else {
            abierta = declaracion.getAbierta();
        }
        fecha = JSOND.getFecha();
        if (!JSOND.getDescripcion().isBlank()) {
            descripcion = JSOND.getDescripcion();
        } else {
            descripcion = declaracion.getDescripcion();
        }

        declaracion.setAbierta(abierta);
        declaracion.setFecha(convertidor.LocalDateToSqlDate(fecha));
        declaracion.setDescripcion(descripcion);

        // GUARDAMOS
        declaracionService.save(declaracion);

        return new ResponseEntity<>(
                new Mensaje("DECLARACION MODIFICADA"),
                HttpStatus.CREATED
        );
    }

    // DDD
    @DeleteMapping("/borrar")
    @PreAuthorize("hasRole('ROLE_D_DECLARACION')")
    @ResponseBody
    public ResponseEntity<?> borrar(@RequestBody JsonBorrarDeclaraciones JSONBD) {

        List<Declaracion> LD = new LinkedList<>();
        List<DeclaracionPK> LDPK = JSONBD.getLDPK();

        for (int i = 0; i < LDPK.size(); i++) {
            LD.add(declaracionService.findByDeclaracionPK(LDPK.get(i)));
        }

        declaracionService.deleteAll(LD);
        // VERIFICAMOS QUE TODOS LOS ID EXISTAN
        // BORRAMOS LOS ID
        return new ResponseEntity<>(
                new Mensaje(" OBJETOS BORRADOS: [ " + LD.size() + " ]"),
                HttpStatus.OK
        );
    }

}
