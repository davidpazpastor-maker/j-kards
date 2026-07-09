package com.taskflow.jkards.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistItem {
    private UUID id;
    private UUID tareaId;
    private String texto;
    private boolean completado;
    private LocalDateTime fechaCreacion;
}