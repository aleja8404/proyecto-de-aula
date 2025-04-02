package com.elingenio.Proyecto.Services;

import com.elingenio.Proyecto.Modelo.Cliente;
import com.elingenio.Proyecto.Modelo.OrderItem;
import com.elingenio.Proyecto.Modelo.Vendedor;
import com.elingenio.Proyecto.Modelo.Venta;
import com.elingenio.Proyecto.Repository.OrderItemRepository;
import com.elingenio.Proyecto.Repository.VentaRepository;
import com.elingenio.Proyecto.Repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private VendedorRepository vendedorRepository;

    public List<OrderItem> obtenerTodos() {
        return orderItemRepository.findAll();
    }

    public Optional<OrderItem> obtenerPorId(Long id) {
        return orderItemRepository.findById(id);
    }

    @Transactional
    public void guardarOrdenes(Cliente cliente, List<OrderItem> items) {
        Venta venta = new Venta();
        venta.setCliente(cliente);
        // Set default vendedor
        Vendedor defaultVendedor = vendedorRepository.findByCorreoElectronico("sistema@ferreteria.com")
            .orElseThrow(() -> new RuntimeException("Default vendedor not found"));
        venta.setVendedor(defaultVendedor);
        venta.setFechaDelPedido(LocalDateTime.now());
        venta.setTotal(items.stream()
            .map(item -> item.getPrecio().multiply(new java.math.BigDecimal(item.getCantidad())))
            .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));
        venta.setEstado("PENDIENTE");
        ventaRepository.save(venta);
        items.forEach(item -> item.setVenta(venta));
        orderItemRepository.saveAll(items);
    }

    public OrderItem guardarOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> obtenerPorCliente(Long clienteId) {
        return orderItemRepository.findByVentaClienteIdCliente(clienteId);
    }

    public void eliminarOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}