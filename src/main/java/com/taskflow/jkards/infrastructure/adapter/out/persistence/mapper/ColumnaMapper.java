package com.taskflow.jkards.infrastructure.adapter.out.persistence.mapper;

import com.taskflow.jkards.domain.model.Columna;
import com.taskflow.jkards.infrastructure.adapter.out.persistence.entity.ColumnaJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ColumnaMapper {

    @Mapping(target = "proyecto.id", source = "proyectoId")
    ColumnaJpaEntity toEntity(Columna columna);

    @Mapping(target = "proyectoId", source = "proyecto.id")
    Columna toDomain(ColumnaJpaEntity entity);

    void updateEntity(Columna columna, @MappingTarget ColumnaJpaEntity entity);
}