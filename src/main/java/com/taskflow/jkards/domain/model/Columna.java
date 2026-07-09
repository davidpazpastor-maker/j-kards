package com.taskflow.jkards.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entidad de dominio pura - Columna (POJO sin JPA)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Columna {

    private UUID id;
    private UUID proyectoId;  // Referencia por ID, no objeto Proyecto
    private String nombre;
    private Integer posicion;
    private LocalDateTime fechaCreacion;

    @Builder.Default
    private List<Tarea> tareas = new ArrayList<>();

    public Columna(UUID proyectoId, String nombre, Integer posicion) {
        this.proyectoId = proyectoId;
        this.nombre = nombre;
        this.posicion = posicion;
        this.fechaCreacion = LocalDateTime.now();
        this.tareas = new ArrayList<>();
    }

    public void agregarTarea(Tarea tarea) {
        this.tareas.add(tarea);
    }

    public void removerTarea(UUID tareaId) {
        this.tareas.removeIf(t -> t.getId().equals(tareaId));
    }
}
