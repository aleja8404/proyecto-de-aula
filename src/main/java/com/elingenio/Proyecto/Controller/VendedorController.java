package com.elingenio.Proyecto.Controller;

import com.elingenio.Proyecto.Modelo.Vendedor;
import com.elingenio.Proyecto.Services.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/vendedores")
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    @GetMapping
    public String listarVendedores(Model model) {
        List<Vendedor> vendedores = vendedorService.obtenerTodos();
        model.addAttribute("vendedores", vendedores);
        return "vistas/vendedores/lista"; // Vista Thymeleaf para listar vendedores
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoVendedor(Model model) {
        model.addAttribute("vendedor", new Vendedor());
        return "vistas/vendedores/formulario"; // Vista Thymeleaf para crear/editar un vendedor
    }

    @PostMapping
    public String guardarVendedor(@ModelAttribute("vendedor") Vendedor vendedor) {
        vendedorService.guardarVendedor(vendedor);
        return "redirect:/vendedores";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarVendedor(@PathVariable Long id, Model model) {
        Vendedor vendedor = vendedorService.obtenerPorId(id).orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("vendedor", vendedor);
        return "vendedores/formulario"; // Reutilizamos la misma vista para editar
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarVendedor(@PathVariable Long id) {
        vendedorService.eliminarVendedor(id);
        return "redirect:/vendedores";
    }
}
