package com.elingenio.Proyecto.Services;

import com.elingenio.Proyecto.Modelo.Vendedor;
import com.elingenio.Proyecto.Repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    public List<Vendedor> obtenerTodos() {
        return vendedorRepository.findAll();
    }

    public Optional<Vendedor> obtenerPorId(Long id) {
        return vendedorRepository.findById(id);
    }
    public Optional<Vendedor> findByCorreoElectronico(String correoElectronico) {
        return vendedorRepository.findByCorreoElectronico(correoElectronico);
    }


    public List<Vendedor> findByVendedorId(Long idVendedor) {
        return vendedorRepository.findByIdVendedor(idVendedor); // Assuming this is needed for other operations
    }

    public Vendedor guardarVendedor(Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    public void eliminarVendedor(Long id) {
        vendedorRepository.deleteById(id);
    }
}
