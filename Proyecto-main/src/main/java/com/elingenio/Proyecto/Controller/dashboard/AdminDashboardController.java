package com.elingenio.Proyecto.Controller.dashboard;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Agregar atributos necesarios para el dashboard de administrador
        return "admin/dashboard"; // Vista Thymeleaf para el dashboard del administrador
    }
}