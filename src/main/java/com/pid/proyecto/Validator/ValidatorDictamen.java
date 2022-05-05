package com.pid.proyecto.Validator;

import com.pid.proyecto.Json.Borrar.JsonBorrarDictamen;
import com.pid.proyecto.Json.Crear.JsonCrearDictamen;
import com.pid.proyecto.Json.Modificar.JsonModificarDictamen;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.entity.CasoPK;
import com.pid.proyecto.entity.DictamenPK;
import com.pid.proyecto.service.CasoService;
import com.pid.proyecto.service.DictamenService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ValidatorDictamen {

    @Autowired
    DictamenService dictamenService;
    
    @Autowired
    CasoService casoService;

    public ResponseEntity<?> ValidarJsonCrearDictamen(JsonCrearDictamen JSOND) {
        List<String> respuesta = new LinkedList<>();

        // SI NO EXISTE EL CASO ENTONCES NO PUEDE EXISTIR EL DICTAMEN
        if (!casoService.existsByCasoPK(new CasoPK(
                JSOND.getIdDenuncia(), JSOND.getIdComision()))) {
            
            respuesta.add(" NO EXISTE EL CASO CON ID: " + new CasoPK(
                    JSOND.getIdDenuncia(), JSOND.getIdComision()).toString() + " PARA PODER CREAR ESTE DICTAMEN");
        }
        
        if (dictamenService.existsByDictamenPK(new DictamenPK(
                JSOND.getIdDenuncia(), JSOND.getIdComision()))) {
            
            respuesta.add(" YA EXISTE UN DICTAMEN CON ID: " + new DictamenPK(
                    JSOND.getIdDenuncia(), JSOND.getIdComision()).toString());
        }

        if (!respuesta.isEmpty()) {
            return new ResponseEntity<>(
                    new Mensaje(respuesta.toString()),
                    HttpStatus.PRECONDITION_FAILED
            );
        }

        return null;
    }

    public ResponseEntity<?> ValidarJsonModificarDictamen(JsonModificarDictamen JSOND) {

        List<String> respuesta = new LinkedList<>();

        if (!dictamenService.existsByDictamenPK(new DictamenPK(
                JSOND.getIdDenuncia(), JSOND.getIdComision()))) {
            
            respuesta.add(" NO EXISTE EL DICTAMEN CON ID: " + new DictamenPK(
                    JSOND.getIdDenuncia(), JSOND.getIdComision()).toString());

        }

        if (!respuesta.isEmpty()) {
            return new ResponseEntity<>(
                    new Mensaje(respuesta.toString()),
                    HttpStatus.PRECONDITION_FAILED
            );
        }

        return null;
    }

    public ResponseEntity<?> ValidarJsonBorrarDictamen(JsonBorrarDictamen JSOND) {

        List<String> respuesta = new LinkedList<>();

        for (DictamenPK PK : JSOND.getLPK()) {
            if (!dictamenService.existsByDictamenPK(PK)) {
                respuesta.add(" NO EXISTE EL DICTAMEN CON ID: " + PK.toString());
            }
        }

        if (!respuesta.isEmpty()) {
            return new ResponseEntity<>(
                    new Mensaje(respuesta.toString()),
                    HttpStatus.PRECONDITION_FAILED
            );
        }

        return null;
    }
}
