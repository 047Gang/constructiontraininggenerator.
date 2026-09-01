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
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
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

    // Палитра фирменных акцентных цветов
    private final DeviceRgb[] primaryColors = {
        new DeviceRgb(37, 99, 235),  // Royal Blue
        new DeviceRgb(217, 119, 6),  // Safety Amber
        new DeviceRgb(15, 23, 42),   // Dark Slate
        new DeviceRgb(5, 150, 105),  // Emerald Green
        new DeviceRgb(124, 58, 237)  // Purple
    };

    public byte[] generateCertificatePdf(String employeeName, Tool tool, String instructorName, String lang) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        boolean isNorwegian = "no".equalsIgnoreCase(lang);

        try {
            PdfWriter writer = new PdfWriter(stream);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Настройка полей страницы
            document.setMargins(36, 36, 36, 36);

            // Загрузка шрифта UTF-8 (для норвежских æ, ø, å)
            InputStream fontStream = new ClassPathResource("fonts/Roboto-Regular.ttf").getInputStream();
            byte[] fontBytes = fontStream.readAllBytes();
            PdfFont font = PdfFontFactory.createFont(fontBytes, PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);

            // Выбор случайной темы оформления
            DeviceRgb themeColor = primaryColors[random.nextInt(primaryColors.length)];

            String certId = "CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

            // Локализация
            String title = isNorwegian ? "KURSBEVIS / SIKKERHETSOPPLÆRING" : "SAFETY TRAINING CERTIFICATE";
            String subTitle = isNorwegian ? "Dette dokumentet bekrefter at:" : "This document certifies that:";
            String passedText = isNorwegian 
                    ? "har gjennomført sikkerhetsopplæring for bruk av følgende verktøy:" 
                    : "has successfully completed safety training for the following tool:";
            String safetyRulesHeader = isNorwegian ? "Sikkerhetsinstrukser:" : "Safety Regulations:";
            String dateText = isNorwegian ? "Dato: " : "Date: ";
            String instructorText = isNorwegian ? "Instruktør: " : "Instructor: ";
            String signatureText = isNorwegian ? "Signatur: ________________" : "Signature: ________________";

            // 1. Динамическая цветная шапка-баннер
            Table headerTable = new Table(1).useAllAvailableWidth();
            Cell headerCell = new Cell()
                    .add(new Paragraph(title)
                            .setFont(font)
                            .setFontSize(18)
                            .setBold()
                            .setFontColor(ColorConstants.WHITE)
                            .setTextAlignment(TextAlignment.CENTER))
                    .setBackgroundColor(themeColor)
                    .setPadding(16);
            headerTable.addCell(headerCell);
            document.add(headerTable);

            // 2. Идентификатор документа
            document.add(new Paragraph("NO. " + certId)
                    .setFont(font)
                    .setFontSize(9)
                    .setFontColor(ColorConstants.GRAY)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginTop(8)
                    .setMarginBottom(20));

            // 3. Основной блок с именем
            document.add(new Paragraph(subTitle)
                    .setFont(font)
                    .setFontSize(11)
                    .setMarginBottom(10));

            document.add(new Paragraph(employeeName)
                    .setFont(font)
                    .setFontSize(22)
                    .setBold()
                    .setFontColor(themeColor)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20));

            document.add(new Paragraph(passedText)
                    .setFont(font)
                    .setFontSize(11)
                    .setMarginBottom(12));

            // 4. Карточка с выделенным названием инструмента
            Table toolTable = new Table(1).useAllAvailableWidth();
            Cell toolCell = new Cell()
                    .add(new Paragraph(tool.getName() + " (" + tool.getCategory() + ")")
                            .setFont(font)
                            .setFontSize(14)
                            .setBold()
                            .setTextAlignment(TextAlignment.CENTER))
                    .setBackgroundColor(new DeviceRgb(248, 250, 252))
                    .setBorder(new SolidBorder(themeColor, 1))
                    .setPadding(12);
            toolTable.addCell(toolCell);
            document.add(toolTable);

            // 5. Раздел правил безопасности
            document.add(new Paragraph(safetyRulesHeader)
                    .setFont(font)
                    .setFontSize(11)
                    .setBold()
                    .setMarginTop(25)
                    .setMarginBottom(6));

            document.add(new Paragraph(tool.getSafetyRules())
                    .setFont(font)
                    .setFontSize(10)
                    .setFontColor(ColorConstants.DARK_GRAY)
                    .setMarginBottom(35));

            // 6. Подвал (Дата, Инструктор и Подпись) в 2 колонки
            Table footerTable = new Table(UnitValue.createPercentArray(new float[]{50, 50})).useAllAvailableWidth();
            
            Cell leftCell = new Cell()
                    .add(new Paragraph(dateText + date).setFont(font).setFontSize(10))
                    .add(new Paragraph(instructorText + (instructorName != null && !instructorName.isBlank() ? instructorName : "________________")).setFont(font).setFontSize(10))
                    .setBorder(Border.NO_BORDER);

            Cell rightCell = new Cell()
                    .add(new Paragraph(signatureText).setFont(font).setFontSize(10).setTextAlignment(TextAlignment.RIGHT))
                    .setBorder(Border.NO_BORDER);

            footerTable.addCell(leftCell);
            footerTable.addCell(rightCell);
            document.add(footerTable);

            // 7. Нижняя декоративная полоса
            document.add(new Paragraph("")
                    .setBorderBottom(new SolidBorder(themeColor, 2))
                    .setMarginTop(20));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stream.toByteArray();
    }
}