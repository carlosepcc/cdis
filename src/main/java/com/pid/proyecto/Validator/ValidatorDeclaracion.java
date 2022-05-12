package com.pid.proyecto.Validator;

import com.pid.proyecto.Json.Borrar.JsonBorrarDeclaraciones;
import com.pid.proyecto.Json.Crear.JsonCrearDeclaracion;
import com.pid.proyecto.Json.Crear.JsonCrearExpediente;
import com.pid.proyecto.Json.Modificar.JsonModificarDeclaracion;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.auxiliares.SesionDetails;
import com.pid.proyecto.entity.CasoPK;
import com.pid.proyecto.entity.Declaracion;
import com.pid.proyecto.entity.DeclaracionPK;
import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.entity.RolPermiso;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.enums.PermisoNombre;
import com.pid.proyecto.enums.RolNombre;
import com.pid.proyecto.service.CasoService;
import com.pid.proyecto.service.DeclaracionService;
import com.pid.proyecto.service.RolService;
import com.pid.proyecto.service.UsuarioService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ValidatorDeclaracion {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    CasoService casoService;

    @Autowired
    DeclaracionService declaracionService;

    @Autowired
    RolService rolService;

    @Autowired
    SesionDetails sesionDetails;

    public ResponseEntity ValidarJsonCrearDeclaracion(JsonCrearDeclaracion JSOND) {

        boolean existe = false;

        List<String> respuesta = new LinkedList();

        if (!usuarioService.existsByUsuario(JSOND.getUsuario())) {
            respuesta.add(" NO EXISTE EL USUARIO CON ID: " + JSOND.getUsuario());
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
    
     public ResponseEntity<?> ValidarJsonCrearExpediente(JsonCrearExpediente JSONE, List<MultipartFile> files) {
        List<String> respuesta = new LinkedList<>();

        if (!declaracionService.existsByDeclaracionPK(JSONE.getDeclaracionPK())) {
            respuesta.add(" NO EXISTE LA DECLARACION CON ID: " + JSONE.getDeclaracionPK().toString());
        }
        
        if(files.isEmpty())
        {
        respuesta.add("DEBE GUARDAR AL MENOS UN FICHERO");
        
        }
        
        if (!respuesta.isEmpty()) {
            return new ResponseEntity<>(
                    new Mensaje(respuesta.toString()),
                    HttpStatus.PRECONDITION_FAILED
            );
        }

        return null;
    }

    public ResponseEntity ValidarJsonModificarDeclaracion(JsonModificarDeclaracion JSOND, List<MultipartFile> files) {
        List<String> respuesta = new LinkedList();
        Usuario usuario = usuarioService.findByUsuario(JSOND.getUsuario());
        DeclaracionPK DPK = new DeclaracionPK(usuario.getUsuario(), JSOND.getIdDenuncia(), JSOND.getIdComision());
        
        if (!declaracionService.existsByDeclaracionPK(DPK)) {
            respuesta.add("NO EXISTE LA DECLARACION QUE QUIERE MODIFICAR");
        }

        Declaracion declaracion = declaracionService.findByDeclaracionPK(DPK);

        // SI EL USUARIO QUE ESTA EN SESION ES EL MISMO
        // QUE DEBE HACER LA DECLARACION ENTONCES EVITAMOS QUE PUEDA MODIFICARLA SI ESTA YA ESTA CERRADA
        if (sesionDetails.getUsuario().equals(declaracion.getDeclaracionPK().getUsuario())) {

            // SI LA DECLARACION SE ENCUENTRA CERRADA ENTONCES EL USUARIO NO PUEDE MODIFICARLA
            if (!declaracion.getAbierta()) {
                respuesta.add("USTED NO PUEDE MODIFICAR ESTA DECLARACION MAS DE 1 VEZ, CONTACTE CON SU COMISION SI DESEA HACER CAMBIOS");
            }
        }

        // SI EL USUARIO QUE ESTA EN SESION NO ES EL MISMO
        // QUE EL DEBE HACER LA DECLARACION ENTONCES EVITAMOS
        // QUE PUEDA MODIFICAR ALGO APARTE DE SI ESTA ABIERTA O CERRADA
        if (!files.isEmpty()) {
            if (!sesionDetails.getUsuario().equals(JSOND.getUsuario())) {
                respuesta.add("USTED NO ES EL PROPIETARIO DE ESTA DECLARACION");
            }
        } else {
            if (sesionDetails.getUsuario().equals(JSOND.getUsuario())) {
                respuesta.add("DEBE SUBIR EL DOCUMENTO DE DECLARACION");
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
