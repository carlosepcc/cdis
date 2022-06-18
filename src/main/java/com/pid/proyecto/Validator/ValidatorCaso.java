package com.pid.proyecto.Validator;

import com.pid.proyecto.Json.Borrar.JsonBorrarCasos;
import com.pid.proyecto.Json.Crear.JsonCrearCaso;
import com.pid.proyecto.Json.Crear.JsonCrearDictamen;
import com.pid.proyecto.Json.Modificar.JsonModificarCaso;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.auxiliares.SesionDetails;
import com.pid.proyecto.entity.CasoPK;
import com.pid.proyecto.entity.Denuncia;
import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.service.CasoService;
import com.pid.proyecto.service.ComisionService;
import com.pid.proyecto.service.DenunciaService;
import com.pid.proyecto.service.UsuarioService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MultipartFilter;

@Component
public class ValidatorCaso {

    @Autowired
    DenunciaService denunciaService;
    @Autowired
    ComisionService comisionService;
    @Autowired
    CasoService casoService;
    @Autowired
    SesionDetails sesionDetails;
    @Autowired
    UsuarioService usuarioService;

    public ResponseEntity<?> ValidarJsonCrearCaso(JsonCrearCaso JSONC) {
        List<String> respuesta = new LinkedList();

        if (!denunciaService.existsById(JSONC.getIdDenuncia())) {
            respuesta.add("NO EXISTE LA DENUNCIA CON ID: " + JSONC.getIdDenuncia());
        }

        Denuncia denuncia = denunciaService.findById(JSONC.getIdDenuncia());
        if (denuncia.getProcesada()) {
            respuesta.add("LA DENUNCIA CON ID: " + JSONC.getIdDenuncia()
                    + " YA ESTÁ EN PROCESO, NO PUEDE CREAR OTRO CASO PARA ELLA");
        }

        if (!comisionService.existsById(JSONC.getIdComision())) {
            respuesta.add("NO EXISTE LA COMISION CON ID: " + JSONC.getIdComision());
        }

        if (JSONC.getAnoExp() == -1 || JSONC.getMesExp() == -1 || JSONC.getDiaExp() == -1) {
            respuesta.add("DEBE INTRODUCIR LA FECHA DE EXPIRACION");
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
        if (!casoService.findByCasoPK(new CasoPK(JSONC.getIdDenuncia(), JSONC.getIdComision())).getAbierto()) {
            respuesta.add("EL CASO QUE INTENTA MODIFICAR YA CERRÓ");
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

        for (CasoPK PK : JSONC.getIds()) {
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

    public ResponseEntity<?> ValidarJsonCrearDictamen(JsonCrearDictamen JSOND, List<MultipartFile> files) {
        List<String> respuesta = new LinkedList<>();

        // SI NO EXISTE EL CASO ENTONCES NO PUEDE EXISTIR EL DICTAMEN
        if (!casoService.existsByCasoPK(JSOND.getCasoPK())) {

            respuesta.add(" NO EXISTE EL CASO CON ID: " + new CasoPK(
                    JSOND.getCasoPK().getDenuncia(), JSOND.getCasoPK().getComision()).toString() + " PARA PODER CREAR ESTE DICTAMEN");
        }
        
        if(files.isEmpty())
        {
        respuesta.add("DEBE INTRODUCIR EL DICTAMEN QUE VA A GUARDAR");
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
