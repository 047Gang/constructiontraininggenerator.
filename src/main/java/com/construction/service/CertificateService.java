package com.construction.service;

import com.construction.model.Tool;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class CertificateService {

    public byte[] generateCertificatePdf(String employeeName, Tool tool, String instructorName) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(stream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Читаем TTF-шрифт из resources и задаем кодировку IDENTITY_H для UTF-8
            InputStream fontStream = new ClassPathResource("fonts/Roboto-Regular.ttf").getInputStream();
            byte[] fontBytes = fontStream.readAllBytes();
            PdfFont font = PdfFontFactory.createFont(fontBytes, PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

            String certId = "CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

            document.add(new Paragraph("СЕРТИФИКАТ ИНСТРУКТАЖА")
                    .setFont(font).setFontSize(22).setBold().setTextAlignment(TextAlignment.CENTER));
            
            document.add(new Paragraph("№ " + certId)
                    .setFont(font).setFontSize(10).setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\nНастоящий документ подтверждает, что:")
                    .setFont(font).setFontSize(12));

            document.add(new Paragraph(employeeName)
                    .setFont(font).setFontSize(18).setBold().setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("прошел(а) инструктаж по технике безопасности при работе с инструментом:")
                    .setFont(font).setFontSize(12));

            document.add(new Paragraph(tool.getName() + " (" + tool.getCategory() + ")")
                    .setFont(font).setFontSize(14).setBold());

            document.add(new Paragraph("\nПравила безопасности:")
                    .setFont(font).setFontSize(12).setBold());
            document.add(new Paragraph(tool.getSafetyRules())
                    .setFont(font).setFontSize(10));

            document.add(new Paragraph("\nДата: " + date)
                    .setFont(font).setFontSize(11));
            document.add(new Paragraph("Инструктор: " + (instructorName != null && !instructorName.isBlank() ? instructorName : "________________"))
                    .setFont(font).setFontSize(11));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stream.toByteArray();
    }
}