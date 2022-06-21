package com.pid.proyecto.Validator;

import com.pid.proyecto.Json.Borrar.JsonBorrarResolucion;
import com.pid.proyecto.Json.Crear.JsonCrearResolucion;
import com.pid.proyecto.Json.EntidadesAuxiliares.ComisionReducida;
import com.pid.proyecto.Json.Modificar.JsonModificarResolucion;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.entity.Comision;
import com.pid.proyecto.entity.ComisionUsuario;
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

        if (JSONR.getAno().isBlank()) {
            respuesta.add("DEBE DECIR EL AÑO DE ESTA RESOLUCIÓN");
        }
        List<Resolucion> LR = resolucionService.findAll();
        for(Resolucion r: LR){
        
            if(r.getUrl().contains(JSONR.getAno()))
                respuesta.add("YA EXISTE UNA RESOLUCION PARA ESTE AÑO");            
        }

        List<String> LCU = new LinkedList<>();

        for (ComisionReducida c : JSONR.getComisiones()) {
            LCU.add(c.getPresidente());
            LCU.add(c.getSecretario());
        }

        for (String cu : LCU) {
            int c = 0;
            String usuario = cu;
            for (String cuu : LCU) {
                if (cuu.equals(usuario)) {
                    c++;
                    if (c > 1) {
                        respuesta.add("EL USUARIO " + cuu + "ESTA REPETIDO");
                    }
                }
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

        for (int id : JSONR.getIds()) {
            if (!resolucionService.existsById(id)) {
                respuesta.add(" NO EXISTE LA RESOLUCION CON ID: " + id);
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
