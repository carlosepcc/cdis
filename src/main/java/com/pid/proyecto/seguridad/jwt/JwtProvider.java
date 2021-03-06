package com.pid.proyecto.seguridad.jwt;

import com.pid.proyecto.Json.JsonUsuario;
import com.pid.proyecto.auxiliares.UsuarioPrincipal;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

// Esta clase va a generar el token y sus metodos de validacion
@Component
public class JwtProvider {

    //para verificar errores mientras se programa
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    //valores de aplication properties
    @Value("${jwt.secret}")
    private String secret;

    //valores de aplication properties
    @Value("${jwt.expiration}")
    private int expiration;

    // crear el token
    public String generateToken(Authentication authentication) {

        // getPrincipal() castea a la clase UserDetailsServiceImpl usando su metodo loadUserByUsername()
        // lo cual desencadena toda nuestra logica de permisos
        //guardamos el usuario autenticado como uno de tipo UsuarioPrincipal
        UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();

        //convertimos las autoridades en cadenas String 
        List<String> roles = usuarioPrincipal.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        List<String> permisos = new LinkedList<>();

        for (int i = 0; i < roles.size(); i++) {
            if (i != 0) {
                permisos.add(roles.get(i));
            }
        }

        JsonUsuario usuario = new JsonUsuario();
        usuario.setNombre(usuarioPrincipal.getNombre());
        usuario.setCargo(usuarioPrincipal.getCargo());
        usuario.setUsuario(usuarioPrincipal.getUsername());
        usuario.setRol(roles.get(0));
        usuario.setPermisos(permisos);

        //construir el token
        return Jwts.builder() // estamos construyendo el token con lo siguiente
                .setSubject(usuarioPrincipal.getUsername()) // le pasamos el nombre de usuario
                .claim("user", usuario)
                //.claim("roles", roles)
                .setIssuedAt(new Date()) // le pasamos la fecha de creacion
                .setExpiration(new Date(new Date().getTime() + expiration * 1000)) // le decimos el tiempo de expiracion
                .signWith(SignatureAlgorithm.HS512, secret.getBytes()) // firmamos el token con el secret
                .compact(); // generamos el token
    }

    // obtener el nombre de usuario en base a un token
    public String getNombreUsuarioFromToken(String token) {
        return (String) Jwts.parser()
                .setSigningKey(secret.getBytes()) // firmamos con secret
                .parseClaimsJws(token) // obtenemos el token
                .getBody() // el cuerpo del token
                .getSubject(); // el usuario del cuerpo del token
    }

    // validar que el token sea correcto
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secret.getBytes()) // le pasamos la firma
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("TOKEN MAL FORMADO");
        } catch (UnsupportedJwtException e) {
            logger.error("TOKEN NO SOPORTADO");
        } catch (ExpiredJwtException e) {
            logger.error("TOKEN EXPIRADO");
        } catch (IllegalArgumentException e) {
            logger.error("TOKEN VACIO");
        } catch (SignatureException e) {
            logger.error("FALLOS EN LA FIRMA");
        }
        return false;
    }

}
