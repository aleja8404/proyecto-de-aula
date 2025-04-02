package com.elingenio.Proyecto.Controller;

import com.elingenio.Proyecto.Modelo.Usuario;
import com.elingenio.Proyecto.Services.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    // Mostrar todos los usuarios con paginación y búsqueda
    @GetMapping
    public String listarYFormulario(
            @RequestParam Optional<Long> id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String search,
            Model model) {
        // Formulario de creación/edición
        if (id.isPresent()) {
            Optional<Usuario> usuario = usuarioServicio.obtenerPorId(id.get());
            usuario.ifPresent(value -> model.addAttribute("usuario", value));
        } else {
            model.addAttribute("usuario", new Usuario());
        }

        // Paginación y búsqueda de usuarios
        Pageable pageable = PageRequest.of(page, size);
        Page<Usuario> usuarios;
        if (search != null && !search.isEmpty()) {
            usuarios = usuarioServicio.buscarPorNombreOEmail(search, pageable);
        } else {
            usuarios = usuarioServicio.obtenerTodosPaginados(pageable);
        }

        model.addAttribute("usuarios", usuarios.getContent());
        model.addAttribute("usuariosPage", usuarios);
        model.addAttribute("search", search);

        return "vistas/usuario/usuarios";
    }

    // Actualizar un usuario por su id
    @PostMapping("/actualizar/{id}")
    public String actualizarUsuario(@PathVariable Long id, @ModelAttribute Usuario usuario, Model model) {
        try {
            usuarioServicio.actualizar(id, usuario);
            return "redirect:/usuarios";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("usuario", usuario);
            model.addAttribute("usuarios", usuarioServicio.obtenerTodosPaginados(PageRequest.of(0, 5)).getContent());
            return "vistas/usuario/usuarios";
        }
    }

    // Crear un usuario con rol
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario,
                                 @RequestParam(required = false) String role,
                                 Model model) {
        try {
            String roleToAssign = (role != null && !role.isEmpty()) ? role : "ROLE_CLIENTE";
            usuarioServicio.guardar(usuario, roleToAssign);
            return "redirect:/usuarios";
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error", "El correo electrónico ya está registrado.");
            model.addAttribute("usuario", usuario);
            model.addAttribute("usuarios", usuarioServicio.obtenerTodosPaginados(PageRequest.of(0, 5)).getContent());
            return "vistas/usuario/usuarios";
        } catch (Exception e) {
            model.addAttribute("error", "Error al guardar el usuario: " + e.getMessage());
            model.addAttribute("usuario", usuario);
            model.addAttribute("usuarios", usuarioServicio.obtenerTodosPaginados(PageRequest.of(0, 5)).getContent());
            return "vistas/usuario/usuarios";
        }
    }

    // Eliminar un usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioServicio.eliminar(id);
        return "redirect:/usuarios";
    }
}