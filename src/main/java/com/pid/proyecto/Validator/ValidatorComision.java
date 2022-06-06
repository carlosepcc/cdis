package com.pid.proyecto.Validator;

import com.pid.proyecto.Json.Borrar.JsonBorrarComision;
import com.pid.proyecto.Json.Crear.JsonCrearComision;
import com.pid.proyecto.Json.Modificar.JsonModificarComision;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.auxiliares.UsuarioRol;
import com.pid.proyecto.entity.Comision;
import com.pid.proyecto.entity.ComisionUsuario;
import com.pid.proyecto.entity.Resolucion;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.service.ComisionService;
import com.pid.proyecto.service.ResolucionService;
import com.pid.proyecto.service.RolService;
import com.pid.proyecto.service.UsuarioService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ValidatorComision {

    @Autowired
    ResolucionService resolucionService;
    @Autowired
    ComisionService comisionService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    public ResponseEntity<?> ValidarJsonCrearComision(JsonCrearComision JSONC) {

        List<String> respuesta = new LinkedList<>();

        // VERIFICAMOS QUE EXISTA LA RESOLUCION
        if (!resolucionService.existsById(JSONC.getIdResolucion())) {
            respuesta.add(" NO EXISTE LA RESOLUCION CON ID: " + JSONC.getIdResolucion());
        }

        Resolucion resolucion = resolucionService.findById(JSONC.getIdResolucion());
        // obtenemos todas las comisiones que coincidan con esta resolucion
        List<Comision> LC = comisionService.findAllByResolucion(resolucion);

        // RECORREMOS LA LISTA DE USUARIOS CON ROL
        if (!JSONC.getIntegrantesComision().isEmpty()) {
            Usuario usuario;
            for (UsuarioRol UR : JSONC.getIntegrantesComision()) {

                if (JSONC.getIntegrantesComision().size() < 2) {
                    respuesta.add("DEBE AGREGAR AL MENOS 2 INTEGRANTES");
                }
                if (!usuarioService.existsByUsuario(UR.getUsuario())) {
                    respuesta.add("NO EXISTE EL USUARIO: " + UR.getUsuario());
                }
                if (!rolService.existsById(UR.getIdRol())) {
                    respuesta.add("NO EXISTE UN ROL DE ID: " + UR.getIdRol());
                }
                if (!rolService.findById(UR.getIdRol()).getTipocomision()) {
                    respuesta.add("EL ROL DE ID: " + UR.getIdRol() + " QUE INTENTA ASIGNAR EXCEDE"
                            + " LOS PRIVILEGIOS DE UN INTEGRANTE DE COMISION NORMAL ");
                }
                
                usuario = usuarioService.findByUsuario(UR.getUsuario());

                // recorremos todas las relaciones de nuestra comision
                for (Comision C : LC) {
                    for (ComisionUsuario cu : C.getComisionUsuarioList()) {
                        // si en alguna de esas relaciones se encuentra el id en el que estamos parados
                        // decimos que si existe y paramos de buscar
                        if (cu.getComisionUsuarioPK().getIdu().equals(usuario.getUsuario())) {
                            respuesta.add(" YA EL USUARIO: "
                                    + usuario.getUsuario()
                                    + " SE ENCUENTRA EN LA COMISION DE ID: "
                                    + cu.getComisionUsuarioPK().getIdc()
                                    + " DENTRO DE ESTA RESOLUCION DE ID: "
                                    + resolucion.getId());
                        }
                    }
                }

            }
        } else {
            respuesta.add("DEBE AGREGAR AL MENOS 2 INTEGRANTES");
        }

        if (!respuesta.isEmpty()) {
            return new ResponseEntity<>(
                    new Mensaje(respuesta.toString()),
                    HttpStatus.PRECONDITION_FAILED
            );
        }

        return null;
    }

    public ResponseEntity<?> ValidarJsonModificarComision(JsonModificarComision JSONC) {

        List<String> respuesta = new LinkedList<>();

        // VALIDAMOS QUE EXISTE LA COMISION
        if (!comisionService.existsById(JSONC.getIdComision())) {
            respuesta.add("NO EXISTE LA COMISION CON ID: " + JSONC.getIdComision());
        }

        Comision comision = comisionService.findById(JSONC.getIdComision());
        Resolucion resolucion = comision.getResolucion();
        List<Comision> LC = resolucion.getComisionList();


        // recorremos todos los id que queremos agregar para verificar que ninguno exista previamente
        if (!JSONC.getIntegrantes().isEmpty()) {
            Usuario usuario;
            for (UsuarioRol UR : JSONC.getIntegrantes()) {

                if (!usuarioService.existsByUsuario(UR.getUsuario())) {
                    respuesta.add("NO EXISTE UN USUARIO DE ID: " + UR.getUsuario());
                }
                if (!rolService.existsById(UR.getIdRol())) {
                    respuesta.add("NO EXISTE UN ROL DE ID: " + UR.getIdRol());
                }
                if (!rolService.findById(UR.getIdRol()).getTipocomision()) {
                    respuesta.add("EL ROL DE ID: " + UR.getIdRol() + " QUE INTENTA ASIGNAR EXCEDE"
                            + " LOS PRIVILEGIOS DE UN INTEGRANTE DE COMISION NORMAL ");
                }

                usuario = usuarioService.findByUsuario(UR.getUsuario());
                
                // recorremos todas las relaciones de nuestra resolucion
                for (Comision C : LC) {
                    for (ComisionUsuario cu : C.getComisionUsuarioList()) {
                        // si en alguna de esas relaciones se encuentra el id en el que estamos parados
                        // decimos que si existe y paramos de buscar
                        if (cu.getComisionUsuarioPK().getIdu().equals(usuario.getUsuario())) {
                            respuesta.add("YA EL USUARIO DE ID: "
                                    + UR.getUsuario()
                                    + " SE ENCUENTRA EN LA COMISION DE ID: "
                                    + cu.getComisionUsuarioPK().getIdc()
                                    + " DENTRO DE ESTA RESOLUCION DE ID: "
                                    + resolucion.getId());
                        }
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

    public ResponseEntity ValidarJsonBorrarComision(JsonBorrarComision JSONC) {

        for (int id : JSONC.getIdComisiones()) {
            if (!comisionService.existsById(id)) {
                return new ResponseEntity<>(
                        new Mensaje("NO EXISTE LA COMISION CON ID: " + id),
                        HttpStatus.NOT_FOUND
                );
            }
        }
        return null;
    }
}
