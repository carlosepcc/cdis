package com.pid.proyecto;

import com.pid.proyecto.entity.Permiso;
import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.entity.RolPermiso;
import com.pid.proyecto.entity.RolPermisoPK;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.enums.PermisoNombre;
import com.pid.proyecto.enums.RolNombre;
import com.pid.proyecto.service.PermisoService;
import com.pid.proyecto.service.RolPermisoService;
import com.pid.proyecto.service.RolService;
import com.pid.proyecto.service.UsuarioService;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// ESTA CLASE CREA LOS ROLES PREDETERMINADOS DEL SISTEMA Y ASOCIA LOS PERMISOS QUE DEBEN TENER
// LA PRIMERA VEZ QUE SE EJECUTA EL PROGRAMA
@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RolService rolSistemaService;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PermisoService permisosService;

    @Autowired
    RolPermisoService rolPermisosService;

    @Override
    public void run(String... args) throws Exception {
        // VERIFICAMOS QUE NO HAYAN ROLES CREADOS NI PERMISOS NI USUARIOS
        // EN RESUMEN QUE LA BASE DE DATOS NO SE HAYA USADO NUNCA
        if (rolSistemaService.findAll().isEmpty()
                && permisosService.findAll().isEmpty()
                && usuarioService.findAll().isEmpty()) {
            Usuario usuario = new Usuario();
            List<Character> Lc = new LinkedList<>();
            Lc.add('c');
            Lc.add('r');
            Lc.add('u');
            Lc.add('d');

            // LISTA CON TODOS LOS PERMISOS QUE SE VAN A CREAR
            List<Permiso> TodosLosPermisos = new LinkedList<>();
            // LISTA CON TODOS LOS ROLES QUE SE VAN A CREAR
            List<Rol> TodosLosRoles = new LinkedList<>();

            TodosLosPermisos.addAll(PermisosCaso(Lc));
            TodosLosPermisos.addAll(PermisosDeclaracion(Lc));
            TodosLosPermisos.addAll(PermisosDenuncia(Lc));
            TodosLosPermisos.addAll(PermisosDenunciaUsuario(Lc));
            TodosLosPermisos.addAll(PermisosPdfDictamen(Lc));
            TodosLosPermisos.addAll(PermisosPdfExpediente(Lc));
            TodosLosPermisos.addAll(PermisosPdfResolucionComisiones(Lc));
            TodosLosPermisos.addAll(PermisosRolSistema(Lc));
            TodosLosPermisos.addAll(PermisosUsuario(Lc));
            TodosLosPermisos.addAll(PermisosComision(Lc));

            TodosLosRoles.add(new Rol(RolNombre.ROLE_USUARIO.name()));
            TodosLosRoles.add(new Rol(RolNombre.ROLE_ACUSADO.name()));
            TodosLosRoles.add(new Rol(RolNombre.ROLE_ADMIN.name()));
            TodosLosRoles.add(new Rol(RolNombre.ROLE_DECANO.name()));
            TodosLosRoles.add(new Rol(RolNombre.ROLE_DECLARANTE.name()));
            TodosLosRoles.add(new Rol(RolNombre.ROLE_DENUNCIANTE.name()));
            TodosLosRoles.add(new Rol(RolNombre.ROLE_PRESIDENTE_C.name()));
            TodosLosRoles.add(new Rol(RolNombre.ROLE_SECRETARIO_C.name()));
            TodosLosRoles.add(
                    new Rol(RolNombre.ROLE_SECRETARIO_D.name())
            );

            // SALVAMOS TODOS LOS ROLES Y PERMISOS
            permisosService.saveAll(TodosLosPermisos);
            rolSistemaService.saveAll(TodosLosRoles);

            // AHORA ENLAZAMOS LOS PERMISOS CON LOS ROLES
            // ENLAZAR PERMISOS DE ADMIN CON EL ROL DE ADMIN
            List<Permiso> permisosAdmin = TodosLosPermisos;
            Rol rolAdmin = rolSistemaService
                    .findByRol(RolNombre.ROLE_ADMIN.name());
            GuardarRelaciones(rolAdmin, permisosAdmin);
            rolSistemaService.save(rolAdmin);

            // ENLAZAR PERMISOS DE USUARIO CON EL ROL DE USUARIO
            List<Permiso> permisosUser = new LinkedList<>();
            permisosUser.add(
                    permisosService.findByPermiso(PermisoNombre.ROLE_R_USUARIO.name())
            );
            Rol rolUser = rolSistemaService
                    .findByRol(RolNombre.ROLE_USUARIO.name());
            GuardarRelaciones(rolUser, permisosUser);
            rolSistemaService.save(rolUser);

            //        List<Permisos> permisosSecretarioDocente = new LinkedList();
            //        List<Permisos> permisosDecano = new LinkedList();
            //        List<Permisos> permisosPresidenteComision = new LinkedList();
            //        List<Permisos> permisosSecretarioComision = new LinkedList();
            //        List<Permisos> permisosAcusadoComision = new LinkedList();
            //        List<Permisos> permisosDeclarante = new LinkedList();
            //        List<Permisos> permisosDenunciante = new LinkedList();
            // CREAMOS UN USUARIO ADMINISTRADOR POR DEFECTO EN NUESTRA BASE DE DATOS
            usuario.setNombre("admin");
            usuario.setCargo("ADMIN");
            usuario.setUsuario("admin");
            usuario.setContrasena(passwordEncoder.encode("admin"));
            usuario.setRol(rolAdmin);
            usuarioService.save(usuario);

        }
    }

    public List<Integer> GuardarRelaciones(Rol rol, List<Permiso> permisos) {
        String NR = rol.getRol();
        RolPermiso RP;
        RolPermisoPK PK;
        List<RolPermiso> LRP = new LinkedList<>();
        List<Integer> ListaExistentes = new LinkedList<>();

        for (Permiso p : permisos) {
            PK = new RolPermisoPK(rol.getId(), p.getId());
            RP = new RolPermiso(PK, NR, p.getPermiso());

            if (!rolPermisosService.existByRolPermisoPK(PK)) {
                LRP.add(RP);
            } else {
                ListaExistentes.add(p.getId());
            }
        }
        rolPermisosService.saveAll(LRP);
        return ListaExistentes;
    }

    public int EliminarRelaciones(Rol rol, List<Permiso> permisos) {
        RolPermisoPK rolPermisosPK;
        List<RolPermiso> LRP = new LinkedList<>();
        RolPermiso RP;
        int respuesta = 0;

        // VERIFICAR QUE TODOS LOS PERMISOS EXISTAN DENTRO DE NUESTRO OBJETO ROL
        for (Permiso p : permisos) {
            rolPermisosPK = new RolPermisoPK(rol.getId(), p.getId());
            if (rolPermisosService.existByRolPermisoPK(rolPermisosPK)) {
                RP = rolPermisosService.findByRolPermisoPK(rolPermisosPK);
                LRP.add(RP);
            } else {
                respuesta = rolPermisosPK.getIdp();
                return respuesta;
            }
        }
        rolPermisosService.deleteAll(LRP);
        return respuesta;
    }

    public List<Permiso> PermisosUsuario(List<Character> Lc) {
        List<Permiso> lista = new LinkedList<>();
        for (char c : Lc) {
            if (c == 'c') {
                lista.add(new Permiso(PermisoNombre.ROLE_C_USUARIO.name()));
            }
            if (c == 'r') {
                lista.add(new Permiso(PermisoNombre.ROLE_R_USUARIO.name()));
            }
            if (c == 'u') {
                lista.add(new Permiso(PermisoNombre.ROLE_U_USUARIO.name()));
            }
            if (c == 'd') {
                lista.add(new Permiso(PermisoNombre.ROLE_D_USUARIO.name()));
            }
        }

        return lista;
    }

    public List<Permiso> PermisosRolSistema(List<Character> Lc) {
        List<Permiso> lista = new LinkedList<>();
        for (char c : Lc) {
            if (c == 'c') {
                lista.add(new Permiso(PermisoNombre.ROLE_C_ROL.name()));
            }
            if (c == 'r') {
                lista.add(new Permiso(PermisoNombre.ROLE_R_ROL.name()));
            }
            if (c == 'u') {
                lista.add(new Permiso(PermisoNombre.ROLE_U_ROL.name()));
            }
            if (c == 'd') {
                lista.add(new Permiso(PermisoNombre.ROLE_D_ROL.name()));
            }
        }

        return lista;
    }

    public List<Permiso> PermisosPdfResolucionComisiones(List<Character> Lc) {
        List<Permiso> lista = new LinkedList<>();
        for (char c : Lc) {
            if (c == 'c') {
                lista.add(
                        new Permiso(PermisoNombre.ROLE_C_RESOLUCION.name())
                );
            }
            if (c == 'r') {
                lista.add(
                        new Permiso(PermisoNombre.ROLE_R_RESOLUCION.name())
                );
            }
            if (c == 'u') {
                lista.add(
                        new Permiso(PermisoNombre.ROLE_U_RESOLUCION.name())
                );
            }
            if (c == 'd') {
                lista.add(
                        new Permiso(PermisoNombre.ROLE_D_RESOLUCION.name())
                );
            }
        }

        return lista;
    }

    public List<Permiso> PermisosPdfExpediente(List<Character> Lc) {
        List<Permiso> lista = new LinkedList<>();
        for (char c : Lc) {
            if (c == 'c') {
                lista.add(new Permiso(PermisoNombre.ROLE_C_EXPEDIENTE.name()));
            }
            if (c == 'r') {
                lista.add(new Permiso(PermisoNombre.ROLE_R_EXPEDIENTE.name()));
            }
            if (c == 'u') {
                lista.add(new Permiso(PermisoNombre.ROLE_U_EXPEDIENTE.name()));
            }
            if (c == 'd') {
                lista.add(new Permiso(PermisoNombre.ROLE_D_EXPEDIENTE.name()));
            }
        }

        return lista;
    }

    public List<Permiso> PermisosPdfDictamen(List<Character> Lc) {
        List<Permiso> lista = new LinkedList<>();
        for (char c : Lc) {
            if (c == 'c') {
                lista.add(new Permiso(PermisoNombre.ROLE_C_DICTAMEN.name()));
            }
            if (c == 'r') {
                lista.add(new Permiso(PermisoNombre.ROLE_R_DICTAMEN.name()));
            }
            if (c == 'u') {
                lista.add(new Permiso(PermisoNombre.ROLE_U_DICTAMEN.name()));
            }
            if (c == 'd') {
                lista.add(new Permiso(PermisoNombre.ROLE_D_DICTAMEN.name()));
            }
        }

        return lista;
    }

    public List<Permiso> PermisosDenunciaUsuario(List<Character> Lc) {
        List<Permiso> lista = new LinkedList<>();
        for (char c : Lc) {
            if (c == 'c') {
                lista.add(new Permiso(PermisoNombre.ROLE_C_DENUNCIA_USUARIO.name()));
            }
            if (c == 'r') {
                lista.add(new Permiso(PermisoNombre.ROLE_R_DENUNCIA_USUARIO.name()));
            }
            if (c == 'u') {
                lista.add(new Permiso(PermisoNombre.ROLE_U_DENUNCIA_USUARIO.name()));
            }
            if (c == 'd') {
                lista.add(new Permiso(PermisoNombre.ROLE_D_DENUNCIA_USUARIO.name()));
            }
        }

        return lista;
    }

    public List<Permiso> PermisosDenuncia(List<Character> Lc) {
        List<Permiso> lista = new LinkedList<>();
        for (char c : Lc) {
            if (c == 'c') {
                lista.add(new Permiso(PermisoNombre.ROLE_C_DENUNCIA.name()));
            }
            if (c == 'r') {
                lista.add(new Permiso(PermisoNombre.ROLE_R_DENUNCIA.name()));
            }
            if (c == 'u') {
                lista.add(new Permiso(PermisoNombre.ROLE_U_DENUNCIA.name()));
            }
            if (c == 'd') {
                lista.add(new Permiso(PermisoNombre.ROLE_D_DENUNCIA.name()));
            }
        }

        return lista;
    }

    public List<Permiso> PermisosDeclaracion(List<Character> Lc) {
        List<Permiso> lista = new LinkedList<>();
        for (char c : Lc) {
            if (c == 'c') {
                lista.add(new Permiso(PermisoNombre.ROLE_C_DECLARACION.name()));
            }
            if (c == 'r') {
                lista.add(new Permiso(PermisoNombre.ROLE_R_DECLARACION.name()));
            }
            if (c == 'u') {
                lista.add(new Permiso(PermisoNombre.ROLE_U_DECLARACION.name()));
            }
            if (c == 'd') {
                lista.add(new Permiso(PermisoNombre.ROLE_D_DECLARACION.name()));
            }
        }

        return lista;
    }

    public List<Permiso> PermisosCaso(List<Character> Lc) {
        List<Permiso> lista = new LinkedList<>();
        for (char c : Lc) {
            if (c == 'c') {
                lista.add(new Permiso(PermisoNombre.ROLE_C_CASO.name()));
            }
            if (c == 'r') {
                lista.add(new Permiso(PermisoNombre.ROLE_R_CASO.name()));
            }
            if (c == 'u') {
                lista.add(new Permiso(PermisoNombre.ROLE_U_CASO.name()));
            }
            if (c == 'd') {
                lista.add(new Permiso(PermisoNombre.ROLE_D_CASO.name()));
            }
        }

        return lista;
    }

    public List<Permiso> PermisosComision(List<Character> Lc) {
        List<Permiso> lista = new LinkedList<>();
        for (char c : Lc) {
            if (c == 'c') {
                lista.add(new Permiso(PermisoNombre.ROLE_C_COMISION.name()));
            }
            if (c == 'r') {
                lista.add(new Permiso(PermisoNombre.ROLE_R_COMISION.name()));
            }
            if (c == 'u') {
                lista.add(new Permiso(PermisoNombre.ROLE_U_COMISION.name()));
            }
            if (c == 'd') {
                lista.add(new Permiso(PermisoNombre.ROLE_D_COMISION.name()));
            }
        }

        return lista;
    }
}
