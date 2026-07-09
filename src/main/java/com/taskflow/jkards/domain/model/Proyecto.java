package com.taskflow.jkards.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entidad de dominio pura - Proyecto (POJO sin JPA)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Proyecto {

    private UUID id;
    private String nombre;
    private String descripcion;
    private UUID creadorId;  // Referencia por ID, no objeto Usuario
    private LocalDateTime fechaCreacion;

    @Builder.Default
    private List<Columna> columnas = new ArrayList<>();

    public Proyecto(String nombre, String descripcion, UUID creadorId) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creadorId = creadorId;
        this.fechaCreacion = LocalDateTime.now();
        this.columnas = new ArrayList<>();
    }

    public void agregarColumna(Columna columna) {
        this.columnas.add(columna);
    }

    public void removerColumna(UUID columnaId) {
        this.columnas.removeIf(c -> c.getId().equals(columnaId));
    }
}
