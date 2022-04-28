package com.pid.proyecto.controller;

import com.pid.proyecto.Json.Borrar.JsonBorrarDictamenes;
import com.pid.proyecto.Json.JsonDictamen;
import com.pid.proyecto.Json.JsonObjeto;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.entity.Comision;
import com.pid.proyecto.entity.Denuncia;
import com.pid.proyecto.entity.Dictamen;
import com.pid.proyecto.entity.DictamenPK;
import com.pid.proyecto.service.ComisionService;
import com.pid.proyecto.service.DenunciaService;
import com.pid.proyecto.service.DictamenService;
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
@RequestMapping("/Dictamen")
@CrossOrigin("*")
public class DictamenController {

    @Autowired
    DenunciaService denunciaService;
    @Autowired
    ComisionService comisionService;
    @Autowired
    DictamenService dictamenService;

    @PutMapping("/crear")
    @PreAuthorize("hasRole('ROLE_C_DICTAMEN')")
    public ResponseEntity<?> crear(
            @Valid @RequestBody JsonDictamen JSOND,
            BindingResult BR
    ) {

        // DECLARAMOS VARIABLES
        Dictamen dictamen;
        Denuncia denuncia;
        Comision comision;
        DictamenPK PK;
        String descripcion;

        // INICIALIZAMOS VARIABLES
        dictamen = new Dictamen();
        denuncia = denunciaService.findById(JSOND.getIdDenuncia());
        comision = comisionService.findById(JSOND.getIdComision());
        PK = new DictamenPK(denuncia.getId(), comision.getId());
        descripcion = JSOND.getDescripcion();

        dictamen.setDictamenPK(PK);
        dictamen.setDescripcion(descripcion);

        // GUARDAMOS
        dictamenService.save(dictamen);

        return new ResponseEntity<>(
                new Mensaje("DICTAMEN CREADO"),
                HttpStatus.CREATED
        );
    }

    // RRR
    // MOSTRAMOS TODOS LOS OBJETOS
    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_DICTAMEN')")
    public ResponseEntity<List<Dictamen>> listar() {

        List<Dictamen> list = dictamenService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/modificar")
    @PreAuthorize("hasRole('ROLE_U_DICTAMEN')")
    public ResponseEntity<?> modificar(
            @Valid @RequestBody JsonDictamen JSOND,
            BindingResult BR
    ) {
        // DECLARAMOS VARIABLES
        Dictamen dictamen;
        Denuncia denuncia;
        Comision comision;
        DictamenPK PK;
        String descripcion;

        // INICIALIZAMOS VARIABLES
        denuncia = denunciaService.findById(JSOND.getIdDenuncia());
        comision = comisionService.findById(JSOND.getIdComision());
        PK = new DictamenPK(denuncia.getId(), comision.getId());
        dictamen = dictamenService.findByDictamenPK(PK);

        if (!JSOND.getDescripcion().isBlank()) {
            descripcion = JSOND.getDescripcion();
        } else {
            descripcion = dictamen.getDescripcion();
        }

        dictamen.setDescripcion(descripcion);

        // GUARDAMOS
        dictamenService.save(dictamen);

        return new ResponseEntity<>(
                new Mensaje("DICTAMEN ACTUALIZADO"),
                HttpStatus.CREATED
        );
    }

    // DDD
    @DeleteMapping("/borrar")
    @PreAuthorize("hasRole('ROLE_D_DICTAMEN')")
    @ResponseBody
    public ResponseEntity<?> borrar(@RequestBody JsonBorrarDictamenes JSONBD) {

        List<Dictamen> LD = new LinkedList<>();
        List<DictamenPK> LPK;
        
        LPK = JSONBD.getLPK();

        for (int i = 0; i < LPK.size(); i++) {
            LD.add(dictamenService.findByDictamenPK(LPK.get(i)));
        }

        // VERIFICAMOS QUE TODOS LOS ID EXISTAN
        // BORRAMOS LOS ID
        dictamenService.deleteAll(LD);
        return new ResponseEntity<>(
                new Mensaje("DICTAMENES BORRADOS: [ " + LPK.size() + " ]"),
                HttpStatus.OK
        );
    }

}
