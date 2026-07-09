package com.taskflow.jkards.infrastructure.adapter.out.persistence.repository;

import com.taskflow.jkards.application.port.out.TareaRepositoryPort;
import com.taskflow.jkards.domain.model.Tarea;
import com.taskflow.jkards.infrastructure.adapter.out.persistence.entity.TareaJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TareaRepositoryAdapter implements TareaRepositoryPort {

    private final TareaJpaRepository tareaJpaRepository;

    @Override
    public Tarea guardar(Tarea tarea) {
        TareaJpaEntity entity = toEntity(tarea);
        TareaJpaEntity saved = tareaJpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Tarea> findById(UUID id) {
        return tareaJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Tarea> findByColumnaId(UUID columnaId) {
        return tareaJpaRepository.findByColumnaIdOrderByPosicionAsc(columnaId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Tarea> findByProyectoId(UUID proyectoId) {
        return tareaJpaRepository.findByProyectoIdOrderByPosicionAsc(proyectoId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        tareaJpaRepository.deleteById(id);
    }

    @Override
    public Tarea actualizar(Tarea tarea) {
        return guardar(tarea);
    }

    @Override
    @Transactional
    public void moverAColumna(UUID tareaId, UUID columnaId, Integer posicion) {
        tareaJpaRepository.moverAColumna(tareaId, columnaId, posicion);
    }

    // Mappers internos
    private TareaJpaEntity toEntity(Tarea tarea) {
        return TareaJpaEntity.builder()
                .id(tarea.getId())
                .columnaId(tarea.getColumnaId())
                .titulo(tarea.getTitulo())
                .descripcion(tarea.getDescripcion())
                .prioridad(tarea.getPrioridad())
                .responsableId(tarea.getResponsableId())
                .fechaLimite(tarea.getFechaLimite())
                .posicion(tarea.getPosicion())
                .fechaCreacion(tarea.getFechaCreacion())
                .build();
    }

    private Tarea toDomain(TareaJpaEntity entity) {
        return Tarea.builder()
                .id(entity.getId())
                .columnaId(entity.getColumnaId())
                .titulo(entity.getTitulo())
                .descripcion(entity.getDescripcion())
                .prioridad(entity.getPrioridad())
                .responsableId(entity.getResponsableId())
                .fechaLimite(entity.getFechaLimite())
                .posicion(entity.getPosicion())
                .fechaCreacion(entity.getFechaCreacion())
                .build();
    }
}