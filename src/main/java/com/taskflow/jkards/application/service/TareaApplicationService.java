package com.taskflow.jkards.application.service;

import com.taskflow.jkards.application.dto.TareaDTOs.*;
import com.taskflow.jkards.application.port.in.TareaUseCase;
import com.taskflow.jkards.application.port.out.TareaRepositoryPort;
import com.taskflow.jkards.domain.model.Tarea;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TareaApplicationService implements TareaUseCase {

    private final TareaRepositoryPort tareaRepository;

    @Override
    @Transactional
    public TareaResponse crearTarea(CrearTareaRequest request) {
        int posicion = request.getPosicion() != null
                ? request.getPosicion()
                : tareaRepository.findByColumnaId(request.getColumnaId()).size() + 1;

        Tarea tarea = Tarea.builder()
                .columnaId(request.getColumnaId())
                .titulo(request.getTitulo())
                .descripcion(request.getDescripcion())
                .prioridad(request.getPrioridad() != null ? request.getPrioridad() : Tarea.Prioridad.MEDIA)
                .responsableId(request.getResponsableId())
                .fechaLimite(request.getFechaLimite())
                .posicion(posicion)
                .fechaCreacion(LocalDateTime.now())
                .build();

        Tarea saved = tareaRepository.guardar(tarea);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public TareaResponse obtenerTarea(UUID id) {
        return tareaRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TareaResponse> obtenerTareasPorColumna(UUID columnaId) {
        return tareaRepository.findByColumnaId(columnaId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TareaResponse> obtenerTareasPorProyecto(UUID proyectoId) {
        return tareaRepository.findByProyectoId(proyectoId)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TareaResponse actualizarTarea(UUID id, ActualizarTareaRequest request) {
        Tarea tarea = tareaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));

        if (request.getTitulo() != null) {
            tarea.setTitulo(request.getTitulo());
        }
        if (request.getDescripcion() != null) {
            tarea.setDescripcion(request.getDescripcion());
        }
        if (request.getPrioridad() != null) {
            tarea.actualizarPrioridad(request.getPrioridad());
        }
        if (request.getResponsableId() != null) {
            tarea.asignarResponsable(request.getResponsableId());
        }
        if (request.getFechaLimite() != null) {
            tarea.setFechaLimite(request.getFechaLimite());
        }

        Tarea updated = tareaRepository.actualizar(tarea);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public TareaResponse moverTarea(UUID tareaId, MoverTareaRequest request) {
        tareaRepository.moverAColumna(tareaId, request.getColumnaId(), request.getPosicion());

        return tareaRepository.findById(tareaId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }

    @Override
    @Transactional
    public void eliminarTarea(UUID id) {
        tareaRepository.deleteById(id);
    }

    private TareaResponse mapToResponse(Tarea tarea) {
        return TareaResponse.builder()
                .id(tarea.getId())
                .columnaId(tarea.getColumnaId())
                .titulo(tarea.getTitulo())
                .descripcion(tarea.getDescripcion())
                .prioridad(tarea.getPrioridad())
                .responsableNombre(null) // TODO: Cargar desde usuario si es necesario
                .fechaLimite(tarea.getFechaLimite())
                .posicion(tarea.getPosicion())
                .fechaCreacion(tarea.getFechaCreacion())
                .build();
    }
}
