package com.taskflow.jkards.domain.repository;

import com.taskflow.jkards.domain.model.Columna;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida (interfaz de repositorio) - Define contrato sin implementación.
 * NO depende de Spring ni JPA.
 * Las implementaciones están en infrastructure/adapter/out/persistence
 */
public interface ColumnaRepository {
    Columna save(Columna columna);
    Optional<Columna> findById(UUID id);
    List<Columna> findByProyectoId(UUID proyectoId);
    void delete(UUID id);
}
