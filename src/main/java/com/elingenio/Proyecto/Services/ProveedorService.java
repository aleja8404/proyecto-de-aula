package com.elingenio.Proyecto.Services;

import com.elingenio.Proyecto.Modelo.Proveedor;
import com.elingenio.Proyecto.Modelo.Producto;
import com.elingenio.Proyecto.Repository.ProveedorRepository;
import com.elingenio.Proyecto.Repository.ProductoRepository; // Add this
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private ProductoRepository productoRepository; // Add this

    public List<Proveedor> obtenerTodos() {
        return proveedorRepository.findAll();
    }

    public Optional<Proveedor> obtenerPorId(Long id) {
        return proveedorRepository.findById(id);
    }

    public Optional<Proveedor> findByCorreoElectronico(String email) {
        return proveedorRepository.findByCorreoElectronico(email);
    }

    public Proveedor guardarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public void eliminarProveedor(Long id) {
        proveedorRepository.deleteById(id);
    }

    public List<Producto> obtenerProductosPorProveedor(Long proveedorId) {
        return productoRepository.findByProveedorIdProveedor(proveedorId);
    }
}