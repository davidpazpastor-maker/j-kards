package com.taskflow.jkards.infrastructure.adapter.out.persistence.mapper;

import com.taskflow.jkards.domain.model.Tarea;
import com.taskflow.jkards.infrastructure.adapter.out.persistence.entity.TareaJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TareaMapper {

    @Mapping(target = "columna.id", source = "columnaId")
    @Mapping(target = "responsable.id", source = "responsableId")
    TareaJpaEntity toEntity(Tarea tarea);

    @Mapping(target = "columnaId", source = "columna.id")
    @Mapping(target = "responsableId", source = "responsable.id")
    Tarea toDomain(TareaJpaEntity entity);

    void updateEntity(Tarea tarea, @MappingTarget TareaJpaEntity entity);
}