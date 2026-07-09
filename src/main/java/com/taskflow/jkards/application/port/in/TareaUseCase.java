package com.taskflow.jkards.application.port.in;

import com.taskflow.jkards.application.dto.TareaDTOs.*;

import java.util.List;
import java.util.UUID;

public interface TareaUseCase {
    TareaResponse crearTarea(CrearTareaRequest request);
    TareaResponse obtenerTarea(UUID id);
    List<TareaResponse> obtenerTareasPorColumna(UUID columnaId);
    List<TareaResponse> obtenerTareasPorProyecto(UUID proyectoId);
    TareaResponse actualizarTarea(UUID id, ActualizarTareaRequest request);
    TareaResponse moverTarea(UUID tareaId, MoverTareaRequest request);
    void eliminarTarea(UUID id);
}