package com.pid.proyecto.controller;

import com.pid.proyecto.Json.JsonComision;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.entity.Comision;
import com.pid.proyecto.entity.ComisionUsuario;
import com.pid.proyecto.entity.ComisionUsuarioPK;
import com.pid.proyecto.entity.Resolucion;
import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.service.ComisionService;
import com.pid.proyecto.service.ComisionUsuarioService;
import com.pid.proyecto.service.ResolucionService;
import com.pid.proyecto.service.RolService;
import com.pid.proyecto.service.UsuarioService;
import java.util.LinkedList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Comision")
@CrossOrigin("*")
public class ComisionController {

    @Autowired
    ResolucionService resolucionService;

    @Autowired
    ComisionService comisionService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolService;

    @Autowired
    ComisionUsuarioService comisionUsuarioService;

    @PutMapping("/crear")
    @PreAuthorize("hasRole('ROLE_C_COMISION')")
    public ResponseEntity<?> crear(
            @Valid @RequestBody JsonComision JSONC,
            BindingResult BR
    ) {
        // VALIDAR ERRORES DE JSON
        if (BR.hasErrors()) {
            List<String> errores = new LinkedList<>();
            for (FieldError FE : BR.getFieldErrors()) {
                errores.add(FE.getDefaultMessage());
            }
            return new ResponseEntity<>(
                    new Mensaje(errores.toString()),
                    HttpStatus.PRECONDITION_FAILED
            );
        }

        // DECLARAMOS VARIABLES
        Comision comision;
        ComisionUsuario comisionUsuario;
        ComisionUsuarioPK comisionUsuarioPK;
        Resolucion resolucion;
        Usuario presidente;
        Usuario secretario;
        Rol rolPresidente;
        Rol rolSecretario;

        // INICIALIZAMOS VARIABLES
        comision = new Comision();
        comisionUsuario = new ComisionUsuario();
        comisionUsuarioPK = new ComisionUsuarioPK();

        resolucion = resolucionService.findById(JSONC.getIdResolucion());
        presidente = usuarioService.findById(JSONC.getIdPresidente());
        secretario = usuarioService.findById(JSONC.getIdSecretario());
        rolPresidente = rolService.findById(JSONC.getIdRolPresidente());
        rolSecretario = rolService.findById(JSONC.getIdRolSecretario());

        comision.setResolucion(resolucion);
        comision.setPusuario(presidente.getUsuario());
        comision.setSusuario(secretario.getUsuario());

        // GUARDAMOS
        comision = comisionService.save(comision);

        // RELACIONAMOS
        // PRESIDENTE
        comisionUsuarioPK.setIdc(comision.getId());
        comisionUsuarioPK.setIdu(presidente.getId());
        comisionUsuario.setComisionUsuarioPK(comisionUsuarioPK);
        comisionUsuario.setRol(rolPresidente);
        comisionUsuarioService.save(comisionUsuario);

        // RELACIONAMOS
        // SECRETARIO
        comisionUsuarioPK.setIdc(comision.getId());
        comisionUsuarioPK.setIdu(secretario.getId());
        comisionUsuario.setComisionUsuarioPK(comisionUsuarioPK);
        comisionUsuario.setRol(rolSecretario);
        comisionUsuarioService.save(comisionUsuario);

        return new ResponseEntity<>(
                new Mensaje("COMISION CREADA"),
                HttpStatus.CREATED
        );
    }

    // RRR
    // MOSTRAMOS TODOS LOS OBJETOS
    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_COMISION')")
    public ResponseEntity<List<Comision>> listar() {

        List<Comision> list = comisionService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/actualizar/{id}")
    @PreAuthorize("hasRole('ROLE_U_COMISION')")
    public ResponseEntity<?> modificar(
            @PathVariable("id") int id,
            @Valid @RequestBody JsonComision JSONC,
            BindingResult BR
    ) {

        // DECLARAMOS VARIABLES
        Comision comision;
        ComisionUsuario comisionUsuario;
        ComisionUsuarioPK comisionUsuarioPK;
        Usuario presidente;
        Usuario secretario;
        Usuario presidenteViejo;
        Usuario secretarioViejo;
        Rol rol;

        // INICIALIZAMOS VARIABLES
        comision = comisionService.findById(id);
        presidenteViejo = usuarioService.findByUsuario(comision.getPusuario());
        secretarioViejo = usuarioService.findByUsuario(comision.getSusuario());
        comisionUsuario = new ComisionUsuario();
        comisionUsuarioPK = new ComisionUsuarioPK();

        if (JSONC.getIdPresidente() != -1) {
            presidente = usuarioService.findById(JSONC.getIdPresidente());
        } else {
            presidente = usuarioService.findByUsuario(comision.getPusuario());
        }

        if (JSONC.getIdSecretario() != -1) {
            secretario = usuarioService.findById(JSONC.getIdSecretario());
        } else {
            secretario = usuarioService.findByUsuario(comision.getSusuario());
        }

        comision.setPusuario(presidente.getUsuario());
        comision.setSusuario(secretario.getUsuario());

        // GUARDAMOS
        comision = comisionService.save(comision);

        // RELACIONAMOS
        // PRESIDENTE
        if (JSONC.getIdPresidente() != -1) {

            comisionUsuarioPK.setIdc(comision.getId());
            comisionUsuarioPK.setIdu(presidenteViejo.getId());

            comisionUsuario = comisionUsuarioService.findByComisionUsuarioPK(comisionUsuarioPK);

            rol = comisionUsuario.getRol();

            comisionUsuarioService.delete(comisionUsuario);

            comisionUsuarioPK.setIdu(presidente.getId());
            comisionUsuario.setComisionUsuarioPK(comisionUsuarioPK);
            comisionUsuario.setRol(rol);

            comisionUsuarioService.save(comisionUsuario);
        }

        comisionUsuarioPK = new ComisionUsuarioPK();
        comisionUsuario = new ComisionUsuario();
        // RELACIONAMOS
        // SECRETARIO
        if (JSONC.getIdSecretario() != -1) {

            comisionUsuarioPK.setIdc(comision.getId());
            comisionUsuarioPK.setIdu(secretarioViejo.getId());

            comisionUsuario = comisionUsuarioService.findByComisionUsuarioPK(comisionUsuarioPK);

            rol = comisionUsuario.getRol();

            comisionUsuarioService.delete(comisionUsuario);

            comisionUsuarioPK.setIdu(secretario.getId());
            comisionUsuario.setComisionUsuarioPK(comisionUsuarioPK);
            comisionUsuario.setRol(rol);

            comisionUsuarioService.save(comisionUsuario);
        }

        return new ResponseEntity<>(
                new Mensaje("COMISION ACTUALIZADA"),
                HttpStatus.CREATED
        );
    }

    // DDD
    @DeleteMapping("/borrar/{ids}")
    @PreAuthorize("hasRole('ROLE_D_COMISION')")
    @ResponseBody
    public ResponseEntity<?> borrar(@PathVariable("ids") List<Integer> ids) {

        List<Comision> LC = new LinkedList<>();
        Comision c;

        // VERIFICAMOS QUE TODOS LOS ID EXISTAN
        for (int id : ids) {
            c = comisionService.findById(id);
            LC.add(c);
        }
        comisionService.deleteAll(LC);
        // BORRAMOS LOS ID
        return new ResponseEntity<>(
                new Mensaje(" OBJETOS BORRADOS: [ " + ids.size() + " ]"),
                HttpStatus.OK
        );
    }

}
