package com.pid.proyecto.controller;

import com.pid.proyecto.Json.Borrar.JsonBorrarDictamen;
import com.pid.proyecto.Json.Crear.JsonCrearDictamen;
import com.pid.proyecto.Json.Modificar.JsonModificarDictamen;
import com.pid.proyecto.Validator.ValidatorDictamen;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.entity.Caso;
import com.pid.proyecto.entity.CasoPK;
import com.pid.proyecto.entity.Comision;
import com.pid.proyecto.entity.Denuncia;
import com.pid.proyecto.entity.Dictamen;
import com.pid.proyecto.entity.DictamenPK;
import com.pid.proyecto.service.CasoService;
import com.pid.proyecto.service.ComisionService;
import com.pid.proyecto.service.DenunciaService;
import com.pid.proyecto.service.DictamenService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Dictamen")
@CrossOrigin("*")
public class DictamenController {

    @Autowired
    DenunciaService denunciaService;
    @Autowired
    ComisionService comisionService;
    @Autowired
    DictamenService dictamenService;
    @Autowired
    CasoService casoService;
    @Autowired
    ValidatorDictamen validator;

    @PutMapping("/crear")
    @PreAuthorize("hasRole('ROLE_C_DICTAMEN')")
    public ResponseEntity<?> crear(
            @Valid @RequestBody JsonCrearDictamen JSOND,
            BindingResult BR
    ) {

        // VALIDAR JSON ANTES DE USARSE
        ResponseEntity respuesta = validator.ValidarJsonCrearDictamen(JSOND);
        if (respuesta != null) {
            return respuesta;
        }

        // DECLARAMOS VARIABLES
        Dictamen dictamen = new Dictamen();

        Denuncia denuncia = denunciaService.findById(JSOND.getIdDenuncia());
        Comision comision = comisionService.findById(JSOND.getIdComision());

        dictamen.setDictamenPK(new DictamenPK(denuncia.getId(), comision.getId()));
        dictamen.setDescripcion(JSOND.getDescripcion());

        // GUARDAMOS
        dictamenService.save(dictamen);

        // CERRAMOS EL CASO
        Caso caso = casoService.findByCasoPK(new CasoPK(denuncia.getId(), comision.getId()));
        caso.setAbierto(false);
        casoService.save(caso);

        return new ResponseEntity<>(
                new Mensaje("DICTAMEN CREADO"),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_DICTAMEN')")
    public ResponseEntity<List<Dictamen>> listar() {

        List<Dictamen> list = dictamenService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasRole('ROLE_U_DICTAMEN')")
    public ResponseEntity<?> modificar(
            @RequestBody JsonModificarDictamen JSOND
    ) {

        // VALIDAR JSON ANTES DE USARSE
        ResponseEntity respuesta = validator.ValidarJsonModificarDictamen(JSOND);
        if (respuesta != null) {
            return respuesta;
        }

        // DECLARAMOS VARIABLES
        Dictamen dictamen;
        Denuncia denuncia;
        Comision comision;
        DictamenPK PK;

        // INICIALIZAMOS VARIABLES
        denuncia = denunciaService.findById(JSOND.getIdDenuncia());
        comision = comisionService.findById(JSOND.getIdComision());
        PK = new DictamenPK(denuncia.getId(), comision.getId());
        dictamen = dictamenService.findByDictamenPK(PK);

        if (!JSOND.getDescripcion().isBlank()) {
            dictamen.setDescripcion(JSOND.getDescripcion());
        }

        // GUARDAMOS
        dictamenService.save(dictamen);

        return new ResponseEntity<>(
                new Mensaje("DICTAMEN ACTUALIZADO"),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/borrar")
    @PreAuthorize("hasRole('ROLE_D_DICTAMEN')")
    @ResponseBody
    public ResponseEntity<?> borrar(@RequestBody JsonBorrarDictamen JSOND) {

        // VALIDAR JSON ANTES DE USARSE
        ResponseEntity respuesta = validator.ValidarJsonBorrarDictamen(JSOND);
        if (respuesta != null) {
            return respuesta;
        }

        for (DictamenPK PK : JSOND.getLPK()) {
            dictamenService.deleteByDictamenPK(PK);
        }

        return new ResponseEntity<>(
                new Mensaje("DICTAMENES BORRADOS: [ " + JSOND.getLPK().size() + " ]"),
                HttpStatus.OK
        );
    }

}
