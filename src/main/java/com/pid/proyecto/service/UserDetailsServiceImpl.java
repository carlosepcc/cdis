package com.pid.proyecto.service;

import com.pid.proyecto.entity.Permiso;
import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.entity.RolPermiso;
import com.pid.proyecto.entity.Usuario;
import com.pid.proyecto.seguridad.auxiliares.UsuarioPrincipal;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// convierte un Usuario de la base de datos en un UsuarioPrincipal
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // inyectamos
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PermisoService permisosService;

    @Autowired
    RolService rolService;

    @Autowired
    RolPermisoService rolPermisoService;

    // cargamos usuarios por nombre de usuario
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioService.getByUsuario(username) // obtenemos un Optional por nombre de usuario (username)
                .get(); // obtenemos el usuario de ese optional

        Rol rol = rolService.getByRol(usuario.getRol().getRol()).get();

        // en esta clase las cosas creadas no tienen atributos que relacionen otras entidades
        // debo agregar un atributo a la clase q relaciona rolsistema con permisos para asi 
        // trabajar sobre ella con un service y obtener el id de cada relacion
        // con el id de cada relacion entre rolsistema y permisos puedo agregar al objeto usuario de 
        // aqui la lista de permisos que tanto me hace falta. :)
        List<Permiso> p = devolverPermisosDeRol(rol);

//        per.add(p);
        // construimos el UsuarioPrincipal
        return UsuarioPrincipal.build(usuario, p);
    }

    public List<Permiso> devolverPermisosDeRol(Rol rol) {
        List<RolPermiso> rolPermisosLista = rolPermisoService.Listar();
        List<Permiso> permisosLista = new LinkedList<>();
        for (RolPermiso RP : rolPermisosLista) {

            if (RP.getRol().equals(rol.getRol())) {
                permisosLista.add(permisosService.getByPermiso(RP.getPermiso()).get());
            }
        }

        return permisosLista;
    }

}
