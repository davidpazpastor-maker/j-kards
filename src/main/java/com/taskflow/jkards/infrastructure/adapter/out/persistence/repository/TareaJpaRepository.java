package com.taskflow.jkards.infrastructure.adapter.out.persistence.repository;

import com.taskflow.jkards.infrastructure.adapter.out.persistence.entity.TareaJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TareaJpaRepository extends JpaRepository<TareaJpaEntity, UUID> {

    /**
     * Encuentra todas las tareas de una columna ordenadas por posición
     */
    List<TareaJpaEntity> findByColumnaIdOrderByPosicionAsc(UUID columnaId);

    /**
     * Encuentra todas las tareas de un proyecto (via columnas) ordenadas
     */
    @Query("SELECT t FROM TareaJpaEntity t WHERE t.columnaId IN " +
            "(SELECT c.id FROM ColumnaJpaEntity c WHERE c.proyectoId = :proyectoId) " +
            "ORDER BY t.posicion ASC")
    List<TareaJpaEntity> findByProyectoIdOrderByPosicionAsc(@Param("proyectoId") UUID proyectoId);

    /**
     * Cuenta las tareas en una columna
     */
    long countByColumnaId(UUID columnaId);

    /**
     * Elimina todas las tareas de una columna
     */
    void deleteByColumnaId(UUID columnaId);

    /**
     * Mueve una tarea a otra columna actualizando su posición
     */
    @Modifying
    @Query("UPDATE TareaJpaEntity t SET t.columnaId = :columnaId, t.posicion = :posicion WHERE t.id = :tareaId")
    void moverAColumna(@Param("tareaId") UUID tareaId,
                       @Param("columnaId") UUID columnaId,
                       @Param("posicion") Integer posicion);

    /**
     * Actualiza la posición de una tarea
     */
    @Modifying
    @Query("UPDATE TareaJpaEntity t SET t.posicion = :posicion WHERE t.id = :tareaId")
    void actualizarPosicion(@Param("tareaId") UUID tareaId,
                            @Param("posicion") Integer posicion);

    /**
     * Actualiza posiciones de múltiples tareas en una columna
     */
    @Modifying
    @Query("UPDATE TareaJpaEntity t SET t.posicion = :posicion WHERE t.id = :tareaId AND t.columnaId = :columnaId")
    void actualizarPosicionEnColumna(@Param("tareaId") UUID tareaId,
                                     @Param("columnaId") UUID columnaId,
                                     @Param("posicion") Integer posicion);
}