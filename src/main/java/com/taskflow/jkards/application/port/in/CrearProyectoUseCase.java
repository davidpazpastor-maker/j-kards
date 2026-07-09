package com.taskflow.jkards.application.port.in;

import com.taskflow.jkards.application.dto.ProyectoDTOs;
import java.util.UUID;

/**
 * Puerto de entrada (use case) - Crear proyecto.
 * Define el contrato sin implementación técnica.
 */
public interface CrearProyectoUseCase {
    ProyectoDTOs.ProyectoResponse crearProyecto(UUID usuarioId, ProyectoDTOs.CrearProyectoRequest request);
}
