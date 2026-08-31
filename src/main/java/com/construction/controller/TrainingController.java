package com.construction.controller;

import com.construction.model.Tool;
import com.construction.service.CertificateService;
import com.construction.service.ToolService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TrainingController {

    private final ToolService toolService;
    private final CertificateService certificateService;

    public TrainingController(ToolService toolService, CertificateService certificateService) {
        this.toolService = toolService;
        this.certificateService = certificateService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tools", toolService.getAllTools());
        return "index";
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateCertificate(
            @RequestParam("employeeName") String employeeName,
            @RequestParam("toolId") String toolId,
            @RequestParam(value = "instructorName", required = false) String instructorName) {

        Tool tool = toolService.getToolById(toolId);
        if (tool == null) {
            return ResponseEntity.badRequest().build();
        }

        byte[] pdfBytes = certificateService.generateCertificatePdf(employeeName, tool, instructorName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=certificate.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}