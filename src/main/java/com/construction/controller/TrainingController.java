package com.construction.controller;

import com.construction.model.Tool;
import com.construction.service.CertificateService;
import com.construction.service.ToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class TrainingController {

    @Autowired
    private ToolService toolService;

    @Autowired
    private CertificateService certificateService;

    @GetMapping
    public String index(Model model) {
        List<Tool> tools = toolService.getAllTools();
        model.addAttribute("tools", tools);
        return "index";
    }

    @PostMapping("/generate-certificate")
    public ResponseEntity<byte[]> generateCertificate(
            @RequestParam String employeeName,
            @RequestParam String toolName,
            @RequestParam(defaultValue = "Инструктор") String trainer) {
        try {
            byte[] certificatePDF = certificateService.generateCertificatePDF(employeeName, toolName, trainer);
            
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=certificate_" + employeeName.replaceAll("\\s+", "_") + ".pdf");
            headers.add("Content-Type", "application/pdf");
            
            return new ResponseEntity<>(certificatePDF, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
