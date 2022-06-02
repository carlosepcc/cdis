package com.pid.proyecto.controller;

import com.pid.proyecto.Json.Borrar.JsonBorrarCasos;
import com.pid.proyecto.Json.Crear.JsonCrearCaso;
import com.pid.proyecto.Json.Crear.JsonCrearDictamen;
import com.pid.proyecto.Json.Modificar.JsonModificarCaso;
import com.pid.proyecto.Validator.ValidatorCaso;
import com.pid.proyecto.auxiliares.Convertidor;
import com.pid.proyecto.auxiliares.GestionarFicheros;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.auxiliares.SesionDetails;
import com.pid.proyecto.entity.Caso;
import com.pid.proyecto.entity.CasoPK;
import com.pid.proyecto.entity.Denuncia;
import com.pid.proyecto.service.CasoService;
import com.pid.proyecto.service.ComisionService;
import com.pid.proyecto.service.DenunciaService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/caso")
@CrossOrigin("*")
public class CasoController {

    @Autowired
    GestionarFicheros gestionarFicheros;

    @Autowired
    DenunciaService denunciaService;

    @Autowired
    ComisionService comisionService;

    @Autowired
    CasoService casoService;

    @Autowired
    Convertidor convertidor;

    @Autowired
    SesionDetails sesionDetails;

    @Autowired
    ValidatorCaso validator;

    @PutMapping
    @PreAuthorize("hasRole('ROLE_C_CASO')")
    public ResponseEntity<?> crearCaso(
            @RequestBody JsonCrearCaso JSONC
    ) {

        ResponseEntity respuesta = validator.ValidarJsonCrearCaso(JSONC);
        if (respuesta != null) {
            return respuesta;
        }

        // DECLARAMOS VARIABLES
        Caso caso;
        LocalDate fechaExpiracion;

        Denuncia denuncia = denunciaService.findById(JSONC.getIdDenuncia());
        // INICIALIZAMOS VARIABLES
        caso = new Caso();
        fechaExpiracion = LocalDate.of(JSONC.getAnoExp(), JSONC.getMesExp(), JSONC.getDiaExp());
        caso.setCasoPK(new CasoPK(denuncia.getId(), comisionService.findById(JSONC.getIdComision()).getId()));
        caso.setAbierto(true);
        caso.setFechaapertura(convertidor.LocalDateToSqlDate(LocalDate.now()));
        caso.setFechaexpiracion(convertidor.LocalDateToSqlDate(fechaExpiracion));
        caso.setDictamen(null);

        // GUARDAMOS
        caso = casoService.save(caso);

        // PROCESAMOS LA DENUNCIA
        denuncia.setProcesada(true);
        denunciaService.save(denuncia);

        // CREAMOS LA CARPETA ASOCIADA A ESTE CASO
        String carpetaCaso = gestionarFicheros.CrearCarpetaCaso(caso);

        return new ResponseEntity<>(
                new Mensaje("CASO CREADO, SE HA CREADO UN DIRECTORIO DESIGNADO PARA EL USO DEL MISMO: " + carpetaCaso),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/dictamen")
    @PreAuthorize("hasRole('ROLE_C_DICTAMEN')")
    public ResponseEntity<?> crearDictamen(
            @RequestPart JsonCrearDictamen JSOND,
            @RequestPart List<MultipartFile> files
    ) {

        // VALIDAR JSON ANTES DE USARSE
        ResponseEntity respuesta = validator.ValidarJsonCrearDictamen(JSOND, files);
        if (respuesta != null) {
            return respuesta;
        }

        Caso caso = casoService.findByCasoPK(JSOND.getCasoPK());

        // CREAMOS LA CARPETA ASOCIADA A ESTE DICTAMEN Y LO GUARDAMOS EN ELLA
        caso = gestionarFicheros.GestionarCrearDictamen(caso, files);
        caso.setAbierto(false);
        casoService.save(caso);

        return new ResponseEntity<>(
                new Mensaje("DICTAMEN CREADO"),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_CASO')")
    public ResponseEntity<List<Caso>> listar() {

        List<Caso> list = casoService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_U_CASO')")
    public ResponseEntity<?> modificar(
            @RequestBody JsonModificarCaso JSONC
    ) {

        ResponseEntity respuesta = validator.ValidarJsonModificarCaso(JSONC);
        if (respuesta != null) {
            return respuesta;
        }

        // DECLARAMOS VARIABLES
        Caso caso;

        // INICIALIZAMOS VARIABLES
        caso = casoService.findByCasoPK(new CasoPK(JSONC.getIdDenuncia(), JSONC.getIdComision()));

        if (JSONC.getAnoExp() != -1 && JSONC.getMesExp() != -1 && JSONC.getDiaExp() != -1) {
            caso.setFechaexpiracion(convertidor.LocalDateToSqlDate(LocalDate.of(JSONC.getAnoExp(), JSONC.getMesExp(), JSONC.getDiaExp())));
        }

        // GUARDAMOS
        casoService.save(caso);

        return new ResponseEntity<>(
                new Mensaje("CASO MODIFICADO"),
                HttpStatus.CREATED
        );
    }

    // DDD
    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_D_CASO')")
    @ResponseBody
    public ResponseEntity<?> borrar(@RequestBody JsonBorrarCasos JSONC) {

        ResponseEntity respuesta = validator.ValidarJsonBorrarCaso(JSONC);
        if (respuesta != null) {
            return respuesta;
        }

        List<CasoPK> LCPK = JSONC.getLCPK();
        Denuncia denuncia;
        Caso caso;

        for (CasoPK PK : LCPK) {
            // SI DECIDIMOS BORRAR UN CASO DEBEMOS VERIFICAR SI YA HABIA CERRADO, SI EL CASO
            // AUN NO HABIA CERRADO ENTONCES LA DENUNCIA NUNCA PUDO TERMINAR Y POR LO TANTO SE MANTIENE
            // SIN PROCESAR NUEVAMENTE
            caso = casoService.findByCasoPK(PK);
            if (caso.getAbierto()) {
                denuncia = denunciaService.findById(PK.getDenuncia());
                denuncia.setProcesada(false);
                denunciaService.save(denuncia);
            }
            casoService.deleteByCasoPK(PK);
        }

        return new ResponseEntity<>(
                new Mensaje(" CASOS BORRADOS: [ " + LCPK.size() + " ]"),
                HttpStatus.OK
        );
    }
}
