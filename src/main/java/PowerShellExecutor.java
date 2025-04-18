import java.io.*;

public class PowerShellExecutor {

    public String execute(String psScriptContent) throws IOException {
        // Write the script to a temporary .ps1 file
        File tempScript = File.createTempFile("atomic_script_", ".ps1");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempScript))) {
            writer.write(psScriptContent);
        }

        // Run the script using pwsh -File
        ProcessBuilder builder = new ProcessBuilder("pwsh", "-File", tempScript.getAbsolutePath());
        builder.redirectErrorStream(true);
        Process process = builder.start();

        // Capture the full output
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        // Wait for execution to complete
        try {
            int exitCode = process.waitFor();
            output.append("\nExit Code: ").append(exitCode);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Delete the temp script file after execution
        tempScript.delete();

        return output.toString();
    }
}
