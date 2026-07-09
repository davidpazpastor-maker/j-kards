package com.taskflow.jkards.application.service;

import com.taskflow.jkards.application.dto.ProyectoDTOs;
import com.taskflow.jkards.application.port.in.*;
import com.taskflow.jkards.application.port.out.ColumnaRepositoryPort;
import com.taskflow.jkards.domain.model.Columna;
import com.taskflow.jkards.domain.model.Proyecto;
import com.taskflow.jkards.domain.repository.ProyectoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Servicio de aplicación que implementa los use cases de Proyecto.
 * Orquesta el dominio (modelos y puertos) con los DTOs de aplicación.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ProyectoApplicationService implements
        CrearProyectoUseCase,
        ObtenerProyectosUseCase,
        ObtenerProyectoUseCase {

    private final ProyectoRepository proyectoRepository;
    private final ColumnaRepositoryPort columnaRepository;

    @Override
    public ProyectoDTOs.ProyectoResponse crearProyecto(UUID usuarioId, ProyectoDTOs.CrearProyectoRequest request) {
        Proyecto proyecto = new Proyecto(
                request.getNombre(),
                request.getDescripcion(),
                usuarioId
        );
        Proyecto saved = proyectoRepository.save(proyecto);

        // Crear columnas automáticas al crear proyecto
        crearColumnasPorDefecto(saved.getId());

        return toResponse(saved);
    }

    /**
     * Crea las columnas por defecto para un nuevo proyecto
     */
    private void crearColumnasPorDefecto(UUID proyectoId) {
        List<String> columnasPorDefecto = List.of(
                "Por Hacer",
                "En Progreso",
                "Hecho"
        );

        for (int i = 0; i < columnasPorDefecto.size(); i++) {
            Columna columna = Columna.builder()
                    .proyectoId(proyectoId)
                    .nombre(columnasPorDefecto.get(i))
                    .posicion(i + 1)
                    .fechaCreacion(LocalDateTime.now())
                    .build();

            columnaRepository.guardar(columna);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProyectoDTOs.ProyectoResponse> obtenerProyectosPorUsuario(UUID usuarioId) {
        return proyectoRepository.findByCreadorId(usuarioId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProyectoDTOs.ProyectoResponse obtenerProyecto(UUID proyectoId) {
        return proyectoRepository.findById(proyectoId)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }

    public ProyectoDTOs.ProyectoResponse actualizarProyecto(UUID proyectoId, ProyectoDTOs.ActualizarProyectoRequest request) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        proyecto.setNombre(request.getNombre());
        proyecto.setDescripcion(request.getDescripcion());

        Proyecto updated = proyectoRepository.save(proyecto);
        return toResponse(updated);
    }

    public void eliminarProyecto(UUID proyectoId) {
        proyectoRepository.delete(proyectoId);
    }

    private ProyectoDTOs.ProyectoResponse toResponse(Proyecto proyecto) {
        return ProyectoDTOs.ProyectoResponse.builder()
                .id(proyecto.getId())
                .nombre(proyecto.getNombre())
                .descripcion(proyecto.getDescripcion())
                .creadorId(proyecto.getCreadorId())
                .fechaCreacion(proyecto.getFechaCreacion())
                .totalColumnas(columnasOrEmpty(proyecto).size())
                .build();
    }

    private List<Columna> columnasOrEmpty(Proyecto proyecto) {
        return proyecto.getColumnas() != null ? proyecto.getColumnas() : Collections.emptyList();
    }
}
