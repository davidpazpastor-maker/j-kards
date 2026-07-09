package com.taskflow.jkards.application.port.out;

import com.taskflow.jkards.domain.model.Columna;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ColumnaRepositoryPort {
    Columna guardar(Columna columna);
    Optional<Columna> findById(UUID id);
    List<Columna> findByProyectoId(UUID proyectoId);
    void deleteById(UUID id);
    Columna actualizar(Columna columna);
}