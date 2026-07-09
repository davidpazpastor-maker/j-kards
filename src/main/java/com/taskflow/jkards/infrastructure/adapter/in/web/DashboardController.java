package com.taskflow.jkards.infrastructure.adapter.in.web;

import com.taskflow.jkards.application.dto.ProyectoDTOs;
import com.taskflow.jkards.application.port.in.CrearProyectoUseCase;
import com.taskflow.jkards.application.port.in.ObtenerProyectosUseCase;
import com.taskflow.jkards.domain.model.Usuario;
import com.taskflow.jkards.infrastructure.adapter.out.security.UsuarioUserDetailsAdapter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ObtenerProyectosUseCase obtenerProyectosUseCase;
    private final CrearProyectoUseCase crearProyectoUseCase;

    @GetMapping
    public String dashboard(@AuthenticationPrincipal UsuarioUserDetailsAdapter userDetails, Model model) {
        Usuario usuario = getAuthenticatedUser(userDetails);
        if (usuario == null) {
            return "redirect:/auth/login";
        }

        if (usuario.getRol() == Usuario.Rol.ADMIN) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("usuario", usuario);
        model.addAttribute("username", usuario.getNombre());
        model.addAttribute("proyectos", obtenerProyectosUseCase.obtenerProyectosPorUsuario(usuario.getId()));
        if (!model.containsAttribute("crearProyectoRequest")) {
            model.addAttribute("crearProyectoRequest", new ProyectoDTOs.CrearProyectoRequest());
        }

        return "dashboard/user-dashboard";
    }

    @PostMapping("/proyectos")
    public String crearProyecto(
            @AuthenticationPrincipal UsuarioUserDetailsAdapter userDetails,
            @Valid @ModelAttribute("crearProyectoRequest") ProyectoDTOs.CrearProyectoRequest request,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        Usuario usuario = getAuthenticatedUser(userDetails);
        if (usuario == null) {
            return "redirect:/auth/login";
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Revisa los datos del proyecto");
            redirectAttributes.addFlashAttribute("crearProyectoRequest", request);
            return "redirect:/dashboard";
        }

        ProyectoDTOs.ProyectoResponse proyecto = crearProyectoUseCase.crearProyecto(usuario.getId(), request);
        redirectAttributes.addFlashAttribute("success", "Proyecto creado correctamente");
        return "redirect:/proyectos/" + proyecto.getId() + "/tablero";
    }

    private Usuario getAuthenticatedUser(UsuarioUserDetailsAdapter userDetails) {
        return userDetails != null ? userDetails.getUsuario() : null;
    }
}
