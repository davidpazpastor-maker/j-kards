package com.taskflow.jkards.infrastructure.adapter.out.persistence.repository;

import com.taskflow.jkards.domain.model.Usuario;
import com.taskflow.jkards.domain.repository.UsuarioRepository;
import com.taskflow.jkards.infrastructure.adapter.out.persistence.entity.UsuarioJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Adaptador que implementa el puerto UsuarioRepository del dominio.
 * Traduce entre objetos de dominio (Usuario) y entidades JPA (UsuarioJpaEntity).
 * CLEAN ARCHITECTURE: El dominio NO conoce de JPA; JPA es un detalle de infraestructura.
 */
@Component
@RequiredArgsConstructor
public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final UsuarioJpaRepository usuarioJpaRepository;

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioJpaEntity entity = mapToDomainEntity(usuario);
        UsuarioJpaEntity saved = usuarioJpaRepository.save(entity);
        return mapToPOJO(saved);
    }

    @Override
    public Optional<Usuario> findById(UUID id) {
        return usuarioJpaRepository.findById(id)
                .map(this::mapToPOJO);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return usuarioJpaRepository.findByEmail(email)
                .map(this::mapToPOJO);
    }

    @Override
    public boolean existsByEmail(String email) {
        return usuarioJpaRepository.existsByEmail(email);
    }

    @Override
    public void delete(UUID id) {
        usuarioJpaRepository.deleteById(id);
    }

    /**
     * Convierte UsuarioJpaEntity (técnica) a Usuario (dominio POJO)
     */
    private Usuario mapToPOJO(UsuarioJpaEntity entity) {
        return Usuario.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .email(entity.getEmail())
                .passwordHash(entity.getPasswordHash())
                .fechaCreacion(entity.getFechaCreacion())
                .rol(Usuario.Rol.USER)
                .build();
    }

    /**
     * Convierte Usuario (dominio POJO) a UsuarioJpaEntity (técnica)
     */
    private UsuarioJpaEntity mapToDomainEntity(Usuario usuario) {
        return UsuarioJpaEntity.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .passwordHash(usuario.getPasswordHash())
                .fechaCreacion(usuario.getFechaCreacion())
                .build();
    }
}
