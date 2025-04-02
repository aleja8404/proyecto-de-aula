package com.elingenio.Proyecto.Controller;

import com.elingenio.Proyecto.Modelo.OrderItem;
import com.elingenio.Proyecto.Modelo.Producto;
import com.elingenio.Proyecto.Modelo.Venta;
import com.elingenio.Proyecto.Services.CompraService;
import com.elingenio.Proyecto.Services.OrderItemService;
import com.elingenio.Proyecto.Services.ProductoService; // Add this
import com.elingenio.Proyecto.Services.VentaService;   // Add this
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order-items")
public class OrderItemController {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductoService productoService; // Add this

    @Autowired
    private VentaService ventaService;    


    @Autowired
    private
    CompraService
    compraService;

    @GetMapping
    public String listarOrderItems(Model model) {
        List<OrderItem> orderItems = orderItemService.obtenerTodos();
        model.addAttribute("orderItems", orderItems);
        model.addAttribute("compras", compraService.obtenerTodas());
        return "vistas/order-items/list";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("orderItem", new OrderItem());
        model.addAttribute("productos", productoService.obtenerTodos());
        model.addAttribute("ventas", ventaService.obtenerTodas());
        return "vistas/order-items/form";
    }

    @PostMapping
    public String guardarOrderItem(@ModelAttribute("orderItem") OrderItem orderItem) {
        System.out.println("Saving OrderItem: " + orderItem);
        orderItemService.guardarOrderItem(orderItem);
        return "redirect:/order-items";
    }
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        OrderItem orderItem = orderItemService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("orderItem", orderItem);
        model.addAttribute("productos", productoService.obtenerTodos());
        model.addAttribute("ventas", ventaService.obtenerTodas());
        return "vistas/order-items/form";
    }
    @PostMapping("/{id}")
    public String actualizarOrderItem(@PathVariable Long id, @ModelAttribute("orderItem") OrderItem orderItem) {
        orderItem.setIdPedidoArticulo(id);
        orderItemService.guardarOrderItem(orderItem);
        return "redirect:/order-items";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarOrderItem(@PathVariable Long id) {
        orderItemService.eliminarOrderItem(id);
        return "redirect:/order-items";
    }
}