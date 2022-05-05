package com.pid.proyecto.Validator;

import com.pid.proyecto.Json.Borrar.JsonBorrarExpedientes;
import com.pid.proyecto.Json.Crear.JsonCrearExpediente;
import com.pid.proyecto.Json.Modificar.JsonModificarExpediente;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.entity.DeclaracionPK;
import com.pid.proyecto.entity.ExpedientePK;
import com.pid.proyecto.service.DeclaracionService;
import com.pid.proyecto.service.ExpedienteService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ValidatorExpediente {

    @Autowired
    ExpedienteService expedienteService;
    @Autowired
    DeclaracionService declaracionService;

    public ResponseEntity<?> ValidarJsonCrearExpediente(JsonCrearExpediente JSONE) {
        List<String> respuesta = new LinkedList<>();

        if (!declaracionService.existsByDeclaracionPK(new DeclaracionPK(
                JSONE.getIdUsuario(),
                JSONE.getIdDenuncia(),
                JSONE.getIdComision()))) {
            respuesta.add(" NO EXISTE LA DECLARACION CON ID: " + new DeclaracionPK(
                    JSONE.getIdUsuario(),
                    JSONE.getIdDenuncia(),
                    JSONE.getIdComision()).toString());
        }
        
        if (expedienteService.existsByExpedientePK(new ExpedientePK(
                JSONE.getIdUsuario(),
                JSONE.getIdDenuncia(),
                JSONE.getIdComision()))) {
            respuesta.add("YA EXISTE UN EXPEDIENTE CON ESTE ID");
        }
        

        if (!respuesta.isEmpty()) {
            return new ResponseEntity<>(
                    new Mensaje(respuesta.toString()),
                    HttpStatus.PRECONDITION_FAILED
            );
        }

        return null;
    }

    public ResponseEntity<?> ValidarJsonModificarExpediente(JsonModificarExpediente JSONE) {

        List<String> respuesta = new LinkedList<>();

        if (!expedienteService.existsByExpedientePK(new ExpedientePK(
                JSONE.getIdUsuario(),
                JSONE.getIdDenuncia(),
                JSONE.getIdComision()))) {
            respuesta.add(" NO EXISTE EL EXPEDIENTE CON ID: " + new ExpedientePK(
                    JSONE.getIdUsuario(),
                    JSONE.getIdDenuncia(),
                    JSONE.getIdComision()).toString());
        }

        if (!respuesta.isEmpty()) {
            return new ResponseEntity<>(
                    new Mensaje(respuesta.toString()),
                    HttpStatus.PRECONDITION_FAILED
            );
        }

        return null;
    }

    public ResponseEntity<?> ValidarJsonBorrarExpediente(JsonBorrarExpedientes JSONE) {

        List<String> respuesta = new LinkedList<>();

        for (ExpedientePK PK : JSONE.getLPK()) {
            if (!expedienteService.existsByExpedientePK(PK)) {
                respuesta.add(" NO EXISTE EL EXPEDIENTE CON ID: " + PK.toString());
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
