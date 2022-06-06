package com.pid.proyecto.Validator;

import com.pid.proyecto.Json.Borrar.JsonBorrarRol;
import com.pid.proyecto.Json.Crear.JsonCrearRol;
import com.pid.proyecto.Json.Modificar.JsonModificarRol;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.service.PermisoService;
import com.pid.proyecto.service.RolService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ValidatorRol {

    @Autowired
    PermisoService permisoService;
    @Autowired
    RolService rolService;

    public ResponseEntity<?> ValidarJsonCrearRol(JsonCrearRol JSONR) {
        List<String> respuesta = new LinkedList<>();

        // VALIDAR QUE EXISTAN TODOS LOS ID DE PERMISOS 
        for (int id : JSONR.getPermisos()) {
            if (!permisoService.existsById(id)) {
                respuesta.add(" EL PERMISO CON ID: " + id + ", NO EXISTE");
            }
        }

        // VERIFICAMOS QUE EL NOMBRE QUE LE VAMOS A ASIGNAR AL ROL NO ESTE REPETIDO
        if (rolService.existsByRol(JSONR.getRol())) {
            respuesta.add(" YA EXISTE UN ROL CON EL NOMBRE " + JSONR.getRol());
        }

        // VERIFICAMOS QUE SE ESTEN AGREGANDO PERMISOS A NUESTRO ROL
        if (JSONR.getPermisos().isEmpty()) {
            respuesta.add(" DEBE ASIGNAR PERMISOS A ESTE ROL");
        }

        if (!respuesta.isEmpty()) {
            return new ResponseEntity<>(
                    new Mensaje(respuesta.toString()),
                    HttpStatus.PRECONDITION_FAILED
            );
        }

        return null;
    }

    public ResponseEntity<?> ValidarJsonModificarRol(JsonModificarRol JSONR) {

        Rol rol = rolService.findById(JSONR.getId());
        List<String> respuesta = new LinkedList<>();

        // VERIFICAR QUE EXISTE EL ROL CON EL ID ESPECIFICADO
        if (!rolService.existsById(JSONR.getId())) {
            respuesta.add(" EL ROL CON ID: " + JSONR.getId() + " NO EXISTE");
        }

        // VERIFICAR QUE EL NOMBRE DEL ROL ESTA BIEN ESCRITO
        if (!JSONR.getRol().matches("ROLE_[A-Z][A-Z|_]+")) {
            respuesta.add(" FORMATO DE ROL INCORRECTO, EL ROL DEBE COMENZAR CON [ROL_] Y SOLO ADMITE [_] Y [A-Z]");
        }

        // VERIFICAR QUE LOS PERMISOS QUE VAMOS A AGREGAR EXISTAN
        for (int idp : JSONR.getPermisos()) {
            if (!permisoService.existsById(idp)) {
                respuesta.add(" NO EXISTE EL PERMISO CON ID: " + idp + "");
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

    public ResponseEntity<?> ValidarJsonBorrarRol(JsonBorrarRol JSONR) {

        // VERIFICAR QUE EXISTE EL ROL CON EL ID ESPECIFICADO
        for (int id : JSONR.getIDS()) {
            if (!rolService.existsById(id)) {
                return new ResponseEntity<>(
                        new Mensaje("EL ROL CON ID: " + id + " NO EXISTE"),
                        HttpStatus.NOT_FOUND
                );
            }
        }

        return null;
    }
}
