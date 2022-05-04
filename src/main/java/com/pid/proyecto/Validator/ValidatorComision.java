package com.pid.proyecto.Validator;

import com.pid.proyecto.Json.Borrar.JsonBorrarComision;
import com.pid.proyecto.Json.Crear.JsonCrearComision;
import com.pid.proyecto.Json.Modificar.JsonModificarComision;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.auxiliares.UsuarioRol;
import com.pid.proyecto.entity.Comision;
import com.pid.proyecto.entity.ComisionUsuario;
import com.pid.proyecto.entity.ComisionUsuarioPK;
import com.pid.proyecto.entity.Resolucion;
import com.pid.proyecto.entity.Rol;
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

        // RECORREMOS LA LISTA DE USUARIOS CON ROL
        for (UsuarioRol us : JSONC.getIntegrantesComision()) {

            // VERIFICAMOS QUE EXISTA EL ID DEL USUARIO QUE VAMOS A USAR
            if (!usuarioService.exitsById(us.getIdIntegrante())) {
                respuesta.add("NO EXISTE EL USUARIO CON ID: " + us.getIdIntegrante());
            }

            // VERIFICAMOS QUE EXISTA EL ID DEL ROL QUE VAMOS A USAR
            if (!rolService.existsById(us.getIdRol())) {
                respuesta.add("NO EXISTE EL ROL CON ID: " + us.getIdRol());
            }

            Rol rol;
            Resolucion resolucion = resolucionService.findById(JSONC.getIdResolucion());
            // obtenemos todas las comisiones que coincidan con esta resolucion
            List<Comision> LC = comisionService.findAllByResolucion(resolucion);
            List<ComisionUsuario> CU;

            // VERIFICAMOS QUE EL INTEGRANTE NO SE ENCUENTRE YA PARTICIPANDO
            // EN OTRA COMISION DE ESTA RESOLUCION
            for (Comision c : LC) {
                CU = c.getComisionUsuarioList();
                for (ComisionUsuario cu : CU) {
                    if (cu.getComisionUsuarioPK().getIdu() == us.getIdIntegrante()) {
                        respuesta.add("YA EL USUARIO DE ID: "
                                + us.getIdIntegrante()
                                + " SE ENCUENTRA EN LA COMISION DE ID: "
                                + cu.getComisionUsuarioPK().getIdc()
                                + " DENTRO DE ESTA RESOLUCION DE ID: "
                                + resolucion.getId());
                    }
                }
            }

            rol = rolService.findById(us.getIdRol());
            if (!rol.isTipoComision()) {
                respuesta.add("EL ROL QUE INTENTA ASIGNAR EXCEDE"
                        + " LOS PRIVILEGIOS DE UN INTEGRANTE DE COMISION NORMAL ");
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

    public ResponseEntity<?> ValidarJsonModificarComision(JsonModificarComision JSONC) {

        List<String> respuesta = new LinkedList<>();

        // VALIDAMOS QUE EXISTE LA COMISION
        if (!comisionService.existsById(JSONC.getIdComision())) {
            respuesta.add("NO EXISTE LA COMISION CON ID: " + JSONC.getIdComision());
        }

        Comision comision = comisionService.findById(JSONC.getIdComision());

        // VALIDAMOS QUE TODOS LOS ID QUE QUEREMOS ELIMINAR DE VERDAD SE ENCUENTREN EN NUESTRA COMISION
        if (!JSONC.getQuitarIntegrantes().isEmpty()) {
            boolean existeA = false;
            int id = -1;

            // recorremos todos los id que queremos eliminar
            for (int idI : JSONC.getQuitarIntegrantes()) {
                existeA = false;
                id = idI;
                // recorremos todas las relaciones de nuestra comision
                for (ComisionUsuario cu : comision.getComisionUsuarioList()) {
                    // si en alguna de esas relaciones se encuentra el id en el que estamos parados
                    // decimos que si existe y paramos de buscar
                    if (cu.getComisionUsuarioPK().getIdu() == idI) {
                        existeA = true;
                        break;
                    }
                }
                // si al terminar de buscar no se confirmó existencia del id entonces rompemos
                if (!existeA) {
                    break;
                }
            }
            if (!existeA) {
                respuesta.add("NO EXISTE RELACION CON EL INTEGRANTE DE ID: " + id);
            }
        }

        // recorremos todos los id que queremos agregar para verificar que ninguno exista previamente
        if (!JSONC.getAgregarIntegrantes().isEmpty()) {
            int id;
            for (UsuarioRol idI : JSONC.getAgregarIntegrantes()) {

                if (!usuarioService.exitsById(idI.getIdIntegrante())) {
                    respuesta.add("NO EXISTE UN USUARIO DE ID: " + idI.getIdIntegrante());
                }
                if (!rolService.existsById(idI.getIdRol())) {
                    respuesta.add("NO EXISTE UN ROL DE ID: " + idI.getIdRol());
                }
                if (!rolService.findById(idI.getIdRol()).isTipoComision()) {
                    respuesta.add("EL ROL DE ID: " + idI.getIdRol() + " QUE INTENTA ASIGNAR EXCEDE"
                            + " LOS PRIVILEGIOS DE UN INTEGRANTE DE COMISION NORMAL ");
                }

                id = idI.getIdIntegrante();
                // recorremos todas las relaciones de nuestra comision
                for (ComisionUsuario cu : comision.getComisionUsuarioList()) {
                    // si en alguna de esas relaciones se encuentra el id en el que estamos parados
                    // decimos que si existe y paramos de buscar
                    if (cu.getComisionUsuarioPK().getIdu() == id) {
                        respuesta.add("YA EXISTE UNA RELACION ENTRE ESTA COMISION Y EL USUARIO DE ID: " + id);
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
