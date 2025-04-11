package com.elingenio.Proyecto.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.elingenio.Proyecto.Modelo.CustomerData;
import com.elingenio.Proyecto.Services.CustomerClassificationService;
import com.elingenio.Proyecto.Services.dto.PredictionCustomer;

@Controller
@RequestMapping("/clasificacion")
public class CustomerClassificationController {

    private final CustomerClassificationService classificationService;

    public CustomerClassificationController(CustomerClassificationService classificationService) {
        this.classificationService = classificationService;
    }

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("customerData", new CustomerData());
        return "CustomerClassification";
    }

    @PostMapping("/clasificar")
    public String clasificar(@ModelAttribute CustomerData datos, Model model) {
        try {
            PredictionCustomer prediction = classificationService.classifyAndSave(datos);

            model.addAttribute("clasificacion", prediction.getPrediction());
            model.addAttribute("confianza", prediction.getConfidence());
            model.addAttribute("customerData", datos);
            model.addAttribute("showResult", true);

            return "CustomerClassification";
        } catch (Exception e) {
            classificationService.logError("Error al realizar la clasificación", e);
            model.addAttribute("error", "Error interno al procesar la clasificación");
            model.addAttribute("customerData", datos);
            return "CustomerClassification";
        }
    }

    @GetMapping("/lista")
    public String listPredictions(Model model) {
        model.addAttribute("predictions", classificationService.getAllPredictions());
        return "CustomerClassificationList";
    }
}