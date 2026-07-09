package com.taskflow.jkards;

import com.taskflow.jkards.application.dto.ProyectoDTOs;
import com.taskflow.jkards.application.dto.TareaDTOs;
import com.taskflow.jkards.application.port.in.ColumnaUseCase;
import com.taskflow.jkards.application.port.in.CrearProyectoUseCase;
import com.taskflow.jkards.application.port.in.TareaUseCase;
import com.taskflow.jkards.domain.model.Tarea;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class KanbanWorkflowTests {

    @Autowired
    private CrearProyectoUseCase crearProyectoUseCase;

    @Autowired
    private ColumnaUseCase columnaUseCase;

    @Autowired
    private TareaUseCase tareaUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void crearProyectoCreaColumnasYPermiteCrearTareas() {
        UUID usuarioId = UUID.randomUUID();

        ProyectoDTOs.ProyectoResponse proyecto = crearProyectoUseCase.crearProyecto(
                usuarioId,
                ProyectoDTOs.CrearProyectoRequest.builder()
                        .nombre("Tablero funcional")
                        .descripcion("Flujo completo de Kanban")
                        .build()
        );

        var columnas = columnaUseCase.obtenerColumnasPorProyecto(proyecto.getId());
        assertThat(columnas)
                .extracting("nombre")
                .containsExactly("Por Hacer", "En Progreso", "Hecho");

        TareaDTOs.TareaResponse tarea = tareaUseCase.crearTarea(
                TareaDTOs.CrearTareaRequest.builder()
                        .columnaId(columnas.getFirst().getId())
                        .titulo("Primera tarea")
                        .descripcion("Detalle de la tarea")
                        .prioridad(Tarea.Prioridad.MEDIA)
                        .build()
        );

        var columnasConTareas = columnaUseCase.obtenerColumnasPorProyecto(proyecto.getId());
        assertThat(columnasConTareas.getFirst().getTareas())
                .extracting("id")
                .containsExactly(tarea.getId());
    }

    @Test
    @WithMockUser
    void kanbanBoardTemplateRenderizaConColumnasYTareas() throws Exception {
        UUID usuarioId = UUID.randomUUID();
        ProyectoDTOs.ProyectoResponse proyecto = crearProyectoUseCase.crearProyecto(
                usuarioId,
                ProyectoDTOs.CrearProyectoRequest.builder()
                        .nombre("Render tablero")
                        .descripcion("Verifica Thymeleaf")
                        .build()
        );

        var columnas = columnaUseCase.obtenerColumnasPorProyecto(proyecto.getId());
        tareaUseCase.crearTarea(TareaDTOs.CrearTareaRequest.builder()
                .columnaId(columnas.getFirst().getId())
                .titulo("Tarea visible")
                .prioridad(Tarea.Prioridad.ALTA)
                .build());

        mockMvc.perform(get("/proyectos/{proyectoId}/tablero", proyecto.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Render tablero")))
                .andExpect(content().string(containsString("Tarea visible")));
    }
}
