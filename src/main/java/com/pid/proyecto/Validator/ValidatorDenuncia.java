package com.pid.proyecto.Validator;

import com.pid.proyecto.Json.Borrar.JsonBorrarDenuncia;
import com.pid.proyecto.Json.Crear.JsonCrearDenuncia;
import com.pid.proyecto.Json.Modificar.JsonModificarDenuncia;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.auxiliares.SesionDetails;
import com.pid.proyecto.entity.DenunciaUsuarioPK;
import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.service.DenunciaService;
import com.pid.proyecto.service.DenunciaUsuarioService;
import com.pid.proyecto.service.UsuarioService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ValidatorDenuncia {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    DenunciaService denunciaService;
    @Autowired
    DenunciaUsuarioService denunciaUsuarioService;
    @Autowired
    SesionDetails sesionDetails;

    public ResponseEntity<?> ValidarJsonCrearDenuncia(JsonCrearDenuncia JSOND) {
        List<String> respuesta = new LinkedList<>();
        
        if (!usuarioService.existsByUsuario(JSOND.getAcusado())) {
            respuesta.add("NO EXISTE EL USUARIO: " + JSOND.getAcusado());
        }

        if (!respuesta.isEmpty()) {
            return new ResponseEntity<>(
                    new Mensaje(respuesta.toString()),
                    HttpStatus.PRECONDITION_FAILED
            );
        }
        return null;
    }

    public ResponseEntity ValidarJsonModificarDenuncia(JsonModificarDenuncia JSOND) {
        List<String> respuesta = new LinkedList<>();

        
        // ESTA ES UNA LLAVE QUE RELACIONA LA DENUNCIA QUE SE INTENTA MODIFICAR CON EL USUARIO EN SESION
        DenunciaUsuarioPK PK = new DenunciaUsuarioPK(JSOND.getIdDenuncia(),
                usuarioService.findByUsuario(sesionDetails.getUsuario()).getId());

        if (!denunciaService.existsById(JSOND.getIdDenuncia())) {
            respuesta.add(" NO EXISTE LA DENUNCIA DE ID: " + JSOND.getIdDenuncia());
        }

        if (!denunciaUsuarioService.existsByDenunciaUsuarioPK(PK)) {
            respuesta.add("USTED NO ES EL PROPIETARIO DE ESTA DENUNCIA, NO PUEDE MODIFICARLA");
        }

        if (denunciaService.findById(JSOND.getIdDenuncia()).getProcesada()) {
            respuesta.add("ESTA DENUNCIA YA SE ENCUENTRA EN PROCESO, NO PUEDE MODIFICARLA");
        }

        if (!JSOND.getAcusado().isBlank()) {
            if (!usuarioService.existsByUsuario(JSOND.getAcusado())) {
                respuesta.add(" NO EXISTE EL ACUSADO CON USUARIO: " + JSOND.getAcusado());
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

    public ResponseEntity ValidarJsonBorrarDenuncia(JsonBorrarDenuncia JSOND) {

        List<String> respuesta = new LinkedList<>();

        for (int id : JSOND.getIdDenuncias()) {
            if (!denunciaService.existsById(id)) {
                if (respuesta.isEmpty()) {
                    respuesta.add(" NO EXISTE EL ID: ");
                }
                respuesta.add(" " + id);
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
