package com.pid.proyecto.Validator;

import com.pid.proyecto.Json.Borrar.JsonBorrarDeclaraciones;
import com.pid.proyecto.Json.Crear.JsonCrearDeclaracion;
import com.pid.proyecto.Json.Modificar.JsonModificarDeclaracion;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.entity.CasoPK;
import com.pid.proyecto.entity.DeclaracionPK;
import com.pid.proyecto.service.CasoService;
import com.pid.proyecto.service.DeclaracionService;
import com.pid.proyecto.service.UsuarioService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ValidatorDeclaracion {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    CasoService casoService;

    @Autowired
    DeclaracionService declaracionService;

    public ResponseEntity ValidarJsonCrearDeclaracion(JsonCrearDeclaracion JSOND) {

        List<String> respuesta = new LinkedList();

        if (!usuarioService.existsById(JSOND.getIdUsuario())) {
            respuesta.add(" NO EXISTE EL USUARIO CON ID: " + JSOND.getIdUsuario());
        }

        if (!casoService.existsByCasoPK(new CasoPK(JSOND.getIdDenuncia(), JSOND.getIdComision()))) {
            respuesta.add(" NO EXISTE EL CASO CON ID DE DENUNCIA: " + JSOND.getIdDenuncia() + " E ID DE COMISION: " + JSOND.getIdComision());
        }

        if (!respuesta.isEmpty()) {
            return new ResponseEntity<>(
                    new Mensaje(respuesta.toString()),
                    HttpStatus.PRECONDITION_FAILED
            );
        }

        return null;

    }

    public ResponseEntity ValidarJsonModificarDeclaracion(JsonModificarDeclaracion JSOND) {
        List<String> respuesta = new LinkedList();

        if (!declaracionService.existsByDeclaracionPK(new DeclaracionPK(JSOND.getIdUsuario(), JSOND.getIdDenuncia(), JSOND.getIdComision()))) {
            respuesta.add("NO EXISTE LA DENUNCIA QUE QUIERE MODIFICAR");
        }
        if (!respuesta.isEmpty()) {
            return new ResponseEntity<>(
                    new Mensaje(respuesta.toString()),
                    HttpStatus.PRECONDITION_FAILED
            );
        }

        return null;
    }

    public ResponseEntity ValidarJsonBorrarDeclaracion(JsonBorrarDeclaraciones JSOND) {
        List<String> respuesta = new LinkedList();

        for (DeclaracionPK PK : JSOND.getLDPK()) {

            if (!declaracionService.existsByDeclaracionPK(PK)) {

                respuesta.add("NO EXISTE LA DENUNCIA CON ID: " + PK.toString());

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
