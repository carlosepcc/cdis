package com.pid.proyecto.controller;

import com.pid.proyecto.seguridad.auxiliares.Mensaje;
import com.pid.proyecto.Json.JwtDto;
import com.pid.proyecto.Json.LoginUsuario;
import com.pid.proyecto.Json.NuevoUsuario;
import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.seguridad.auxiliares.SesionDetails;
import com.pid.proyecto.Json.ModificarUsuario;
import com.pid.proyecto.seguridad.auxiliares.UsuarioPrincipal;
import com.pid.proyecto.seguridad.enums.RolNombre;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
//revisamos MainSecurity.class en el configure vemos la direccion para crear nuevo usuario
@RequestMapping("/Usuario")
//podemos acceder desde cualquier url
@CrossOrigin("*")
public class usuarioController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    RolService rolSistemaService;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    SesionDetails sesionDetails;

    @Autowired
    UserDetailsService userDetailsService;

    // MOSTRAMOS TODOS LOS USUARIOS
    @GetMapping()
    public ResponseEntity<List<Usuario>> list() {
        List<Usuario> list = usuarioService.findAll();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    // CREAMOS UN NUEVO USUARIO
    @PutMapping("/crearUsuario")
    @PreAuthorize("hasRole('ROLE_C_USUARIO')")
    public ResponseEntity<?> crear(@Valid @RequestBody NuevoUsuario NU, BindingResult BR) {
        // DECLARAMOS VARIABLES Y LAS LLENAMOS CON NUESTRO JSON
        Usuario usuario = new Usuario();
        String nombre = NU.getNombre();
        String apellidos = NU.getApellidos();
        String username = NU.getUsuario();
        String password = NU.getContrasena();
        Rol rol;

        // VALIDAR ERRORES
        if (BR.hasErrors()) {
            List<String> errores = new LinkedList<>();
            for (FieldError FE : BR.getFieldErrors()) {
                errores.add(FE.getDefaultMessage());
            }
            return new ResponseEntity(new Mensaje(errores.toString()), HttpStatus.BAD_REQUEST);
        }

        // PREGUNTAMOS SI YA EXISTE ESE USUARIO EN NUESTRO SISTEMA
        if (usuarioService.existsByUsuario(NU.getUsuario())) {
            return new ResponseEntity(new Mensaje("ESE USUARIO YA EXISTE"), HttpStatus.BAD_REQUEST);
        }

        // CONFIRMAMOS QUE EXISTA EL ROL QUE ESTAMOS PASANDO EN EL JSON
        if (rolSistemaService.existsByRol(NU.getRol())) {
            // ASIGNAMOS EL ROL AL QUE SE HACE REFERENCIA
            rol = rolSistemaService.getByRol(NU.getRol()).get();
            // SI SE INTENTA CREAR OTRO ADMINISTRADOR
            if (rol.getRol().equals(RolNombre.ROLE_ADMIN_SISTEMA.name())) {
                // SI LA PERSONA AUTENTICADA NO POSEE EL ROL DE ADMINISTRADOR NO PODEMOS CREAR OTRO ADMINISTRADOR
                if (!sesionDetails.getPrivilegios().contains(RolNombre.ROLE_ADMIN_SISTEMA.name())) {
                    return new ResponseEntity(new Mensaje("USTED NO POSEE PRIVILEGIOS PARA CREAR UN USUARIO ADMINISTRADOR"), HttpStatus.BAD_REQUEST);
                }
            }
        } // SI NO EXISTE EL ROL LANZAMOS UN ERROR
        else {
            return new ResponseEntity(new Mensaje("EL ROL QUE INTENTA ASIGNAR NO EXISTE"), HttpStatus.BAD_REQUEST);
        }

        // CREAMOS EL USUARIO UNA VEZ YA TENEMOS TODAS LAS VARIABLES LISTAS
        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setUsuario(username);
        usuario.setContrasena(passwordEncoder.encode(password));
        usuario.setRol(rol);

        // SALVAMOS ESE USUARIO
        usuarioService.save(usuario);

        // RETORNAMOS CONFIRMACION
        return new ResponseEntity(new Mensaje("USUARIO GUARDADO"), HttpStatus.CREATED);
    }

    @PutMapping("/actualizarUsuario/{id}")
    @PreAuthorize("hasRole('ROLE_U_USUARIO')")
    public ResponseEntity<?> actualizar(@PathVariable("id") int id, @Valid @RequestBody ModificarUsuario MU, BindingResult BR) {

        // VALIDAR ERRORES
        if (BR.hasErrors()) {
            List<String> errores = new LinkedList<>();
            for (FieldError FE : BR.getFieldErrors()) {
                errores.add(FE.getDefaultMessage());
            }
            return new ResponseEntity(new Mensaje(errores.toString()), HttpStatus.BAD_REQUEST);
        }

        // DECLARAMOS VARIABLES Y LAS LLENAMOS CON NUESTRO JSON
        Usuario usuario;
        String nombre = MU.getNombre();
        String apellidos = MU.getApellidos();
        String username = MU.getUsuario();
        String password = MU.getContrasena();
        Rol rol = new Rol();

        if (!MU.getRol().isBlank()) {
            rol = rolSistemaService.getByRol(MU.getRol()).get();
        }

        // VERIFICAMOS QUE EXISTA EL ID DE USUARIO Y LLENAMOS NUESTRA VARIABLE usuario
        if (usuarioService.existsById(id)) {
            usuario = usuarioService.getByIdusuario(id).get();
        } else {
            return new ResponseEntity(new Mensaje("ESE ID DE USUARIO NO EXISTE"), HttpStatus.BAD_REQUEST);
        }

        // LLENAMOS NUESTRO rol SOLO SI SE PASO POR PARAMETRO SU INFORMACION
        if (MU.getRol().isEmpty() || MU.getRol().isBlank()) {
            // CONFIRMAMOS QUE EXISTA EL ROL QUE ESTAMOS PASANDO EN EL JSON
            if (rolSistemaService.existsByRol(MU.getRol())) {
                // ASIGNAMOS EL ROL AL QUE SE HACE REFERENCIA
                rol = rolSistemaService.getByRol(MU.getRol()).get();
                // SI SE INTENTA CREAR OTRO ADMINISTRADOR
                if (rol.getRol().equals(RolNombre.ROLE_ADMIN_SISTEMA.name())) {
                    // SI LA PERSONA AUTENTICADA NO POSEE EL ROL DE ADMINISTRADOR NO PODEMOS CREAR OTRO ADMINISTRADOR
                    if (!sesionDetails.getPrivilegios().contains(RolNombre.ROLE_ADMIN_SISTEMA.name())) {
                        return new ResponseEntity(new Mensaje("USTED NO POSEE PRIVILEGIOS PARA CREAR UN USUARIO ADMINISTRADOR"), HttpStatus.BAD_REQUEST);
                    }
                }
            } // SI NO EXISTE EL ROL LANZAMOS UN ERROR
            else {
                return new ResponseEntity(new Mensaje("EL ROL QUE INTENTA ASIGNAR NO EXISTE"), HttpStatus.BAD_REQUEST);
            }
        }

        // CREAMOS EL USUARIO UNA VEZ YA TENEMOS TODAS LAS VARIABLES LISTAS
        if (!nombre.isBlank()) {
            usuario.setNombre(nombre);
        }
        if (!apellidos.isBlank()) {
            usuario.setApellidos(apellidos);
        }
        if (!username.isBlank()) {
            usuario.setUsuario(username);
        }
        if (!password.isBlank()) {
            usuario.setContrasena(passwordEncoder.encode(password));
        }
        if (!MU.getRol().isBlank()) {
            usuario.setRol(rol);
        }

        // SALVAMOS ESE USUARIO
        usuarioService.save(usuario);

        // RETORNAMOS CONFIRMACION
        return new ResponseEntity(new Mensaje("USUARIO GUARDADO"), HttpStatus.CREATED);
    }

    @DeleteMapping("/{ids}")
    @PreAuthorize("hasRole('ROLE_D_USUARIO')")
    public ResponseEntity<?> borrar(@PathVariable("ids") List<Integer> ids) {

        List<Integer> ID = new LinkedList<>();

        // VERIFICAMOS QUE TODOS LOS ID EXISTAN
        for (int id : ids) {
            if (!usuarioService.existsById(ids.get(id))) {
                return new ResponseEntity(new Mensaje("NO EXISTE ALGUNO DE LOS ID ESPECIFICADOS"), HttpStatus.NOT_FOUND);
            }
            ID.add(id);
        }

        // SI TODOS EXISTEN LOS BORRAMOS DE LA BASE DE DATOS
        for (int id : ID) {
            usuarioService.delete(id);
        }
        return new ResponseEntity(new Mensaje(ids.size() + " USUARIOS BORRADOS"), HttpStatus.OK);
    }

    @PostMapping("/loginUsuario")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult BR) {

        // VALIDAR ERRORES
        if (BR.hasErrors()) {
            List<String> errores = new LinkedList<>();
            for (FieldError FE : BR.getFieldErrors()) {
                errores.add(FE.getDefaultMessage());
            }
            return new ResponseEntity(new Mensaje(errores.toString()), HttpStatus.BAD_REQUEST);
        }

        Authentication authentication
                = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getUsuario(), loginUsuario.getContrasena()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        
        // ESTE METODO DESENCADENA LA CREACION DEL TOKEN Y RESUELVE MEDIANTE EL NOMBRE DE USUARIO
        // EL RESTO DE SU INFORMACION
        String jwt = jwtProvider.generateToken(authentication);

        JwtDto jwtDto = new JwtDto(jwt); //CONSTRUIMOS EL TOKEN
        return new ResponseEntity(jwtDto, HttpStatus.OK);
    }
}
