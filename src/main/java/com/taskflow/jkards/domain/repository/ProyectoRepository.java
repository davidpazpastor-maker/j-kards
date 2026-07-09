package com.taskflow.jkards.domain.repository;

import com.taskflow.jkards.domain.model.Proyecto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida (interfaz de repositorio) - Define contrato sin implementación.
 * NO depende de Spring ni JPA.
 * Las implementaciones están en infrastructure/adapter/out/persistence
 */
public interface ProyectoRepository {
    Proyecto save(Proyecto proyecto);
    Optional<Proyecto> findById(UUID id);
    List<Proyecto> findByCreadorId(UUID creadorId);
    List<Proyecto> findAll();
    void delete(UUID id);
}