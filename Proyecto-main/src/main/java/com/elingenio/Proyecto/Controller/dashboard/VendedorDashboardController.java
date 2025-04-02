package com.elingenio.Proyecto.Controller.dashboard;

import com.elingenio.Proyecto.Modelo.Vendedor;
import com.elingenio.Proyecto.Services.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/vendedor")
@PreAuthorize("hasRole('VENDEDOR')")
public class VendedorDashboardController {

    @Autowired
    private VendedorService vendedorService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, Authentication authentication) {
        String email = authentication.getName();
        Optional<Vendedor> vendedorOpt = vendedorService.findByCorreoElectronico(email);
        if (vendedorOpt.isEmpty()) {
            return "redirect:/vendedor/setup";
        }
        model.addAttribute("vendedor", vendedorOpt.get());
        // Aquí deberías agregar vendedorProductos, ventas y compras si ya tienes los servicios
        return "vendedor/dashboard";
    }

    @GetMapping("/setup")
    public String mostrarFormularioSetup(Model model, Authentication authentication) {
        String email = authentication.getName();
        Optional<Vendedor> vendedorOpt = vendedorService.findByCorreoElectronico(email);
        if (vendedorOpt.isPresent()) {
            return "redirect:/vendedor/dashboard";
        }
        Vendedor vendedor = new Vendedor();
        vendedor.setCorreoElectronico(email);
        model.addAttribute("vendedor", vendedor);
        return "vendedor/setup";
    }

    @PostMapping("/setup")
    public String guardarSetup(@ModelAttribute("vendedor") Vendedor vendedor, Authentication authentication) {
        String email = authentication.getName();
        vendedor.setCorreoElectronico(email); // Asegura que el correo no se modifique
        vendedorService.guardarVendedor(vendedor);
        return "redirect:/vendedor/dashboard";
    }
}