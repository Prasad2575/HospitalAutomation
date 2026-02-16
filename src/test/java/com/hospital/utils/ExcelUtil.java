package com.hospital.utils;

import com.hospital.model.HospitalInfo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    // ✅ NEW: Create rows automatically (no row creation in test)
    public static List<String[]> buildHospitalRows(List<HospitalInfo> hospitals) {

        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"S.No", "Hospital Name", "Beds", "Rating", "Phone", "URL"});

        for (HospitalInfo h : hospitals) {
            rows.add(new String[]{
                    String.valueOf(h.getsNo()),
                    h.getName(),
                    String.valueOf(h.getBeds()),
                    String.valueOf(h.getRating()),
                    h.getPhone(),
                    h.getUrl()
            });
        }
        return rows;
    }

    public static void writeHospitalData(String filePath, String sheetName, List<String[]> rows) {
        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet(sheetName);

            // Header style (bold)
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);

            // Create rows
            for (int r = 0; r < rows.size(); r++) {
                Row row = sheet.createRow(r);
                String[] cols = rows.get(r);

                for (int c = 0; c < cols.length; c++) {
                    Cell cell = row.createCell(c);
                    cell.setCellValue(cols[c]);

                    if (r == 0) {
                        cell.setCellStyle(headerStyle);
                    }
                }
            }

            // Auto-size columns
            int colCount = rows.get(0).length;
            for (int c = 0; c < colCount; c++) {
                sheet.autoSizeColumn(c);
            }

            // Ensure folder exists
            Path parent = Path.of(filePath).getParent();
            if (parent != null) Files.createDirectories(parent);

            // Write file
            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }

            System.out.println("✅ Excel saved: " + filePath);

        } catch (Exception e) {
            throw new RuntimeException("Failed to write Excel: " + e.getMessage(), e);
        }
    }
}