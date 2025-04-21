import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

public class LogExporter {

    public static void exportJsonToExcel(String jsonPath, String excelPath) {
        try {
            // Đọc JSON thành danh sách log
            Gson gson = new Gson();
            Reader reader = new FileReader(jsonPath);
            Type listType = new TypeToken<List<AtomicTestLog>>(){}.getType();
            List<AtomicTestLog> logs = gson.fromJson(reader, listType);

            // Tạo workbook và sheet
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Atomic Logs");

            // Ghi tiêu đề
            String[] headers = {
                    "ExecutionTimeUtc", "ExecutionTimeLocal", "Technique", "TestNumber", "Hostname",
                    "IpAddress", "Username", "Guid", "Tag", "CustomTag", "Command", "Result"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Ghi nội dung log
            for (int i = 0; i < logs.size(); i++) {
                AtomicTestLog log = logs.get(i);
                Row row = sheet.createRow(i + 1);

                row.createCell(0).setCellValue(log.getExecutionTimeUtc());
                row.createCell(1).setCellValue(log.getExecutionTimeLocal());
                row.createCell(2).setCellValue(log.getTechnique());
                row.createCell(3).setCellValue(log.getTestNumber());
                row.createCell(4).setCellValue(log.getHostname());
                row.createCell(5).setCellValue(log.getIpAddress());
                row.createCell(6).setCellValue(log.getUsername());
                row.createCell(7).setCellValue(log.getGuid());
                row.createCell(8).setCellValue(log.getTag());
                row.createCell(9).setCellValue(log.getCustomTag());
                row.createCell(10).setCellValue(log.getCommand());
                row.createCell(11).setCellValue(log.getResult());
            }

            // Tự động căn chỉnh cột
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            // Ghi ra file Excel
            FileOutputStream fileOut = new FileOutputStream(excelPath);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            System.out.println("Xuất log ra Excel thành công: " + excelPath);

        } catch (IOException e) {
            System.err.println("Lỗi xuất file Excel: " + e.getMessage());
        }
    }
}
