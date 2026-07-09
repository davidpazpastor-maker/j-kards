package com.taskflow.jkards.infrastructure.adapter.out.persistence.repository;

import com.taskflow.jkards.application.port.out.ColumnaRepositoryPort;
import com.taskflow.jkards.domain.model.Columna;
import com.taskflow.jkards.infrastructure.adapter.out.persistence.entity.ColumnaJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ColumnaRepositoryAdapter implements ColumnaRepositoryPort {

    private final ColumnaJpaRepository columnaJpaRepository;

    @Override
    public Columna guardar(Columna columna) {
        ColumnaJpaEntity entity = toEntity(columna);
        ColumnaJpaEntity saved = columnaJpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Columna> findById(UUID id) {
        return columnaJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Columna> findByProyectoId(UUID proyectoId) {
        return columnaJpaRepository.findByProyectoIdOrderByPosicionAsc(proyectoId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        columnaJpaRepository.deleteById(id);
    }

    @Override
    public Columna actualizar(Columna columna) {
        return guardar(columna);
    }

    // Mappers internos (sin MapStruct para simplificar)
    private ColumnaJpaEntity toEntity(Columna columna) {
        return ColumnaJpaEntity.builder()
                .id(columna.getId())
                .proyectoId(columna.getProyectoId())
                .nombre(columna.getNombre())
                .posicion(columna.getPosicion())
                .fechaCreacion(columna.getFechaCreacion())
                .build();
    }

    private Columna toDomain(ColumnaJpaEntity entity) {
        return Columna.builder()
                .id(entity.getId())
                .proyectoId(entity.getProyectoId())
                .nombre(entity.getNombre())
                .posicion(entity.getPosicion())
                .fechaCreacion(entity.getFechaCreacion())
                .build();
    }
}