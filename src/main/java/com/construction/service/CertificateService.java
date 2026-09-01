package com.construction.service;

import com.construction.model.Tool;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

@Service
public class CertificateService {

    private final Random random = new Random();

    // Палитра случайных тем оформлений
    private final DeviceRgb[] primaryColors = {
        new DeviceRgb(37, 99, 235),  // Blue
        new DeviceRgb(217, 119, 6),  // Amber / Orange
        new DeviceRgb(15, 23, 42),   // Dark Slate
        new DeviceRgb(5, 150, 105),  // Emerald Green
        new DeviceRgb(79, 70, 229)   // Indigo
    };

    public byte[] generateCertificatePdf(String employeeName, Tool tool, String instructorName, String lang) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        boolean isNorwegian = "no".equalsIgnoreCase(lang);

        try {
            PdfWriter writer = new PdfWriter(stream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Загружаем шрифт с поддержкой UTF-8 (для æ, ø, å)
            InputStream fontStream = new ClassPathResource("fonts/Roboto-Regular.ttf").getInputStream();
            byte[] fontBytes = fontStream.readAllBytes();
            PdfFont font = PdfFontFactory.createFont(fontBytes, PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

            // Выбираем случайную цветовую тему
            DeviceRgb primaryColor = primaryColors[random.nextInt(primaryColors.length)];

            String certId = "CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

            // Переводы текстов
            String title = isNorwegian ? "KURSBEVIS / SIKKERHETSOPPLÆRING" : "SAFETY TRAINING CERTIFICATE";
            String subTitle = isNorwegian ? "Dette dokumentet bekrefter at:" : "This document certifies that:";
            String passedText = isNorwegian 
                    ? "har gjennomført sikkerhetsopplæring for bruk av følgende verktøy:" 
                    : "has successfully completed safety training for the following tool:";
            String safetyRulesHeader = isNorwegian ? "Sikkerhetsinstrukser:" : "Safety Regulations:";
            String dateText = isNorwegian ? "Dato: " : "Date: ";
            String instructorText = isNorwegian ? "Instruktør: " : "Instructor: ";

            // Разделительная рамка сверху для динамического дизайна
            Paragraph borderTop = new Paragraph("")
                    .setBorderBottom(new SolidBorder(primaryColor, random.nextBoolean() ? 3 : 1))
                    .setMarginBottom(20);
            document.add(borderTop);

            // Заголовок
            document.add(new Paragraph(title)
                    .setFont(font)
                    .setFontSize(20)
                    .setBold()
                    .setFontColor(primaryColor)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("NO. " + certId)
                    .setFont(font)
                    .setFontSize(9)
                    .setFontColor(ColorConstants.GRAY)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(25));

            document.add(new Paragraph(subTitle)
                    .setFont(font)
                    .setFontSize(11));

            // Имя сотрудника
            document.add(new Paragraph(employeeName)
                    .setFont(font)
                    .setFontSize(18)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(10)
                    .setMarginBottom(15));

            document.add(new Paragraph(passedText)
                    .setFont(font)
                    .setFontSize(11));

            // Название инструмента
            document.add(new Paragraph(tool.getName() + " (" + tool.getCategory() + ")")
                    .setFont(font)
                    .setFontSize(14)
                    .setBold()
                    .setFontColor(primaryColor)
                    .setMarginBottom(15));

            // Инструкции по безопасности
            document.add(new Paragraph(safetyRulesHeader)
                    .setFont(font)
                    .setFontSize(11)
                    .setBold());
            document.add(new Paragraph(tool.getSafetyRules())
                    .setFont(font)
                    .setFontSize(10)
                    .setFontColor(ColorConstants.DARK_GRAY)
                    .setMarginBottom(30));

            // Подпись и дата
            document.add(new Paragraph(dateText + date)
                    .setFont(font)
                    .setFontSize(10));
            document.add(new Paragraph(instructorText + (instructorName != null && !instructorName.isBlank() ? instructorName : "________________"))
                    .setFont(font)
                    .setFontSize(10));

            // Нижняя рамка
            document.add(new Paragraph("")
                    .setBorderBottom(new SolidBorder(primaryColor, 1))
                    .setMarginTop(20));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stream.toByteArray();
    }
}