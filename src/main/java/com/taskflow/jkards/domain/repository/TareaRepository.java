package com.taskflow.jkards.domain.repository;

import com.taskflow.jkards.domain.model.Tarea;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida (interfaz de repositorio) - Define contrato sin implementación.
 * NO depende de Spring ni JPA.
 * Las implementaciones están en infrastructure/adapter/out/persistence
 */
public interface TareaRepository {
    Tarea guardar(Tarea tarea);
    Optional<Tarea> findById(UUID id);
    List<Tarea> findByColumnaId(UUID columnaId);
    List<Tarea> findByProyectoId(UUID proyectoId);
    void deleteById(UUID id);
    Tarea actualizar(Tarea tarea);
    void moverAColumna(UUID tareaId, UUID columnaId, Integer posicion);
}
