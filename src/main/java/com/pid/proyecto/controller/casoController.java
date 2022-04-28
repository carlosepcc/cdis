package com.pid.proyecto.controller;

import com.pid.proyecto.Json.Borrar.JsonBorrarCasos;
import com.pid.proyecto.Json.JsonCaso;
import com.pid.proyecto.auxiliares.Convertidor;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.entity.Caso;
import com.pid.proyecto.entity.CasoPK;
import com.pid.proyecto.entity.Comision;
import com.pid.proyecto.entity.Denuncia;
import com.pid.proyecto.service.CasoService;
import com.pid.proyecto.service.ComisionService;
import com.pid.proyecto.service.DenunciaService;
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
@RequestMapping("/Caso")
@CrossOrigin("*")
public class CasoController {

    @Autowired
    DenunciaService denunciaService;

    @Autowired
    ComisionService comisionService;

    @Autowired
    CasoService casoService;

    @Autowired
    Convertidor convertidor;

    @PutMapping("/crear")
    @PreAuthorize("hasRole('ROLE_C_CASO')")
    public ResponseEntity<?> crear(
            @Valid @RequestBody JsonCaso JSONC,
            BindingResult BR
    ) {

        // DECLARAMOS VARIABLES
        Caso caso;
        Denuncia denuncia;
        Comision comision;
        CasoPK casoPK;
        boolean abierto;
        LocalDate fechaApertura;
        LocalDate fechaExpiracion;

        // INICIALIZAMOS VARIABLES
        caso = new Caso();
        denuncia = denunciaService.findById(JSONC.getIdDenuncia());
        comision = comisionService.findById(JSONC.getIdComision());
        casoPK = new CasoPK(denuncia.getId(), comision.getId());
        abierto = JSONC.isAbierto();
        fechaApertura = JSONC.getFechaApertura();
        fechaExpiracion = LocalDate.of(JSONC.getAnoExp(), JSONC.getMesExp(), JSONC.getDiaExp());

        caso.setCasoPK(casoPK);
        caso.setAbierto(abierto);
        caso.setFechaapertura(convertidor.LocalDateToSqlDate(fechaApertura));
        caso.setFechaexpiracion(convertidor.LocalDateToSqlDate(fechaExpiracion));

        // GUARDAMOS
        casoService.save(caso);

        return new ResponseEntity<>(
                new Mensaje("CASO CREADO"),
                HttpStatus.CREATED
        );
    }

    // RRR
    // MOSTRAMOS TODOS LOS OBJETOS
    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_CASO')")
    public ResponseEntity<List<Caso>> listar() {

        List<Caso> list = casoService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/modificar/{id}")
    @PreAuthorize("hasRole('ROLE_U_CASO')")
    public ResponseEntity<?> modificar(
            @PathVariable("id") List<Integer> idDCPK,
            @Valid @RequestBody JsonCaso JSONC,
            BindingResult BR
    ) {
        // DECLARAMOS VARIABLES
        Caso caso;
        CasoPK casoPK;
        boolean abierto;
        LocalDate fechaExpiracion;

        // INICIALIZAMOS VARIABLES
        casoPK = new CasoPK(idDCPK.get(0), idDCPK.get(1));
        caso = casoService.findByCasoPK(casoPK);

        if (!JSONC.isAbierto()) {
            abierto = JSONC.isAbierto();
        } else {
            abierto = caso.getAbierto();
        }

        if (JSONC.getAnoExp() != -1) {
            fechaExpiracion = LocalDate.of(JSONC.getAnoExp(), JSONC.getMesExp(), JSONC.getDiaExp());
        } else {
            fechaExpiracion = convertidor.SqlDateToLocalDate((Date) caso.getFechaexpiracion());
        }
        caso.setAbierto(abierto);
        caso.setFechaexpiracion(convertidor.LocalDateToSqlDate(fechaExpiracion));

        // GUARDAMOS
        casoService.save(caso);

        return new ResponseEntity<>(
                new Mensaje("CASO MODIFICADO"),
                HttpStatus.CREATED
        );
    }

    // DDD
    @DeleteMapping("/borrar")
    @PreAuthorize("hasRole('ROLE_D_CASO')")
    @ResponseBody
    public ResponseEntity<?> borrar(@RequestBody JsonBorrarCasos JSONB) {

        List<Caso> LC = new LinkedList<>();
        List<CasoPK> LCPK = JSONB.getLCPK();

        for (int i = 0; i < LCPK.size(); i++) {
            LC.add(casoService.findByCasoPK(LCPK.get(i)));
        }

        casoService.deleteAll(LC);

        // VERIFICAMOS QUE TODOS LOS ID EXISTAN
        // BORRAMOS LOS ID
        return new ResponseEntity<>(
                new Mensaje(" CASOS BORRADOS: [ " + LCPK.size() + " ]"),
                HttpStatus.OK
        );
    }
}
