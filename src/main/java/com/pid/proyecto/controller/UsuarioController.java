package com.pid.proyecto.controller;

import com.pid.proyecto.Json.CrearEntidad.NuevoUsuario;
import com.pid.proyecto.Json.Login.JwtDto;
import com.pid.proyecto.Json.Login.LoginUsuario;
import com.pid.proyecto.Json.ModificarEntidad.ModificarUsuario;
import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.enums.RolNombre;
import com.pid.proyecto.seguridad.auxiliares.Mensaje;
import com.pid.proyecto.seguridad.auxiliares.SesionDetails;
import com.pid.proyecto.seguridad.jwt.JwtProvider;
import com.pid.proyecto.service.RolService;
import com.pid.proyecto.service.UsuarioService;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Matcher;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
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
import org.springframework.web.bind.annotation.ResponseBody;
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

  // CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC
  // CREAMOS UN NUEVO USUARIO
  @PutMapping("/crearUsuario")
  @PreAuthorize("hasRole('ROLE_C_USUARIO')")
  public ResponseEntity<?> crear(
    @Valid @RequestBody NuevoUsuario NU,
    BindingResult BR
  ) {
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
      return new ResponseEntity<>(
        new Mensaje(errores.toString()),
        HttpStatus.BAD_REQUEST
      );
    }

    // PREGUNTAMOS SI YA EXISTE ESE USUARIO EN NUESTRO SISTEMA
    if (usuarioService.existsByUsuario(NU.getUsuario())) {
      return new ResponseEntity<>(
        new Mensaje("ESE USUARIO YA EXISTE"),
        HttpStatus.BAD_REQUEST
      );
    }

    // CONFIRMAMOS QUE EXISTA EL ROL QUE ESTAMOS PASANDO EN EL JSON
    if (rolSistemaService.existsByRol(NU.getRol())) {
      // ASIGNAMOS EL ROL AL QUE SE HACE REFERENCIA
      rol = rolSistemaService.getByRol(NU.getRol()).get();
      // SI SE INTENTA CREAR OTRO ADMINISTRADOR
      if (rol.getRol().equals(RolNombre.ROLE_ADMIN_SISTEMA.name())) {
        // SI LA PERSONA AUTENTICADA NO POSEE EL ROL DE ADMINISTRADOR NO PODEMOS CREAR OTRO ADMINISTRADOR
        if (
          !sesionDetails
            .getPrivilegios()
            .contains(RolNombre.ROLE_ADMIN_SISTEMA.name())
        ) {
          return new ResponseEntity<>(
            new Mensaje(
              "USTED NO POSEE PRIVILEGIOS PARA CREAR UN USUARIO ADMINISTRADOR"
            ),
            HttpStatus.BAD_REQUEST
          );
        }
      }
    } // SI NO EXISTE EL ROL LANZAMOS UN ERROR
    else {
      return new ResponseEntity<>(
        new Mensaje("EL ROL QUE INTENTA ASIGNAR NO EXISTE"),
        HttpStatus.BAD_REQUEST
      );
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
    return new ResponseEntity<>(
      new Mensaje("USUARIO GUARDADO"),
      HttpStatus.CREATED
    );
  }

  // RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR
  // MOSTRAMOS TODOS LOS USUARIOS
  @GetMapping
  @PreAuthorize("hasRole('ROLE_R_USUARIO')")
  public ResponseEntity<List<Usuario>> list() {
    List<Usuario> list = usuarioService.findAll();
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  // UUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU
  @PutMapping("/actualizarUsuario/{id}")
  @PreAuthorize("hasRole('ROLE_U_USUARIO')")
  public ResponseEntity<?> actualizar(
    @PathVariable("id") int id,
    @Valid @RequestBody ModificarUsuario MU,
    BindingResult BR
  ) {
    // VALIDAR ERRORES
    if (BR.hasErrors()) {
      List<String> errores = new LinkedList<>();
      for (FieldError FE : BR.getFieldErrors()) {
        errores.add(FE.getDefaultMessage());
      }
      return new ResponseEntity<>(
        new Mensaje(errores.toString()),
        HttpStatus.BAD_REQUEST
      );
    }

    // DECLARAMOS VARIABLES
    Usuario usuario;
    String nombre = MU.getNombre();
    String apellidos = MU.getApellidos();
    String username = MU.getUsuario();
    String password = MU.getContrasena();
    String Srol = MU.getRol();
    Rol rol = new Rol();

    // LLENAMOS LAS VARIABLES

    // usuario
    if (usuarioService.existsById(id)) {
      usuario = usuarioService.getByIdusuario(id).get();
    } else {
      return new ResponseEntity<>(
        new Mensaje("ESE ID DE USUARIO NO EXISTE"),
        HttpStatus.BAD_REQUEST
      );
    }
    // nombre lleno
    // apellidos lleno
    // username lleno
    // password lleno
    // Srol lleno

    // rol
    // LLENAMOS NUESTRO rol SOLO SI SE PASO POR PARAMETRO SU INFORMACION
    if (!Srol.isBlank()) {
      // CONFIRMAMOS QUE EXISTA EL ROL QUE ESTAMOS PASANDO EN EL JSON
      if (rolSistemaService.existsByRol(Srol)) {
        // ASIGNAMOS EL ROL AL QUE SE HACE REFERENCIA
        rol = rolSistemaService.getByRol(Srol).get();
      } // SI NO EXISTE EL ROL LANZAMOS UN ERROR
      else {
        return new ResponseEntity<>(
          new Mensaje("EL ROL QUE INTENTA ASIGNAR NO EXISTE"),
          HttpStatus.BAD_REQUEST
        );
      }
    }

    // ACTUALIZAMOS EL USUARIO UNA VEZ YA TENEMOS TODAS LAS VARIABLES LISTAS
    if (!nombre.isBlank()) {
      if (nombre.matches("[A-Z][a-z]*")) {
        return new ResponseEntity<>(
          new Mensaje("FORMATO DE NOMBRE INCORRECTO"),
          HttpStatus.BAD_REQUEST
        );
      }
      usuario.setNombre(nombre);
    }
    if (!apellidos.isBlank()) {
      if (apellidos.matches("[A-Z][a-z]*")) {
        return new ResponseEntity<>(
          new Mensaje("FORMATO DE APELLIDO INCORRECTO"),
          HttpStatus.BAD_REQUEST
        );
      }
      usuario.setApellidos(apellidos);
    }

    if (!username.isBlank()) {
      usuario.setUsuario(username);
    }
    if (!password.isBlank()) {
      usuario.setContrasena(passwordEncoder.encode(password));
    }
    if (!Srol.isBlank()) {
      usuario.setRol(rol);
    }

    // SALVAMOS ESE USUARIO
    usuarioService.save(usuario);

    // RETORNAMOS CONFIRMACION
    return new ResponseEntity<>(
      new Mensaje("USUARIO GUARDADO"),
      HttpStatus.CREATED
    );
  }

  // DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD
  @DeleteMapping("/borrarUsuarios/{ids}")
  @PreAuthorize("hasRole('ROLE_D_USUARIO')")
  @ResponseBody
  public ResponseEntity<?> borrar(@PathVariable("ids") List<Integer> ids) {
    List<Usuario> LU = new LinkedList<>();

    // VERIFICAMOS QUE TODOS LOS ID EXISTAN
    for (int id : ids) {
      if (!usuarioService.existsById(id)) {
        return new ResponseEntity<>(
          new Mensaje("NO EXISTE ALGUNO DE LOS ID ESPECIFICADOS"),
          HttpStatus.NOT_FOUND
        );
      }
      LU.add(usuarioService.getByIdusuario(id).get());
    }

    for (Usuario u : LU) {
      usuarioService.delete(u.getIdusuario());
    }
    //usuarioService.deleteAll(LU);
    return new ResponseEntity<>(
      new Mensaje(" USUARIOS BORRADOS: [ " + ids.size() + " ]"),
      HttpStatus.OK
    );
  }

  @PostMapping("/loginUsuario")
  public ResponseEntity<JwtDto> login(
    @Valid @RequestBody LoginUsuario loginUsuario
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

    JwtDto jwtDto = new JwtDto(jwt); //CONSTRUIMOS EL TOKEN
    return new ResponseEntity<>(jwtDto, HttpStatus.OK);
  }
}
