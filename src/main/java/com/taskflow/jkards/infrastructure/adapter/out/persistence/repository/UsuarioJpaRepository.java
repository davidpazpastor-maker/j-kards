package com.taskflow.jkards.infrastructure.adapter.out.persistence.repository;

import com.taskflow.jkards.infrastructure.adapter.out.persistence.entity.UsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Spring Data JPA Repository - INTERNO de infrastructure.
 * NO exponer fuera de infrastructure/adapter/out/persistence.
 */
@Repository
public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpaEntity, UUID> {
    Optional<UsuarioJpaEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}
