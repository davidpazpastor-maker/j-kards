package com.taskflow.jkards.application.port.in;

import com.taskflow.jkards.application.dto.ProyectoDTOs;
import java.util.UUID;

/**
 * Puerto de entrada (use case) - Obtener proyecto por ID.
 */
public interface ObtenerProyectoUseCase {
    ProyectoDTOs.ProyectoResponse obtenerProyecto(UUID proyectoId);
}
