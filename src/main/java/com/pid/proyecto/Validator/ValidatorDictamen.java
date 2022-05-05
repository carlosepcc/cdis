package com.pid.proyecto.Validator;

import com.pid.proyecto.Json.Borrar.JsonBorrarDictamenes;
import com.pid.proyecto.Json.Crear.JsonCrearDictamen;
import com.pid.proyecto.Json.Modificar.JsonModificarDictamen;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.entity.DictamenPK;
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

    public ResponseEntity<?> ValidarJsonCrearDictamen(JsonCrearDictamen JSOND) {
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

    public ResponseEntity<?> ValidarJsonModificardictamen(JsonModificarDictamen JSOND) {

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

    public ResponseEntity<?> ValidarJsonBorrardictamen(JsonBorrarDictamenes JSOND) {

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
