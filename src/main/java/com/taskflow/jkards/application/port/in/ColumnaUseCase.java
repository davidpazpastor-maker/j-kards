package com.taskflow.jkards.application.port.in;

import com.taskflow.jkards.application.dto.ColumnaDTOs.CrearColumnaRequest;
import com.taskflow.jkards.application.dto.ColumnaDTOs.ColumnaResponse;
import com.taskflow.jkards.application.dto.ColumnaDTOs.ActualizarColumnaRequest;

import java.util.List;
import java.util.UUID;

public interface ColumnaUseCase {
    ColumnaResponse crearColumna(CrearColumnaRequest request);
    ColumnaResponse obtenerColumna(UUID id);
    List<ColumnaResponse> obtenerColumnasPorProyecto(UUID proyectoId);
    ColumnaResponse actualizarColumna(UUID id, ActualizarColumnaRequest request);
    void eliminarColumna(UUID id);
}