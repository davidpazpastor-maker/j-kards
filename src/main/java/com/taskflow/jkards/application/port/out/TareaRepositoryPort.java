package com.taskflow.jkards.application.port.out;

import com.taskflow.jkards.domain.model.Tarea;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TareaRepositoryPort {
    Tarea guardar(Tarea tarea);
    Optional<Tarea> findById(UUID id);
    List<Tarea> findByColumnaId(UUID columnaId);
    List<Tarea> findByProyectoId(UUID proyectoId);
    void deleteById(UUID id);
    Tarea actualizar(Tarea tarea);
    void moverAColumna(UUID tareaId, UUID columnaId, Integer posicion);
}