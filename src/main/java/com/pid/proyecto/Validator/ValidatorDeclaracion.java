package com.pid.proyecto.Validator;

import com.pid.proyecto.Json.Borrar.JsonBorrarDeclaraciones;
import com.pid.proyecto.Json.Crear.JsonCrearDeclaracion;
import com.pid.proyecto.Json.Modificar.JsonModificarDeclaracion;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.auxiliares.SesionDetails;
import com.pid.proyecto.entity.CasoPK;
import com.pid.proyecto.entity.Declaracion;
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

    @Autowired
    SesionDetails sesionDetails;

    public ResponseEntity ValidarJsonCrearDeclaracion(JsonCrearDeclaracion JSOND) {

        List<String> respuesta = new LinkedList();

        if (!usuarioService.existsById(JSOND.getIdUsuario())) {
            respuesta.add(" NO EXISTE EL USUARIO CON ID: " + JSOND.getIdUsuario());
        }

        if (!casoService.existsByCasoPK(new CasoPK(JSOND.getIdDenuncia(), JSOND.getIdComision()))) {
            respuesta.add(" NO EXISTE EL CASO CON ID DE DENUNCIA: " + JSOND.getIdDenuncia() + " E ID DE COMISION: " + JSOND.getIdComision());
        }

        // SI EL USUARIO QUE ESTA EN SESION NO ES EL MISMO
        // QUE EL DEBE HACER LA DECLARACION ENTONCES EVITAMOS
        // QUE PUEDA MODIFICAR ALGO APARTE DE SI ESTA ABIERTA O CERRADA
        if (!JSOND.getDescripcion().isBlank()) {
            if (!sesionDetails.getUsuario().equals(usuarioService.findById(
                    JSOND.getIdUsuario()
            ).getUsuario())) {
                respuesta.add("USTED NO ES EL PROPIETARIO DE ESTA DECLARACION");
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

    public ResponseEntity ValidarJsonModificarDeclaracion(JsonModificarDeclaracion JSOND) {
        List<String> respuesta = new LinkedList();

        if (!declaracionService.existsByDeclaracionPK(new DeclaracionPK(JSOND.getIdUsuario(), JSOND.getIdDenuncia(), JSOND.getIdComision()))) {
            respuesta.add("NO EXISTE LA DECLARACION QUE QUIERE MODIFICAR");
        }

        Declaracion declaracion = declaracionService.findByDeclaracionPK(new DeclaracionPK(JSOND.getIdUsuario(), JSOND.getIdDenuncia(), JSOND.getIdComision()));

        // SI EL USUARIO QUE ESTA EN SESION ES EL MISMO
        // QUE DEBE HACER LA DECLARACION ENTONCES EVITAMOS QUE PUEDA MODIFICARLA SI ESTA YA ESTA CERRADA
        if (sesionDetails.getUsuario().equals(usuarioService.findById(
                declaracion.getDeclaracionPK().getUsuario()
        ).getUsuario())) {

            // SI LA DECLARACION SE ENCUENTRA CERRADA ENTONCES EL USUARIO NO PUEDE MODIFICARLA
            if (!declaracion.getAbierta()) {
                respuesta.add("USTED NO PUEDE MODIFICAR ESTA DECLARACION MAS DE 1 VEZ, CONTACTE CON SU COMISION SI DESEA HACER CAMBIOS");
            }
        }

        if (!JSOND.getDescripcion().isBlank()) {

            // SI EL USUARIO QUE ESTA EN SESION NO ES EL MISMO
            // QUE EL DEBE HACER LA DECLARACION ENTONCES EVITAMOS
            // QUE PUEDA MODIFICAR ALGO APARTE DE SI ESTA ABIERTA O CERRADA
            if (!sesionDetails.getUsuario().equals(usuarioService.findById(
                    declaracion.getDeclaracionPK().getUsuario()
            ).getUsuario())) {

                respuesta.add("USTED NO ES EL PROPIETARIO DE ESTA DECLARACION, NO PUEDE MODIFICARLA");

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

    public ResponseEntity ValidarJsonBorrarDeclaracion(JsonBorrarDeclaraciones JSOND) {
        List<String> respuesta = new LinkedList();

        for (DeclaracionPK PK : JSOND.getLDPK()) {

            if (!declaracionService.existsByDeclaracionPK(PK)) {

                respuesta.add("NO EXISTE LA DECLARAACION CON ID: " + PK.toString());

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
