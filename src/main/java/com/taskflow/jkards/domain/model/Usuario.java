package com.taskflow.jkards.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad de dominio pura - POJO sin dependencias de frameworks.
 * No tiene anotaciones JPA ni Spring.
 * Contiene solo lógica de negocio.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    private UUID id;
    private String nombre;
    private String email;
    private String passwordHash;
    private LocalDateTime fechaCreacion;
    @Builder.Default
    private Rol rol = Rol.USER;

    /**
     * Constructor de conveniencia para crear un usuario nuevo
     */
    public Usuario(String nombre, String email, String passwordHash) {
        this.nombre = nombre;
        this.email = email;
        this.passwordHash = passwordHash;
        this.rol = Rol.USER;
        this.fechaCreacion = LocalDateTime.now();
    }

    /**
     * Retorna el passwordHash (usado por Spring Security a través del adapter)
     */
    public String getPassword() {
        return this.passwordHash;
    }

    public enum Rol {
        USER, ADMIN
    }
}
