package com.taskflow.jkards.domain.repository;

import com.taskflow.jkards.domain.model.Usuario;

import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida (interfaz de repositorio) - Define contrato sin implementación.
 * NO depende de Spring ni JPA.
 * Las implementaciones están en infrastructure/adapter/out/persistence
 */
public interface UsuarioRepository {
    Usuario save(Usuario usuario);
    Optional<Usuario> findById(UUID id);
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    void delete(UUID id);
}