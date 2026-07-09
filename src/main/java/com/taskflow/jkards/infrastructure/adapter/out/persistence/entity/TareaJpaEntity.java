package com.taskflow.jkards.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import com.taskflow.jkards.domain.model.Tarea;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad JPA - SOLO para persistencia.
 * Representación técnica de Tarea del dominio.
 */
@Entity
@Table(name = "tareas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TareaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "columna_id", nullable = false)
    private UUID columnaId;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false, columnDefinition = "prioridad_enum")
    @Builder.Default
    private Tarea.Prioridad prioridad = Tarea.Prioridad.MEDIA;

    @Column(name = "responsable_id")
    private UUID responsableId;

    @Column(name = "fecha_limite")
    private LocalDateTime fechaLimite;

    @Column(nullable = false)
    @Builder.Default
    private Integer posicion = 0;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        if (fechaCreacion == null) {
            fechaCreacion = LocalDateTime.now();
        }
    }
}
