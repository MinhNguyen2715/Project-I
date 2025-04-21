import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Logger {
    private static final String LOG_FILE_PATH = "logs/atomic-log.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static Boolean append = true;

    public static void setAppend(Boolean value) {
        append = value;

    }

    public static void log(String techniqueId, String testGuid, String command, String result) {
        try {
            AtomicTestLog logEntry = new AtomicTestLog();

            ZonedDateTime now = ZonedDateTime.now();
            logEntry.setExecutionTimeUtc(Instant.now().toString());
            logEntry.setExecutionTimeLocal(now.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
            logEntry.setTechnique(techniqueId);
            logEntry.setTestNumber(testGuid);
            logEntry.setHostname(InetAddress.getLocalHost().getHostName());
            logEntry.setIpAddress(InetAddress.getLocalHost().getHostAddress());
            logEntry.setUsername(System.getProperty("user.name"));
            logEntry.setGuid(UUID.randomUUID().toString());
            logEntry.setTag("Scheduled");
            logEntry.setCustomTag("APT29");
            logEntry.setCommand(command);
            logEntry.setResult(result);

            List<AtomicTestLog> logs = new ArrayList<>();

            File logFile = new File(LOG_FILE_PATH);
            if (append && logFile.exists()) {
                // Đọc danh sách log cũ
                try (Reader reader = new FileReader(logFile)) {
                    Type listType = new TypeToken<ArrayList<AtomicTestLog>>(){}.getType();
                    List<AtomicTestLog> existingLogs = gson.fromJson(reader, listType);
                    if (existingLogs != null) {
                        logs.addAll(existingLogs);
                    }
                } catch (Exception e) {
                    System.err.println("Lỗi đọc log cũ: " + e.getMessage());
                }
            }

            // Thêm log mới
            logs.add(logEntry);

            // Ghi lại toàn bộ
            try (Writer writer = new FileWriter(logFile)) {
                gson.toJson(logs, writer);
            }

        } catch (IOException e) {
            System.err.println("Lỗi ghi log: " + e.getMessage());
        }
    }
}
