package com.pid.proyecto.Validator;

import com.pid.proyecto.Json.Borrar.JsonBorrarCasos;
import com.pid.proyecto.Json.Crear.JsonCrearCaso;
import com.pid.proyecto.Json.Modificar.JsonModificarCaso;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.entity.CasoPK;
import com.pid.proyecto.service.CasoService;
import com.pid.proyecto.service.ComisionService;
import com.pid.proyecto.service.DenunciaService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ValidatorCaso {

    @Autowired
    DenunciaService denunciaService;
    @Autowired
    ComisionService comisionService;
    @Autowired
    CasoService casoService;

    public ResponseEntity<?> ValidarJsonCrearCaso(JsonCrearCaso JSONC) {
        List<String> respuesta = new LinkedList();

        if (!denunciaService.existsById(JSONC.getIdDenuncia())) {
            respuesta.add("NO EXISTE LA DENUNCIA CON ID: " + JSONC.getIdDenuncia());
        }
        if (!comisionService.existsById(JSONC.getIdComision())) {
            respuesta.add("NO EXISTE LA COMISION CON ID: " + JSONC.getIdComision());
        }

        if (!respuesta.isEmpty()) {
            return new ResponseEntity<>(
                    new Mensaje(respuesta.toString()),
                    HttpStatus.PRECONDITION_FAILED
            );
        }

        return null;
    }

    public ResponseEntity ValidarJsonModificarCaso(JsonModificarCaso JSONC) {

        List<String> respuesta = new LinkedList<>();

        if (!casoService.existsByCasoPK(new CasoPK(JSONC.getIdDenuncia(), JSONC.getIdComision()))) {
            respuesta.add("EL CASO QUE INTENTA MODIFICAR NO EXISTE");
        }

        if (!respuesta.isEmpty()) {
            return new ResponseEntity<>(
                    new Mensaje(respuesta.toString()),
                    HttpStatus.PRECONDITION_FAILED
            );
        }

        return null;

    }

    public ResponseEntity ValidarJsonBorrarCaso(JsonBorrarCasos JSONC) {
        List<String> respuesta = new LinkedList<>();

        for (CasoPK PK : JSONC.getLCPK()) {
            if (!casoService.existsByCasoPK(PK)) {
                respuesta.add(" NO EXISTE EL CASO CON ID DE DENUNCIA: " + PK.getDenuncia() + " E ID DE COMISION: " + PK.getComision());

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
