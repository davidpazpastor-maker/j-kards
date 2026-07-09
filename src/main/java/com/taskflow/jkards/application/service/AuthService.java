package com.taskflow.jkards.application.service;

import com.taskflow.jkards.application.dto.AuthDTOs;
import com.taskflow.jkards.domain.model.Usuario;
import com.taskflow.jkards.domain.repository.UsuarioRepository;
import com.taskflow.jkards.infrastructure.adapter.out.security.UsuarioUserDetailsAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Registra un nuevo usuario en el dominio.
     * El adaptador de persistencia (UsuarioRepositoryAdapter) maneja la traducción a/desde JPA.
     */
    public AuthDTOs.UserResponse register(AuthDTOs.RegisterRequest request) {
        // Validar email único
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Crear usuario de dominio (POJO puro)
        Usuario usuario = new Usuario(
                request.getNombre(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );

        // Guardar a través del puerto (adaptador traduce a JPA)
        usuario = usuarioRepository.save(usuario);

        return toUserResponse(usuario);
    }

    /**
     * Autentica un usuario contra Spring Security.
     * El servicio CustomUserDetailsService lo carga del repositorio del dominio.
     */
    public AuthDTOs.UserResponse login(AuthDTOs.LoginRequest request) {
        // Autenticar
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Obtener el Usuario del dominio desde el adaptador
        UsuarioUserDetailsAdapter adapter = (UsuarioUserDetailsAdapter) authentication.getPrincipal();
        Usuario usuario = adapter.getUsuario();

        return toUserResponse(usuario);
    }

    public void logout() {
        SecurityContextHolder.clearContext();
    }

    private AuthDTOs.UserResponse toUserResponse(Usuario usuario) {
        return AuthDTOs.UserResponse.builder()
                .id(usuario.getId().toString())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .rol(usuario.getRol().name())
                .build();
    }
}