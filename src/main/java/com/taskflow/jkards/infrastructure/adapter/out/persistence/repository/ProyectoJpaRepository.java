package com.taskflow.jkards.infrastructure.adapter.out.persistence.repository;

import com.taskflow.jkards.infrastructure.adapter.out.persistence.entity.ProyectoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Spring Data JPA Repository - INTERNO de infrastructure.
 * NO exponer fuera de infrastructure/adapter/out/persistence.
 */
@Repository
public interface ProyectoJpaRepository extends JpaRepository<ProyectoJpaEntity, UUID> {
    List<ProyectoJpaEntity> findByCreadorId(UUID creadorId);
    boolean existsByCreadorIdAndNombre(UUID creadorId, String nombre);

}
