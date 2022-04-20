package com.pid.proyecto.controller;

import com.pid.proyecto.Json.CrearEntidad.JsonNuevoUsuario;
import com.pid.proyecto.Json.CrearEntidad.JsonRegistrarUsuario;
import com.pid.proyecto.Json.Login.JsonJwtDto;
import com.pid.proyecto.Json.Login.JsonLoginUsuario;
import com.pid.proyecto.Json.ModificarEntidad.JsonModificarUsuario;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//revisamos MainSecurity.class en el configure vemos la direccion para crear nuevo usuario
@RequestMapping("/Usuario")
//podemos acceder desde cualquier url
@CrossOrigin("*")
public class UsuarioController {
  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UsuarioService usuarioService;

  @Autowired
  RolService rolService;

  @Autowired
  JwtProvider jwtProvider;

  @Autowired
  SesionDetails sesionDetails;

  @Autowired
  UserDetailsService userDetailsService;

  // CCC
  // CREAMOS UN NUEVO USUARIO
  @PutMapping("/crearUsuario")
  @PreAuthorize("hasRole('ROLE_C_USUARIO')")
  public ResponseEntity<?> crear(
    @Valid @RequestBody JsonNuevoUsuario NU,
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
        HttpStatus.PRECONDITION_FAILED
      );
    }

    // DECLARAMOS VARIABLES
    Usuario usuario = new Usuario();
    String nombre = NU.getNombre();
    String apellidos = NU.getApellidos();
    String username = NU.getUsuario();
    String password = NU.getContrasena();
    String Srol = NU.getRol();
    Rol rol;

    //LLENAMOS LAS VARIABLES A USAR

    // usuario en espera
    // nombre lleno
    // apellidos lleno
    // username lleno
    // password lleno
    // Srol lleno

    // rol
    // CONFIRMAMOS QUE EXISTA EL ROL QUE ESTAMOS PASANDO EN EL JSON
    if (rolService.existsByRol(Srol)) {
      // ASIGNAMOS EL ROL AL QUE SE HACE REFERENCIA
      rol = rolService.getByRol(Srol).get();
    } // SI NO EXISTE EL ROL LANZAMOS UN ERROR
    else {
      return new ResponseEntity<>(
        new Mensaje("EL ROL QUE INTENTA ASIGNAR NO EXISTE"),
        HttpStatus.NOT_FOUND
      );
    }

    // PREGUNTAMOS SI YA EXISTE ESA ENTIDAD EN NUESTRO SISTEMA
    if (usuarioService.existsByUsuario(username)) {
      return new ResponseEntity<>(
        new Mensaje("ESE USUARIO YA EXISTE"),
        HttpStatus.CONFLICT
      );
    }

    // LLENAMOS EL USUARIO UNA VEZ YA TENEMOS TODAS LAS VARIABLES LISTAS
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

  // RRR
  // MOSTRAMOS TODOS LOS USUARIOS
  @GetMapping
  @PreAuthorize("hasRole('ROLE_R_USUARIO')")
  public ResponseEntity<List<Usuario>> listar() {
    List<Usuario> list = usuarioService.findAll();
    return new ResponseEntity<>(list, HttpStatus.OK);
  }

  // UUU
  @PostMapping("/actualizarUsuario/{id}")
  @PreAuthorize("hasRole('ROLE_U_USUARIO')")
  public ResponseEntity<?> actualizar(
    @PathVariable("id") int id,
    @Valid @RequestBody JsonModificarUsuario MU,
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
        HttpStatus.NOT_FOUND
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
      if (rolService.existsByRol(Srol)) {
        // ASIGNAMOS EL ROL AL QUE SE HACE REFERENCIA
        rol = rolService.getByRol(Srol).get();
      } // SI NO EXISTE EL ROL LANZAMOS UN ERROR
      else {
        return new ResponseEntity<>(
          new Mensaje("EL ROL QUE INTENTA ASIGNAR NO EXISTE"),
          HttpStatus.NOT_FOUND
        );
      }
    }

    // ACTUALIZAMOS CON LAS VARIABLES LLENAS Y VERIFICAMOS QUE NO COINCIDA CON OTRA ENTIDAD EL PARAMETRO DE USUARIO
    // nombre
    if (!nombre.isBlank()) {
      if (
        !nombre.matches(
          "([A-Z|Á|É|Í|Ó|Ú|Ñ][a-z|á|é|í|ó|ú|ñ|ű]*)|([A-Z|Á|É|Í|Ó|Ú|Ñ][a-z|á|é|í|ó|ú|ñ|ű]* [A-Z|Á|É|Í|Ó|Ú|Ñ][a-z|á|é|í|ó|ú|ñ|ű]*)"
        )
      ) {
        return new ResponseEntity<>(
          new Mensaje(
            "FORMATO DE NOMBRE INCORRECTO, DEBE COMENZAR CON MAYÚSCULA SEGUIDO DE MINÚSCULAS Y SOLO ADMITE DOS ENTRADAS"
          ),
          HttpStatus.PRECONDITION_FAILED
        );
      }
      usuario.setNombre(nombre);
    }
    // apellidos
    if (!apellidos.isBlank()) {
      if (
        !apellidos.matches(
          "([A-Z|Á|É|Í|Ó|Ú|Ñ][a-z|á|é|í|ó|ú|ñ|ű]*)|([A-Z|Á|É|Í|Ó|Ú|Ñ][a-z|á|é|í|ó|ú|ñ|ű]* [A-Z|Á|É|Í|Ó|Ú|Ñ][a-z|á|é|í|ó|ú|ñ|ű]*)"
        )
      ) {
        return new ResponseEntity<>(
          new Mensaje(
            "FORMATO DE APELLIDO INCORRECTO, DEBE COMENZAR CON MAYÚSCULA SEGUIDO DE MINÚSCULAS Y SOLO ADMITE DOS ENTRADAS"
          ),
          HttpStatus.PRECONDITION_FAILED
        );
      }
      usuario.setApellidos(apellidos);
    }
    // usuario
    if (!username.isBlank()) {
      if (!username.matches("[a-z]*")) {
        return new ResponseEntity<>(
          new Mensaje("FORMATO DE USUARIO INCORRECTO, SOLO LETRAS MINÚSCULAS"),
          HttpStatus.PRECONDITION_FAILED
        );
      }
      if (
        (usuario.getUsuario() != username) &&
        !usuarioService.existsByUsuario(username)
      ) {
        usuario.setUsuario(username);
      } else {
        return new ResponseEntity<>(
          new Mensaje(
            "YA EXISTE ALGUIEN MÁS CON ESE USUARIO, ASIGNE OTRO DISTINTO"
          ),
          HttpStatus.PRECONDITION_FAILED
        );
      }
    }
    // contraseña
    if (!password.isBlank()) {
      if (!(password.length() <= 8)) {
        return new ResponseEntity<>(
          new Mensaje("LA CONTRASEÑA DEBE CONTENER DE 4 - 10 CARACTERES"),
          HttpStatus.PRECONDITION_FAILED
        );
      }
      usuario.setContrasena(passwordEncoder.encode(password));
    }

    // rol
    if (!Srol.isBlank()) {
      usuario.setRol(rol);
    }

    // SALVAMOS ESE USUARIO
    if (
      !nombre.isBlank() ||
      !apellidos.isBlank() ||
      !username.isBlank() ||
      !password.isBlank() ||
      !Srol.isBlank()
    ) {
      usuarioService.save(usuario);

      // RETORNAMOS CONFIRMACION
      return new ResponseEntity<>(
        new Mensaje("USUARIO GUARDADO"),
        HttpStatus.OK
      );
    } else return new ResponseEntity<>(
      new Mensaje("NO SE EFECTUARON CAMBIOS EN ESTE USUARIO"),
      HttpStatus.NOT_MODIFIED
    );
  }

  // UUUP
  @PostMapping("/actualizarUsuarioPropio")
  @PreAuthorize("hasRole('ROLE_UP_USUARIO')")
  public ResponseEntity<?> actualizarPropio(
    @Valid @RequestBody JsonModificarUsuario MU,
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
    int id = usuarioService
      .getByUsuario(sesionDetails.getUsuario())
      .get()
      .getIdusuario();
    Usuario usuario;
    String password = MU.getContrasena();

    // LLENAMOS LAS VARIABLES

    // usuario
    usuario = usuarioService.getByIdusuario(id).get();
    // password lleno

    // ACTUALIZAMOS CON LAS VARIABLES LLENAS
    // contraseña
    if (!password.isBlank()) {
      if (!(password.length() <= 8)) {
        return new ResponseEntity<>(
          new Mensaje("LA CONTRASEÑA DEBE CONTENER DE 4 - 10 CARACTERES"),
          HttpStatus.PRECONDITION_FAILED
        );
      }
      usuario.setContrasena(passwordEncoder.encode(password));
    }

    // SALVAMOS ESE USUARIO
    if (!password.isBlank()) {
      usuarioService.save(usuario);

      // RETORNAMOS CONFIRMACION
      return new ResponseEntity<>(
        new Mensaje("USUARIO GUARDADO"),
        HttpStatus.OK
      );
    } else return new ResponseEntity<>(
      new Mensaje("NO SE EFECTUARON CAMBIOS EN ESTE USUARIO"),
      HttpStatus.NOT_MODIFIED
    );
  }

  // DDD
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


    usuarioService.deleteAll(LU);
    return new ResponseEntity<>(
      new Mensaje(" USUARIOS BORRADOS: [ " + ids.size() + " ]"),
      HttpStatus.OK
    );
  }

  // LOGIN
  @PostMapping("/loginUsuario")
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

  // REGISTRARSE
  @PutMapping("/RegistrarUsuario")
  public ResponseEntity<?> Registrarse(
    @Valid @RequestBody JsonRegistrarUsuario RU,
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
    Usuario usuario = new Usuario();
    String nombre = RU.getNombre();
    String apellidos = RU.getApellidos();
    String username = RU.getUsuario();
    String password = RU.getContrasena();
    Rol rol = rolService.getByRol(RolNombre.ROLE_USUARIO_SISTEMA.name()).get();

    // LLENAMOS LAS VARIABLES

    // usuario en espera
    // nombre lleno
    // apellidos lleno
    // username lleno
    // password lleno
    // rol lleno

    // ACTUALIZAMOS CON LAS VARIABLES LLENAS
    // nombre
    usuario.setNombre(nombre);
    // apellidos
    usuario.setApellidos(apellidos);
    // usuario
    if (usuarioService.existsByUsuario(username)) {
      return new ResponseEntity<>(
        new Mensaje("ESE USUARIO YA ESTA REGISTRADO"),
        HttpStatus.OK
      );
    }
    usuario.setUsuario(username);
    // contraseña
    usuario.setContrasena(passwordEncoder.encode(password));
    // rol
    usuario.setRol(rol);

    // SALVAMOS ESE USUARIO

    usuarioService.save(usuario);

    // RETORNAMOS CONFIRMACION
    return new ResponseEntity<>(new Mensaje("USUARIO GUARDADO"), HttpStatus.OK);
  }
}
