package com.elingenio.Proyecto.Controller;

import com.elingenio.Proyecto.Modelo.Venta;
import com.elingenio.Proyecto.Services.ClienteService;
import com.elingenio.Proyecto.Services.VendedorService;
import com.elingenio.Proyecto.Services.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private VendedorService vendedorService;

    @GetMapping
    public String listarVentas(Model model) {
        model.addAttribute("ventas", ventaService.obtenerTodas());
        return "vistas/ventas/lista";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrear(Model model) {
        model.addAttribute("venta", new Venta());
        model.addAttribute("clientes", clienteService.obtenerTodos());
        model.addAttribute("vendedores", vendedorService.obtenerTodos());
        return "vistas/ventas/formulario";
    }

    @PostMapping
    public String guardarVenta(@ModelAttribute("venta") Venta venta) {
        ventaService.registrarVenta(venta);
        return "redirect:/ventas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Optional<Venta> venta = ventaService.obtenerPorId(id);
        if (venta.isPresent()) {
            model.addAttribute("venta", venta.get());
            model.addAttribute("clientes", clienteService.obtenerTodos());
            model.addAttribute("vendedores", vendedorService.obtenerTodos());
            return "vistas/ventas/formulario";
        } else {
            return "redirect:/ventas";
        }
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarVenta(@PathVariable Long id, @ModelAttribute("venta") Venta venta) {
        venta.setIdVenta(id);
        ventaService.registrarVenta(venta);
        return "redirect:/ventas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return "redirect:/ventas";
    }
}