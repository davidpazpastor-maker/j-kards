package com.taskflow.jkards.application.dto;

import com.taskflow.jkards.domain.model.Tarea.Prioridad;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class TareaDTOs {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CrearTareaRequest {
        @NotNull(message = "El ID de la columna es obligatorio")
        private UUID columnaId;

        @NotBlank(message = "El título es obligatorio")
        private String titulo;

        private String descripcion;

        @NotNull(message = "La prioridad es obligatoria")
        private Prioridad prioridad;

        private UUID responsableId;
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime fechaLimite;
        private Integer posicion;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TareaResponse {
        private UUID id;
        private UUID columnaId;
        private String titulo;
        private String descripcion;
        private Prioridad prioridad;
        private String responsableNombre;
        private LocalDateTime fechaLimite;
        private Integer posicion;
        private LocalDateTime fechaCreacion;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MoverTareaRequest {
        @NotNull(message = "El ID de la columna destino es obligatorio")
        private UUID columnaId;

        @NotNull(message = "La posición es obligatoria")
        private Integer posicion;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ActualizarTareaRequest {
        private String titulo;
        private String descripcion;
        private Prioridad prioridad;
        private UUID responsableId;
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime fechaLimite;
    }
}
