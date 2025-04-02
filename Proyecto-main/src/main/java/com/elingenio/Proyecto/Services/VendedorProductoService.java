package com.elingenio.Proyecto.Services;

import com.elingenio.Proyecto.Modelo.VendedorProducto;
import com.elingenio.Proyecto.Repository.VendedorProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendedorProductoService {

    @Autowired
    private VendedorProductoRepository vendedorProductoRepository;

    // Obtener todos los registros
    public List<VendedorProducto> obtenerTodos() {
        return vendedorProductoRepository.findAll();
    }

    // Guardar un nuevo registro
    public VendedorProducto guardarVendedorProducto(VendedorProducto vendedorProducto) {
        return vendedorProductoRepository.save(vendedorProducto);
    }

    // Eliminar un registro por ID
    public void eliminarVendedorProducto(Long id) {
        vendedorProductoRepository.deleteById(id);
    }

    // Obtener un registro por ID
    public Optional<VendedorProducto> obtenerPorId(Long id) {
        return vendedorProductoRepository.findById(id);
    }

    // Actualizar un registro existente
    public VendedorProducto actualizarVendedorProducto(Long id, VendedorProducto vendedorProductoActualizado) {
        return vendedorProductoRepository.findById(id).map(vendedorProducto -> {
            vendedorProducto.setVendedor(vendedorProductoActualizado.getVendedor());
            vendedorProducto.setProducto(vendedorProductoActualizado.getProducto());
            vendedorProducto.setCantidad(vendedorProductoActualizado.getCantidad()); // Added
            vendedorProducto.setPrecio(vendedorProductoActualizado.getPrecio());     // Added
            return vendedorProductoRepository.save(vendedorProducto);
        }).orElseThrow(() -> new RuntimeException("VendedorProducto no encontrado con ID: " + id));
    }

    // Obtener productos asignados por ID de vendedor
    public List<VendedorProducto> findByVendedorId(Long vendedorId) {
        return vendedorProductoRepository.findByVendedorIdVendedor(vendedorId);
    }
}