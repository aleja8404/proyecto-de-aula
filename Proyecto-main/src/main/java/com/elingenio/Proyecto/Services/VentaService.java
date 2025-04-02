package com.elingenio.Proyecto.Services;

import com.elingenio.Proyecto.Modelo.OrderItem;
// Asegúrate de que la clase se llame "Ventas" y no "ventas"
import com.elingenio.Proyecto.Modelo.Venta;

import com.elingenio.Proyecto.Repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;


@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    public List<Venta> obtenerTodas() {
        return ventaRepository.findAll();
    }

    public Optional<Venta> obtenerPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public Venta registrarVenta(Venta venta) {
        return ventaRepository.save(venta);
    }
    public List<Venta> findByVendedorId(Long vendedorId) {
        return ventaRepository.findByVendedorIdVendedor(vendedorId);
    }

public List<OrderItem> findOrderItemsByVentaId(Long ventaId) {
        return ventaRepository.findOrderItemsByVentaId(ventaId);
    }

    public void eliminarVenta(Long id) {
        ventaRepository.deleteById(id);
    }






    
}
