package com.taskflow.jkards.application.port.in;

import com.taskflow.jkards.application.dto.ProyectoDTOs;
import java.util.List;
import java.util.UUID;

/**
 * Puerto de entrada (use case) - Obtener proyectos por usuario.
 */
public interface ObtenerProyectosUseCase {
    List<ProyectoDTOs.ProyectoResponse> obtenerProyectosPorUsuario(UUID usuarioId);
}
