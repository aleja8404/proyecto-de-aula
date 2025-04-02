package com.elingenio.Proyecto.Controller;

import com.elingenio.Proyecto.Modelo.Producto;
import com.elingenio.Proyecto.Services.ProductoService;
import com.elingenio.Proyecto.Services.ProveedorService; // Add this
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProveedorService proveedorService; // Add this

    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.obtenerTodos());
        return "vistas/productos/lista";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoProducto(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("proveedores", proveedorService.obtenerTodos()); // Add proveedores
        return "vistas/productos/formulario";
    }

    @PostMapping
    public String guardarProducto(@ModelAttribute Producto producto) {
        productoService.guardarProducto(producto);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarProducto(@PathVariable Long id, Model model) {
        Optional<Producto> producto = productoService.obtenerPorId(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            model.addAttribute("proveedores", proveedorService.obtenerTodos()); // Add proveedores
            return "vistas/productos/formulario"; // Fixed path
        } else {
            return "redirect:/productos";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return "redirect:/productos";
    }
}