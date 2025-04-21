import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Logger {
    private static final String LOG_FILE_PATH = "logs/atomic-log.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static Boolean append = true;

    public static void setAppend(Boolean append) {
        Logger.append = append;
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

            String json = gson.toJson(logEntry);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, append))) {
                writer.write(json);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Logging error: " + e.getMessage());
        }
    }
}
