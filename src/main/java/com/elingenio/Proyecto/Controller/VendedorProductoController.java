package com.elingenio.Proyecto.Controller;

import com.elingenio.Proyecto.Modelo.VendedorProducto;
import com.elingenio.Proyecto.Services.VendedorProductoService;
import com.elingenio.Proyecto.Services.VendedorService;
import com.elingenio.Proyecto.Services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/vendedor-producto")
public class VendedorProductoController {

    @Autowired
    private VendedorProductoService vendedorProductoService;

    @Autowired
    private VendedorService vendedorService;

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String listarVendedorProductos(Model model) {
        model.addAttribute("vendedorProductos", vendedorProductoService.obtenerTodos());
        return "vistas/vendedor-producto/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("vendedorProducto", new VendedorProducto());
        model.addAttribute("vendedores", vendedorService.obtenerTodos());
        model.addAttribute("productos", productoService.obtenerTodos());
        return "vistas/vendedor-producto/formulario";
    }

    @PostMapping
    public String guardarVendedorProducto(@ModelAttribute VendedorProducto vendedorProducto) {
        vendedorProductoService.guardarVendedorProducto(vendedorProducto);
        return "redirect:/vendedor-producto";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<VendedorProducto> vendedorProducto = vendedorProductoService.obtenerPorId(id);
        if (vendedorProducto.isPresent()) {
            model.addAttribute("vendedorProducto", vendedorProducto.get());
            model.addAttribute("vendedores", vendedorService.obtenerTodos());
            model.addAttribute("productos", productoService.obtenerTodos());
            return "vistas/vendedor-producto/formulario"; // Fixed path
        } else {
            return "redirect:/vendedor-producto";
        }
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarVendedorProducto(@PathVariable Long id, @ModelAttribute VendedorProducto vendedorProducto) {
        vendedorProducto.setIdVendedorProducto(id); // Set ID explicitly
        vendedorProductoService.guardarVendedorProducto(vendedorProducto); // Use guardar instead of actualizar
        return "redirect:/vendedor-producto";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarVendedorProducto(@PathVariable Long id) {
        vendedorProductoService.eliminarVendedorProducto(id);
        return "redirect:/vendedor-producto";
    }
}