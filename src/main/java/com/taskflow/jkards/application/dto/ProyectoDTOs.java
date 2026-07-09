package com.taskflow.jkards.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

public class ProyectoDTOs {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CrearProyectoRequest {
        @NotBlank(message = "El nombre del proyecto es obligatorio")
        @Size(max = 150, message = "El nombre no puede superar 150 caracteres")
        private String nombre;

        @Size(max = 1000, message = "La descripcion no puede superar 1000 caracteres")
        private String descripcion;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ActualizarProyectoRequest {
        @NotBlank(message = "El nombre del proyecto es obligatorio")
        @Size(max = 150, message = "El nombre no puede superar 150 caracteres")
        private String nombre;

        @Size(max = 1000, message = "La descripcion no puede superar 1000 caracteres")
        private String descripcion;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProyectoResponse {
        private UUID id;
        private String nombre;
        private String descripcion;
        private UUID creadorId;
        private LocalDateTime fechaCreacion;
        private Integer totalColumnas;
    }
}
