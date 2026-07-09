package com.taskflow.jkards.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

public class EtiquetaDTOs {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EtiquetaResponse {
        private UUID id;
        private String nombre;
        private String color;
    }

    public enum ColorEtiqueta {
        ROJO("#ef4444"),
        NARANJA("#f97316"),
        AMARILLO("#eab308"),
        VERDE("#22c55e"),
        AZUL("#3b82f6"),
        MORADO("#a855f7"),
        ROSA("#ec4899"),
        GRIS("#6b7280");

        private final String hex;

        ColorEtiqueta(String hex) {
            this.hex = hex;
        }

        public String getHex() {
            return hex;
        }
    }
}