package com.pid.proyecto.seguridad.auxiliares;

import com.pid.proyecto.entity.Permiso;
import com.pid.proyecto.entity.Rol;
import com.pid.proyecto.entity.Usuario;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

//clase encargada de la seguridad
public class UsuarioPrincipal implements UserDetails {


    private final String nombre;
    private final String apellidos;
    private final String usuario;
    private final String contrasena;
    private final Collection<? extends GrantedAuthority> authoritys;

    public UsuarioPrincipal(String nombre, String apellidos, String usuario, String contrasena, Collection<? extends GrantedAuthority> authoritys) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.authoritys = authoritys;
    }

    // convertimos un objeto de la entidad Usuario en UsuarioPrincipal para obtener sus privilegios (roles)
    // obtenemos los roles y los guardamos en una lista
    public static UsuarioPrincipal build(Usuario usuario, List<Permiso> LP) {
        List<GrantedAuthority> authoritys = new LinkedList<>();
        // SIEMPRE LO PRIMERO QUE VA A CONTENER EL LISTADO DE AUTORIDADES ES EL ROL

        List<Rol> LR = new LinkedList<>();
        LR.add(usuario.getRol());


        

        authoritys.addAll(LR
                .stream()
                .map(permiso -> new SimpleGrantedAuthority(permiso.getRol()))
                .collect(Collectors.toList()));
        
        authoritys.addAll(LP
                .stream()
                .map(permiso -> new SimpleGrantedAuthority(permiso.getPermiso()))
                .collect(Collectors.toList()));
        

//        // AHORA AÑADIMOS A LA LISTA DE AUTORIDADES LOS PERMISOS QUE SI NECESITAMOS PARA TRABAJAR
//        authoritys.addAll(usuario.getRolSistema().getPermisosList() // Obtenemos el rol y luego los permisos de ese rol
//                .stream() //recogemos cada objeto de la lista de Rolsistema obtenida antes
//                .map(permisos -> new SimpleGrantedAuthority(permisos.getPermiso().name())) //convertimos esos permisos obtenidos en authoritys a partir del nombre del permiso
//                // y estos son los que usamos en las clases controladoras en el preautorize
//                .collect(Collectors.toList())); //recolectamos lo que convertimos
        //devolvemos ese UsuarioPrincipal creado con su lista de roles
        return new UsuarioPrincipal(usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getUsuario(),
                usuario.getContrasena(),
                authoritys);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authoritys;
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return usuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

}
