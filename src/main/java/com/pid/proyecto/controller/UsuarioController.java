package com.pid.proyecto.controller;

import com.pid.proyecto.Json.JsonUsuario;
import com.pid.proyecto.Json.Login.JsonJwtDto;
import com.pid.proyecto.Json.Login.JsonLoginUsuario;
import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.auxiliares.Mensaje;
import com.pid.proyecto.auxiliares.SesionDetails;
import com.pid.proyecto.seguridad.jwt.JwtProvider;
import com.pid.proyecto.service.RolService;
import com.pid.proyecto.service.UsuarioService;
import java.util.LinkedList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@RequestMapping("/Usuario")
// PODEMOS ACCEDER DESDE CUALQUIER URL
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    SesionDetails sesionDetails;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    RolService rolService;

    @Autowired
    UsuarioService usuarioService;

    @PutMapping("/crear")
    @PreAuthorize("hasRole('ROLE_C_USUARIO')")
    public ResponseEntity<?> crear(
            @RequestBody JsonUsuario JSONU
    ) {

        // DECLARAMOS VARIABLES
        Usuario usuario;
        Rol rol;
        String nombre;
        String username;
        String password;
        String cargo;

        //LLENAMOS LAS VARIABLES A USAR
        usuario = new Usuario();
        rol = rolService.findById(1);
        nombre = JSONU.getNombre();
        username = JSONU.getUsuario();
        password = passwordEncoder.encode(JSONU.getContrasena());
        cargo = JSONU.getCargo();

        // LLENAMOS EL USUARIO UNA VEZ YA TENEMOS TODAS LAS VARIABLES LISTAS
        usuario.setRol(rol);
        usuario.setNombre(nombre);
        usuario.setUsuario(username);
        usuario.setContrasena(passwordEncoder.encode(password));
        usuario.setCargo(cargo);

        // SALVAMOS ESE USUARIO
        usuarioService.save(usuario);

        // RETORNAMOS CONFIRMACION
        return new ResponseEntity<>(
                new Mensaje("USUARIO GUARDADO"),
                HttpStatus.CREATED
        );
    }

    // RRR
    // MOSTRAMOS TODOS LOS USUARIOS
    @GetMapping
    @PreAuthorize("hasRole('ROLE_R_USUARIO')")
    public ResponseEntity<List<Usuario>> listar() {
        List<Usuario> list = usuarioService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    // UUU
//    @PostMapping("/actualizar/{id}")
//    @PreAuthorize("hasRole('ROLE_U_USUARIO')")
//    public ResponseEntity<?> actualizar(
//            @PathVariable("id") int id,
//            @RequestBody JsonUsuario JSONU
//    ) {
//        // DECLARAMOS VARIABLES
//        Usuario usuario;
//        Rol rol;
//        String nombre;
//        String username;
//        String password;
//        String cargo;
//
//        //LLENAMOS LAS VARIABLES A USAR
//        usuario = usuarioService.findById(id);
//        if (!JSONU.getRol().isBlank()) {
//            rol = rolService.findByRol(JSONU.getRol());
//        } else {
//            rol = usuario.getRol();
//        }
//        if (!JSONU.getNombre().isBlank()) {
//            nombre = JSONU.getNombre();
//        } else {
//            nombre = usuario.getNombre();
//        }
//        if (!JSONU.getUsuario().isBlank()) {
//            username = JSONU.getUsuario();
//        } else {
//            username = usuario.getUsuario();
//        }
//        if (!JSONU.getContrasena().isBlank()) {
//            password = passwordEncoder.encode(JSONU.getContrasena());
//        } else {
//            password = usuario.getContrasena();
//        }
//        if (!JSONU.getCargo().isBlank()) {
//            cargo = JSONU.getCargo();
//        } else {
//            cargo = usuario.getCargo();
//        }
//
//        // LLENAMOS EL USUARIO UNA VEZ YA TENEMOS TODAS LAS VARIABLES LISTAS
//        usuario.setRol(rol);
//        usuario.setNombre(nombre);
//        usuario.setUsuario(username);
//        usuario.setContrasena(passwordEncoder.encode(password));
//        usuario.setCargo(cargo);
//
//        // SALVAMOS ESE USUARIO
//        usuarioService.save(usuario);
//
//        // RETORNAMOS CONFIRMACION
//        return new ResponseEntity<>(
//                new Mensaje("USUARIO GUARDADO"),
//                HttpStatus.CREATED
//        );
//
//    }

    // DDD
    @DeleteMapping("/borrar/{ids}")
    @PreAuthorize("hasRole('ROLE_D_USUARIO')")
    @ResponseBody
    public ResponseEntity<?> borrar(@PathVariable("ids") List<Integer> ids) {
        List<Usuario> LU = new LinkedList<>();

        // VERIFICAMOS QUE TODOS LOS ID EXISTAN
        for (int id : ids) {
            LU.add(usuarioService.findById(id));
        }

        usuarioService.deleteAll(LU);
        return new ResponseEntity<>(
                new Mensaje(" USUARIOS BORRADOS: [ " + ids.size() + " ]"),
                HttpStatus.OK
        );
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<JsonJwtDto> login(
            @Valid @RequestBody JsonLoginUsuario loginUsuario
    ) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUsuario.getUsuario(),
                        loginUsuario.getContrasena()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // ESTE METODO DESENCADENA LA CREACION DEL TOKEN Y RESUELVE MEDIANTE EL NOMBRE DE USUARIO
        // EL RESTO DE SU INFORMACION
        String jwt = jwtProvider.generateToken(authentication);

        JsonJwtDto jwtDto = new JsonJwtDto(jwt); //CONSTRUIMOS EL TOKEN
        return new ResponseEntity<>(jwtDto, HttpStatus.OK);
    }

}
