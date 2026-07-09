-- ============================================================
-- JKards - Update funcional Kanban
-- Motor: PostgreSQL 15+ / Supabase
-- Ejecutar después del schema base si ya existe la base de datos.
-- ============================================================

CREATE EXTENSION IF NOT EXISTS pgcrypto;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'prioridad_enum') THEN
        CREATE TYPE prioridad_enum AS ENUM ('BAJA', 'MEDIA', 'ALTA', 'URGENTE');
    END IF;
END $$;

DO $$
BEGIN
    IF EXISTS (SELECT 1 FROM pg_type WHERE typname = 'prioridad_enum')
       AND NOT EXISTS (
           SELECT 1
           FROM pg_enum e
           JOIN pg_type t ON t.oid = e.enumtypid
           WHERE t.typname = 'prioridad_enum'
             AND e.enumlabel = 'URGENTE'
       ) THEN
        ALTER TYPE prioridad_enum ADD VALUE 'URGENTE';
    END IF;
END $$;

-- Asegura índices esperados por el tablero.
CREATE INDEX IF NOT EXISTS idx_usuarios_email ON usuarios(email);
CREATE INDEX IF NOT EXISTS idx_proyectos_creador ON proyectos(creador_id);
CREATE INDEX IF NOT EXISTS idx_columnas_proyecto ON columnas(proyecto_id);
CREATE INDEX IF NOT EXISTS idx_tareas_columna ON tareas(columna_id);
CREATE INDEX IF NOT EXISTS idx_tareas_responsable ON tareas(responsable_id);
CREATE INDEX IF NOT EXISTS idx_tareas_prioridad ON tareas(prioridad);
CREATE INDEX IF NOT EXISTS idx_tareas_fecha_limite ON tareas(fecha_limite);

-- Crea columnas base en proyectos existentes que aún no tengan columnas.
INSERT INTO columnas (proyecto_id, nombre, posicion)
SELECT p.id, base.nombre, base.posicion
FROM proyectos p
CROSS JOIN (
    VALUES
        ('Por Hacer', 1),
        ('En Progreso', 2),
        ('Hecho', 3)
) AS base(nombre, posicion)
WHERE NOT EXISTS (
    SELECT 1
    FROM columnas c
    WHERE c.proyecto_id = p.id
);

-- Normaliza tareas antiguas sin posición útil dentro de cada columna.
WITH ordenadas AS (
    SELECT
        id,
        ROW_NUMBER() OVER (
            PARTITION BY columna_id
            ORDER BY posicion ASC, fecha_creacion ASC, id ASC
        ) AS nueva_posicion
    FROM tareas
)
UPDATE tareas t
SET posicion = o.nueva_posicion
FROM ordenadas o
WHERE t.id = o.id;
