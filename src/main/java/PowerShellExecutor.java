import java.io.*;

public class PowerShellExecutor {
    // Run any PowerShell command and return its output
    public String execute(String command) throws IOException {
        ProcessBuilder builder = new ProcessBuilder("pwsh", "-Command", command);
        builder.redirectErrorStream(true);
        Process process = builder.start();

        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        try {
            int exitCode = process.waitFor();
            output.append("\nExit Code: ").append(exitCode);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return output.toString();
    }

}