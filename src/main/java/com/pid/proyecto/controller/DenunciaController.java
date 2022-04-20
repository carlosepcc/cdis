package com.pid.proyecto.controller;

import com.pid.proyecto.entity.Denuncia;
import com.pid.proyecto.entity.Permiso;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.seguridad.auxiliares.ConvertidorToListEntity;
import com.pid.proyecto.seguridad.auxiliares.Filtrador;
import com.pid.proyecto.seguridad.auxiliares.Mensaje;
import com.pid.proyecto.seguridad.auxiliares.SesionDetails;
import com.pid.proyecto.Json.CrearEntidad.JsonNuevaDenuncia;
import com.pid.proyecto.service.DenunciaService;
import com.pid.proyecto.service.UserDetailsServiceImpl;
import com.pid.proyecto.service.UsuarioService;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Denuncia")
//podemos acceder desde cualquier url
@CrossOrigin("*")
public class denunciaController {

    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    DenunciaService denunciaService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ConvertidorToListEntity convertidor;

    @Autowired
    SesionDetails sesionDetails;

    @Autowired
    Filtrador filtrador;

    @GetMapping()
    public ResponseEntity<List<Denuncia>> list() {
        List<Denuncia> list = denunciaService.Listar();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @PutMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')"
            + " or hasRole('ROLE_TRABAJADOR')"
            + " or hasRole('ROLE_DECANO')"
            + " or hasRole('ROLE_PROFESOR')")
    public ResponseEntity<?> crear(@Valid @RequestBody JsonNuevaDenuncia nuevaDenuncia, BindingResult bindingResult) {

        // si tiene errores en el binding result
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(new Mensaje("CAMPOS MAL PUESTOS"), HttpStatus.BAD_REQUEST);
        }

        // obtenemos el denunciante que se encuentra ahora en sesion
        String denunciante = sesionDetails.getUsuario();

        // verificamos que los valores introducidos en el json para 
        // los estudiantes existan en nuestra base de datos
        if (convertidor.convertirListaStringToListaUsuario(nuevaDenuncia.getEstudiantes()) == null) {
            return new ResponseEntity<>(new Mensaje("REGISTRE ANTES A TODOS LOS ESTUDIANTES"), HttpStatus.BAD_REQUEST);
        }

        // preparamos una lista de roles para verificar la autoridad en el sistema del denunciante
        List<Permiso> rolDenunciante;
        rolDenunciante = new LinkedList<>();
        rolDenunciante.addAll(userDetailsServiceImpl.devolverPermisosDeRol(usuarioService.getByUsuario(denunciante).get().getRol()));

        // creamos la denuncia parcialmente
        Denuncia denuncia = new Denuncia();
        denuncia.setDescripcionden(nuevaDenuncia.getDescripcion());
        denuncia.setFecha(new Date());

        // preparamos la lista de relaciones de nuestra denuncia con los usuarios
        List<Usuario> LU = new LinkedList<>();

        // añadimos el objeto usuario de nuestro denunciante a la relacion con esta denuncia 
        LU.add(usuarioService.getByUsuario(denunciante).get());

        // añadimos la lista de estudiantes a nuestra variable que ya tiene el denunciante
        LU.addAll(convertidor.convertirListaStringToListaUsuario(nuevaDenuncia.getEstudiantes()));

        if (!LU.isEmpty()) {
            return new ResponseEntity(new Mensaje(sesionDetails.getUsuario()), HttpStatus.CONFLICT);
        }

        // salvamos la denuncia
        denunciaService.save(denuncia);
        return new ResponseEntity(new Mensaje("DENUNCIA GUARDADA"), HttpStatus.CREATED);
    }

}
