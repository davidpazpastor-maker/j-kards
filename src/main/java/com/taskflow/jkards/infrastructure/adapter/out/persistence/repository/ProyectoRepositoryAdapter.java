package com.taskflow.jkards.infrastructure.adapter.out.persistence.repository;

import com.taskflow.jkards.domain.model.Proyecto;
import com.taskflow.jkards.domain.repository.ProyectoRepository;
import com.taskflow.jkards.infrastructure.adapter.out.persistence.entity.ProyectoJpaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Adaptador que implementa el puerto ProyectoRepository del dominio.
 * Traduce entre objetos de dominio (Proyecto) y entidades JPA (ProyectoJpaEntity).
 */
@Component
@RequiredArgsConstructor
public class ProyectoRepositoryAdapter implements ProyectoRepository {

    private final ProyectoJpaRepository proyectoJpaRepository;

    @Override
    public Proyecto save(Proyecto proyecto) {
        ProyectoJpaEntity entity = mapToDomainEntity(proyecto);
        ProyectoJpaEntity saved = proyectoJpaRepository.save(entity);
        return mapToPOJO(saved);
    }

    @Override
    public Optional<Proyecto> findById(UUID id) {
        return proyectoJpaRepository.findById(id)
                .map(this::mapToPOJO);
    }

    @Override
    public List<Proyecto> findByCreadorId(UUID creadorId) {
        return proyectoJpaRepository.findByCreadorId(creadorId)
                .stream()
                .map(this::mapToPOJO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Proyecto> findAll() {
        return proyectoJpaRepository.findAll()
                .stream()
                .map(this::mapToPOJO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(UUID id) {
        proyectoJpaRepository.deleteById(id);
    }

    private Proyecto mapToPOJO(ProyectoJpaEntity entity) {
        return Proyecto.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .creadorId(entity.getCreadorId())
                .fechaCreacion(entity.getFechaCreacion())
                .build();
    }

    private ProyectoJpaEntity mapToDomainEntity(Proyecto proyecto) {
        return ProyectoJpaEntity.builder()
                .id(proyecto.getId())
                .nombre(proyecto.getNombre())
                .descripcion(proyecto.getDescripcion())
                .creadorId(proyecto.getCreadorId())
                .fechaCreacion(proyecto.getFechaCreacion())
                .build();
    }
}
