package com.taskflow.jkards.application.service;

import com.taskflow.jkards.application.dto.ColumnaDTOs.*;
import com.taskflow.jkards.application.dto.TareaDTOs;
import com.taskflow.jkards.application.port.in.ColumnaUseCase;
import com.taskflow.jkards.application.port.out.ColumnaRepositoryPort;
import com.taskflow.jkards.application.port.out.TareaRepositoryPort;
import com.taskflow.jkards.domain.model.Columna;
import com.taskflow.jkards.domain.model.Tarea;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ColumnaApplicationService implements ColumnaUseCase {

    private final ColumnaRepositoryPort columnaRepository;
    private final TareaRepositoryPort tareaRepository;

    @Override
    @Transactional
    public ColumnaResponse crearColumna(CrearColumnaRequest request) {
        int posicion = request.getPosicion() != null
                ? request.getPosicion()
                : columnaRepository.findByProyectoId(request.getProyectoId()).size() + 1;

        Columna columna = Columna.builder()
                .proyectoId(request.getProyectoId())
                .nombre(request.getNombre())
                .posicion(posicion)
                .fechaCreacion(LocalDateTime.now())
                .build();

        Columna saved = columnaRepository.guardar(columna);
        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ColumnaResponse obtenerColumna(UUID id) {
        return columnaRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Columna no encontrada"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ColumnaResponse> obtenerColumnasPorProyecto(UUID proyectoId) {
        return columnaRepository.findByProyectoId(proyectoId)
                .stream()
                .map(this::withTareas)
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ColumnaResponse actualizarColumna(UUID id, ActualizarColumnaRequest request) {
        Columna columna = columnaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Columna no encontrada"));

        if (request.getNombre() != null) {
            columna.setNombre(request.getNombre());
        }
        if (request.getPosicion() != null) {
            columna.setPosicion(request.getPosicion());
        }

        Columna updated = columnaRepository.actualizar(columna);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void eliminarColumna(UUID id) {
        columnaRepository.deleteById(id);
    }

    private ColumnaResponse mapToResponse(Columna columna) {
        return ColumnaResponse.builder()
                .id(columna.getId())
                .proyectoId(columna.getProyectoId())
                .nombre(columna.getNombre())
                .posicion(columna.getPosicion())
                .tareas(tareasOrEmpty(columna).stream()
                        .map(t -> TareaDTOs.TareaResponse.builder()
                                .id(t.getId())
                                .columnaId(t.getColumnaId())
                                .titulo(t.getTitulo())
                                .descripcion(t.getDescripcion())
                                .prioridad(t.getPrioridad())
                                .responsableNombre(null)
                                .fechaLimite(t.getFechaLimite())
                                .posicion(t.getPosicion())
                                .fechaCreacion(t.getFechaCreacion())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private Columna withTareas(Columna columna) {
        columna.setTareas(tareaRepository.findByColumnaId(columna.getId()));
        return columna;
    }

    private List<Tarea> tareasOrEmpty(Columna columna) {
        return columna.getTareas() != null ? columna.getTareas() : Collections.emptyList();
    }
}
