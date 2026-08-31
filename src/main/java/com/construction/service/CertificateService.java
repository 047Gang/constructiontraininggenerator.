package com.construction.service;

import com.construction.model.TrainingCertificate;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class CertificateService {

    public byte[] generateCertificatePDF(String employeeName, String toolName, String trainer) throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PdfWriter writer = new PdfWriter(stream);
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);
        
        // Fonts
        PdfFont titleFont = PdfFontFactory.createFont(PdfEncodings.IDENTITY_H);
        PdfFont regularFont = PdfFontFactory.createFont(PdfEncodings.IDENTITY_H);
        
        // Title
        Paragraph title = new Paragraph("СЕРТИФИКАТ ОБУЧЕНИЯ")
                .setFont(titleFont)
                .setFontSize(28)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER);
        document.add(title);
        
        // Separator
        document.add(new LineSeparator());
        document.add(new Paragraph("\n"));
        
        // Certificate content
        Paragraph certNumber = new Paragraph()
                .setFont(regularFont)
                .setFontSize(11)
                .add("Номер сертификата: ").add(generateCertificateNumber());
        document.add(certNumber);
        document.add(new Paragraph("\n"));
        
        // Employee name
        Paragraph employeeInfo = new Paragraph()
                .setFont(regularFont)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER)
                .add("Данное свидетельство подтверждает, что сотрудник\n\n")
                .add(employeeName)
                .add("\n\n");
        document.add(employeeInfo);
        
        // Tool info
        Paragraph toolInfo = new Paragraph()
                .setFont(regularFont)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .add("Прошел(а) обучение по безопасной работе с инструментом:\n\n")
                .add(toolName)
                .add("\n\n");
        document.add(toolInfo);
        
        // Date
        Paragraph dateInfo = new Paragraph()
                .setFont(regularFont)
                .setFontSize(11)
                .setTextAlignment(TextAlignment.CENTER)
                .add("Дата обучения: ").add(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        document.add(dateInfo);
        document.add(new Paragraph("\n\n\n"));
        
        // Trainer signature
        Paragraph trainerInfo = new Paragraph()
                .setFont(regularFont)
                .setFontSize(11)
                .add("Инструктор: ___________________________\n\n")
                .add(trainer);
        document.add(trainerInfo);
        document.add(new Paragraph("\n\n"));
        
        // Footer
        Paragraph footer = new Paragraph()
                .setFont(regularFont)
                .setFontSize(9)
                .setTextAlignment(TextAlignment.CENTER)
                .add("Сертификат действителен в соответствии с нормативными документами")
                .add("\nОсновной закон о безопасности и охране труда на производстве");
        document.add(footer);
        
        document.close();
        return stream.toByteArray();
    }
    
    private String generateCertificateNumber() {
        return "CERT-" + System.currentTimeMillis();
    }
}
