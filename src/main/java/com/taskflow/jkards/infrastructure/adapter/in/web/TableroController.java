package com.taskflow.jkards.infrastructure.adapter.in.web;

import com.taskflow.jkards.application.dto.ColumnaDTOs.CrearColumnaRequest;
import com.taskflow.jkards.application.dto.TareaDTOs;
import com.taskflow.jkards.application.dto.TareaDTOs.CrearTareaRequest;
import com.taskflow.jkards.application.dto.TareaDTOs.MoverTareaRequest;
import com.taskflow.jkards.application.dto.TareaDTOs.TareaResponse;
import com.taskflow.jkards.application.port.in.ColumnaUseCase;
import com.taskflow.jkards.application.port.in.ObtenerProyectoUseCase;
import com.taskflow.jkards.application.port.in.TareaUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/proyectos/{proyectoId}/tablero")
@RequiredArgsConstructor
public class TableroController {

    private final ColumnaUseCase columnaUseCase;
    private final TareaUseCase tareaUseCase;
    private final ObtenerProyectoUseCase obtenerProyectoUseCase;

    @GetMapping
    public String mostrarTablero(@PathVariable UUID proyectoId, Model model) {
        var proyecto = obtenerProyectoUseCase.obtenerProyecto(proyectoId);
        var columnas = columnaUseCase.obtenerColumnasPorProyecto(proyectoId);

        model.addAttribute("proyecto", proyecto);
        model.addAttribute("proyectoId", proyectoId);
        model.addAttribute("columnas", columnas);
        model.addAttribute("crearColumnaRequest", new CrearColumnaRequest());
        model.addAttribute("crearTareaRequest", new CrearTareaRequest());

        return "tablero/kanban-board";
    }

    @PostMapping("/columnas")
    public String crearColumna(@PathVariable UUID proyectoId,
                               @Valid @ModelAttribute CrearColumnaRequest request,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        request.setProyectoId(proyectoId);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Error al crear la columna");
            return "redirect:/proyectos/{proyectoId}/tablero";
        }

        columnaUseCase.crearColumna(request);

        redirectAttributes.addFlashAttribute("success", "Columna creada correctamente");
        return "redirect:/proyectos/{proyectoId}/tablero";
    }

    @PostMapping("/tareas")
    public String crearTarea(@PathVariable UUID proyectoId,
                             @Valid @ModelAttribute CrearTareaRequest request,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Error al crear la tarea");
            return "redirect:/proyectos/{proyectoId}/tablero";
        }

        tareaUseCase.crearTarea(request);

        redirectAttributes.addFlashAttribute("success", "Tarea creada correctamente");
        return "redirect:/proyectos/{proyectoId}/tablero";
    }

    @PostMapping("/tareas/{tareaId}/mover")
    @ResponseBody
    public TareaResponse moverTarea(@PathVariable UUID tareaId,
                                    @RequestBody MoverTareaRequest request) {
        return tareaUseCase.moverTarea(tareaId, request);
    }
    // Endpoint para editar tarea inline
    @PutMapping("/tareas/{tareaId}")
    @ResponseBody
    public TareaResponse actualizarTarea(@PathVariable UUID tareaId,
                                         @RequestBody TareaDTOs.ActualizarTareaRequest request) {
        return tareaUseCase.actualizarTarea(tareaId, request);
    }

    // Endpoint para eliminar tarea
    @DeleteMapping("/tareas/{tareaId}")
    @ResponseBody
    public Map<String, String> eliminarTarea(@PathVariable UUID tareaId) {
        tareaUseCase.eliminarTarea(tareaId);
        Map<String, String> response = new HashMap<>();
        response.put("success", "Tarea eliminada correctamente");
        return response;
    }

    // Endpoint para eliminar columna
    @DeleteMapping("/columnas/{columnaId}")
    @ResponseBody
    public Map<String, String> eliminarColumna(@PathVariable UUID columnaId) {
        columnaUseCase.eliminarColumna(columnaId);
        Map<String, String> response = new HashMap<>();
        response.put("success", "Columna eliminada correctamente");
        return response;
    }
    @GetMapping("/progreso")
    @ResponseBody
    public Map<String, Object> obtenerProgreso(@PathVariable UUID proyectoId) {
        var columnas = columnaUseCase.obtenerColumnasPorProyecto(proyectoId);

        int totalTareas = 0;
        int tareasCompletadas = 0;

        for (var columna : columnas) {
            int tareasEnColumna = columna.getTareas().size();
            totalTareas += tareasEnColumna;

            if (columna.getNombre().toLowerCase().contains("hecho") ||
                    columna.getNombre().toLowerCase().contains("completado")) {
                tareasCompletadas += tareasEnColumna;
            }
        }

        double porcentaje = totalTareas > 0 ?
                (double) tareasCompletadas / totalTareas * 100 : 0;

        Map<String, Object> response = new HashMap<>();
        response.put("totalTareas", totalTareas);
        response.put("tareasCompletadas", tareasCompletadas);
        response.put("porcentaje", Math.round(porcentaje * 100.0) / 100.0);

        return response;
    }
}
