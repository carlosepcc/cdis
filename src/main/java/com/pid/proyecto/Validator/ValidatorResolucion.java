package com.pid.proyecto.Validator;

import com.pid.proyecto.Json.Borrar.JsonBorrarResolucion;
import com.pid.proyecto.Json.Crear.JsonCrearResolucion;
import com.pid.proyecto.Json.Modificar.JsonModificarResolucion;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.entity.Resolucion;
import com.pid.proyecto.service.ResolucionService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ValidatorResolucion {

    @Autowired
    ResolucionService resolucionService;

    public ResponseEntity<?> ValidarJsonCrearResolucion(JsonCrearResolucion JSONR) {
        List<String> respuesta = new LinkedList<>();
        List<Resolucion> resoluciones = resolucionService.findAll();
        List<String> urls = new LinkedList<>();

        if (JSONR.getAno().isBlank()) {
            respuesta.add("DEBE DECIR EL AÑO DE ESTA RESOLUCIÓN");
        }

        for (Resolucion r : resoluciones) {
            if (r.getUrl().equalsIgnoreCase("RESOLUCION_" + JSONR.getAno())) {
                respuesta.add("YA EXISTE ESTA RESOLUCION");
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

    public ResponseEntity<?> ValidarJsonModificarResolucion(JsonModificarResolucion JSONR) {

        List<String> respuesta = new LinkedList<>();

        if (!resolucionService.existsById(JSONR.getID())) {
            respuesta.add(" NO EXISTE LA RESOLUCION CON ID: " + JSONR.getID());
        }

        if (!respuesta.isEmpty()) {
            return new ResponseEntity<>(
                    new Mensaje(respuesta.toString()),
                    HttpStatus.PRECONDITION_FAILED
            );
        }

        return null;
    }

    public ResponseEntity<?> ValidarJsonBorrarResolucion(JsonBorrarResolucion JSONR) {

        List<String> respuesta = new LinkedList<>();

        for (int PK : JSONR.getIds()) {
            if (!resolucionService.existsById(PK)) {
                respuesta.add(" NO EXISTE LA RESOLUCION CON ID: " + PK);
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
