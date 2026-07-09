package com.taskflow.jkards.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

public class ColumnaDTOs {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CrearColumnaRequest {
        private UUID proyectoId;

        @NotBlank(message = "El nombre de la columna es obligatorio")
        private String nombre;

        private Integer posicion;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ColumnaResponse {
        private UUID id;
        private UUID proyectoId;
        private String nombre;
        private Integer posicion;
        private List<TareaDTOs.TareaResponse> tareas;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActualizarColumnaRequest {
        private String nombre;
        private Integer posicion;
    }
}
