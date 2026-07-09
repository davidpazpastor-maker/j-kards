package com.taskflow.jkards.infrastructure.adapter.out.security;

import com.taskflow.jkards.domain.model.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Adaptador que permite usar el Usuario del dominio con Spring Security.
 * Implementa UserDetails (interfaz de Spring Security) para el Usuario (POJO del dominio).
 * CLEAN ARCHITECTURE: Usuario del dominio sin dependencias de Spring, pero compatible con Spring mediante este adaptador.
 */
public class UsuarioUserDetailsAdapter implements UserDetails {

    private final Usuario usuario;

    public UsuarioUserDetailsAdapter(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())
        );
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();  // Retorna passwordHash del Usuario
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
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

    /**
     * Retorna el Usuario del dominio (POJO)
     */
    public Usuario getUsuario() {
        return usuario;
    }
}
