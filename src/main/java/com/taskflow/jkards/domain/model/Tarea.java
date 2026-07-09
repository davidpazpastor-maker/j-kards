package com.taskflow.jkards.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad de dominio pura - Tarea (POJO sin JPA)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tarea {

    private UUID id;
    private UUID columnaId;  // Referencia por ID, no objeto Columna
    private String titulo;
    private String descripcion;
    @Builder.Default
    private Prioridad prioridad = Prioridad.MEDIA;
    private UUID responsableId;  // Referencia por ID, no objeto Usuario
    private LocalDateTime fechaLimite;
    @Builder.Default
    private Integer posicion = 0;
    private LocalDateTime fechaCreacion;

    public Tarea(UUID columnaId, String titulo, String descripcion) {
        this.columnaId = columnaId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = Prioridad.MEDIA;
        this.posicion = 0;
        this.fechaCreacion = LocalDateTime.now();
    }

    public void asignarResponsable(UUID responsableId) {
        this.responsableId = responsableId;
    }

    public void actualizarPrioridad(Prioridad nuevaPrioridad) {
        this.prioridad = nuevaPrioridad;
    }


    public enum Prioridad {
        BAJA, MEDIA, ALTA, URGENTE
    }
}
