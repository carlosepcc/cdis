package com.pid.proyecto;

import com.pid.proyecto.entity.Permiso;
import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.enums.PermisoNombre;
import com.pid.proyecto.enums.RolNombre;
import com.pid.proyecto.service.PermisoService;
import com.pid.proyecto.service.RolService;
import com.pid.proyecto.service.UsuarioService;
import java.util.Collection;
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

    @Override
    public void run(String... args) throws Exception {
        // VERIFICAMOS QUE NO HAYAN ROLES CREADOS NI PERMISOS NI USUARIOS
        // EN RESUMEN QUE LA BASE DE DATOS NO SE HAYA USADO NUNCA
        if (rolSistemaService.findAll().isEmpty()
                && permisosService.findAll().isEmpty()
                && usuarioService.findAll().isEmpty()) {

            Usuario usuario = new Usuario();

            // LISTA CON TODOS LOS PERMISOS QUE SE VAN A CREAR
            List<Permiso> TodosLosPermisos = new LinkedList<>();
            // LISTA CON TODOS LOS ROLES QUE SE VAN A CREAR
            List<Rol> TodosLosRoles = new LinkedList<>();

            TodosLosPermisos.addAll(ObtenerPermisos());
            TodosLosRoles.addAll(ObtenerRoles());

            // SALVAMOS TODOS LOS ROLES Y PERMISOS
            rolSistemaService.saveAll(TodosLosRoles);
            TodosLosPermisos = permisosService.saveAll(TodosLosPermisos);

            // AHORA ENLAZAMOS LOS PERMISOS CON LOS ROLES
            // ENLAZAR PERMISOS DE ADMIN CON EL ROL DE ADMIN
            Rol rolAdmin = RolPermisoAdmin(TodosLosPermisos);
            // ENLAZAR PERMISOS DE USUARIO CON EL ROL DE USUARIO
            RolPermisoUsuario();
            // ENLAZAR PERMISOS DE PRESIDENTE DE COMISION CON EL ROL DE PRESIDENTE DE COMISION
            RolPermisoPresidenteComision();
            // ENLAZAR PERMISOS DE SECRETARIO DE COMISION CON EL ROL DE SECRETARIO DE COMISION 
            RolPermisoSecretarioComision();

            //        List<Permisos> permisosDecano = new LinkedList();
            //        List<Permisos> permisosSecretarioDocente = new LinkedList();
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

    public List<Permiso> ObtenerPermisos() {

        // LISTA CON TODOS LOS PERMISOS QUE SE VAN A CREAR
        List<Permiso> lista = new LinkedList<>();
        
        lista.add(new Permiso(PermisoNombre.ROLE_C_ROL.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_U_ROL.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_D_ROL.name()));
        
        lista.add(new Permiso(PermisoNombre.ROLE_C_COMISION.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_U_COMISION.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_D_COMISION.name()));
        
        lista.add(new Permiso(PermisoNombre.ROLE_C_CASO.name()));
        
        lista.add(new Permiso(PermisoNombre.ROLE_R_ROL.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_D_CASO.name()));
        
        lista.add(new Permiso(PermisoNombre.ROLE_C_RESOLUCION.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_U_RESOLUCION.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_D_RESOLUCION.name())); // DE AQUI HACIA ARRIBA SON PERMISOS ESPECIALES
        
        
        lista.add(new Permiso(PermisoNombre.ROLE_C_USUARIO.name())); 
        lista.add(new Permiso(PermisoNombre.ROLE_R_USUARIO.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_U_USUARIO.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_D_USUARIO.name()));
        
        lista.add(new Permiso(PermisoNombre.ROLE_R_RESOLUCION.name()));
        

        lista.add(new Permiso(PermisoNombre.ROLE_C_EXPEDIENTE.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_R_EXPEDIENTE.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_U_EXPEDIENTE.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_D_EXPEDIENTE.name()));

        lista.add(new Permiso(PermisoNombre.ROLE_C_DICTAMEN.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_R_DICTAMEN.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_U_DICTAMEN.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_D_DICTAMEN.name()));

        lista.add(new Permiso(PermisoNombre.ROLE_C_DENUNCIA_USUARIO.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_R_DENUNCIA_USUARIO.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_U_DENUNCIA_USUARIO.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_D_DENUNCIA_USUARIO.name()));

        lista.add(new Permiso(PermisoNombre.ROLE_C_DENUNCIA.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_R_DENUNCIA.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_U_DENUNCIA.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_D_DENUNCIA.name()));

        lista.add(new Permiso(PermisoNombre.ROLE_C_DECLARACION.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_R_DECLARACION.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_U_DECLARACION.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_D_DECLARACION.name()));

        lista.add(new Permiso(PermisoNombre.ROLE_R_CASO.name()));
        lista.add(new Permiso(PermisoNombre.ROLE_U_CASO.name()));
        
        lista.add(new Permiso(PermisoNombre.ROLE_R_COMISION.name()));
        
        lista.add(new Permiso(PermisoNombre.ROLE_R_PERMISO.name()));
        

        return lista;
    }

    private Collection<? extends Rol> ObtenerRoles() {

        List<Rol> TodosLosRoles = new LinkedList<>();

        TodosLosRoles.add(new Rol(RolNombre.ROLE_USUARIO.name()));
//            TodosLosRoles.add(new Rol(RolNombre.ROLE_ACUSADO.name()));
        TodosLosRoles.add(new Rol(RolNombre.ROLE_ADMIN.name()));
        TodosLosRoles.add(new Rol(RolNombre.ROLE_DECANO.name()));
//            TodosLosRoles.add(new Rol(RolNombre.ROLE_DECLARANTE.name()));
//            TodosLosRoles.add(new Rol(RolNombre.ROLE_DENUNCIANTE.name()));
        TodosLosRoles.add(new Rol(RolNombre.ROLE_PRESIDENTE_C.name()));
        TodosLosRoles.add(new Rol(RolNombre.ROLE_SECRETARIO_C.name()));
//            TodosLosRoles.add(new Rol(RolNombre.ROLE_SECRETARIO_D.name()));

        return TodosLosRoles;
    }

    private void RolPermisoUsuario() {

        List<Permiso> permisosUser = new LinkedList<>();
        permisosUser.add(permisosService.findByPermiso(PermisoNombre.ROLE_R_USUARIO.name()));

        permisosUser.add(permisosService.findByPermiso(PermisoNombre.ROLE_C_DENUNCIA.name()));
        permisosUser.add(permisosService.findByPermiso(PermisoNombre.ROLE_R_DENUNCIA.name()));
        permisosUser.add(permisosService.findByPermiso(PermisoNombre.ROLE_U_DENUNCIA.name()));
        permisosUser.add(permisosService.findByPermiso(PermisoNombre.ROLE_D_DENUNCIA.name()));
        permisosUser.add(permisosService.findByPermiso(PermisoNombre.ROLE_U_DECLARACION.name()));

        Rol rolUser = rolSistemaService
                .findByRol(RolNombre.ROLE_USUARIO.name());

        rolUser.setPermisoList(permisosUser);
        rolSistemaService.save(rolUser);

    }

    private void RolPermisoPresidenteComision() {
        List<Permiso> permisosPresidenteComision = new LinkedList();

        permisosPresidenteComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_R_COMISION.name()));

        permisosPresidenteComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_R_CASO.name()));
        permisosPresidenteComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_U_CASO.name()));

        permisosPresidenteComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_C_DECLARACION.name()));
        permisosPresidenteComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_R_DECLARACION.name()));
        permisosPresidenteComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_U_DECLARACION.name()));
        permisosPresidenteComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_D_DECLARACION.name()));

        permisosPresidenteComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_C_DICTAMEN.name()));
        permisosPresidenteComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_R_DICTAMEN.name()));
        permisosPresidenteComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_U_DICTAMEN.name()));
        permisosPresidenteComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_D_DICTAMEN.name()));

        permisosPresidenteComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_C_EXPEDIENTE.name()));
        permisosPresidenteComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_R_EXPEDIENTE.name()));
        permisosPresidenteComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_U_EXPEDIENTE.name()));
        permisosPresidenteComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_D_EXPEDIENTE.name()));
        
        permisosPresidenteComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_R_PERMISO.name()));

        Rol rolPresidenteC = rolSistemaService.findByRol(RolNombre.ROLE_PRESIDENTE_C.name());

        rolPresidenteC.setPermisoList(permisosPresidenteComision);
        rolSistemaService.save(rolPresidenteC);
    }

    private void RolPermisoSecretarioComision() {
        List<Permiso> permisosSecretarioComision = new LinkedList();

        permisosSecretarioComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_R_COMISION.name()));

        permisosSecretarioComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_R_CASO.name()));
        permisosSecretarioComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_U_CASO.name()));

        permisosSecretarioComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_C_DECLARACION.name()));
        permisosSecretarioComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_R_DECLARACION.name()));
        permisosSecretarioComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_U_DECLARACION.name()));
        permisosSecretarioComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_D_DECLARACION.name()));

        permisosSecretarioComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_C_DICTAMEN.name()));
        permisosSecretarioComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_R_DICTAMEN.name()));
        permisosSecretarioComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_U_DICTAMEN.name()));
        permisosSecretarioComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_D_DICTAMEN.name()));

        permisosSecretarioComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_C_EXPEDIENTE.name()));
        permisosSecretarioComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_R_EXPEDIENTE.name()));
        permisosSecretarioComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_U_EXPEDIENTE.name()));
        permisosSecretarioComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_D_EXPEDIENTE.name()));
        
        permisosSecretarioComision.add(permisosService.findByPermiso(PermisoNombre.ROLE_R_PERMISO.name()));

        Rol rolSecretarioC = rolSistemaService.findByRol(RolNombre.ROLE_SECRETARIO_C.name());

        rolSecretarioC.setPermisoList(permisosSecretarioComision);
        rolSistemaService.save(rolSecretarioC);
    }

    private Rol RolPermisoAdmin(List<Permiso> TodosLosPermisos) {
        List<Permiso> permisosAdmin = TodosLosPermisos;
        Rol rolAdmin = rolSistemaService
                .findByRol(RolNombre.ROLE_ADMIN.name());

        rolAdmin.setPermisoList(permisosAdmin);
        return rolSistemaService.save(rolAdmin);
    }
}
