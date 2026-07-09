package com.taskflow.jkards.infrastructure.adapter.out.persistence.repository;

import com.taskflow.jkards.infrastructure.adapter.out.persistence.entity.ColumnaJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ColumnaJpaRepository extends JpaRepository<ColumnaJpaEntity, UUID> {

    /**
     * Encuentra todas las columnas de un proyecto ordenadas por posición
     */
    List<ColumnaJpaEntity> findByProyectoIdOrderByPosicionAsc(UUID proyectoId);

    /**
     * Cuenta las columnas de un proyecto
     */
    long countByProyectoId(UUID proyectoId);

    /**
     * Elimina todas las columnas de un proyecto
     */
    void deleteByProyectoId(UUID proyectoId);

    /**
     * Verifica si existe una columna con ese nombre en el proyecto
     */
    boolean existsByProyectoIdAndNombre(UUID proyectoId, String nombre);
}