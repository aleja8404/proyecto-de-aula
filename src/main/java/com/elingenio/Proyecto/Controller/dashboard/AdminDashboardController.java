package com.elingenio.Proyecto.Controller.dashboard;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class AdminDashboardController {

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("titulo", "Panel de Administración");
        return "admin/dashboard"; // Carga el archivo src/main/resources/templates/admin/dashboard.html
    }
}
