package com.pid.proyecto.seguridad.auxiliares;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

// esta clase se encarga de extraer informacion del token en sesion
@Component
public class SesionDetails {

    UserDetails userDetails;

    public String getUsuario() {

        userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return userDetails.getUsername();
    }

    public String getPassword() {

        userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return userDetails.getPassword();
    }

    public String getPrivilegios() {

        userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return userDetails.getAuthorities().toString();
    }
}
