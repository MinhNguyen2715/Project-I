import java.io.*;

public class PowerShellExecutor {

    // Run any PowerShell command and return its output
    public static String execute(String command) throws IOException {
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

    // Run test by technique ID
    public static String runAtomicTest(String techniqueId) throws IOException {
        return execute("Invoke-AtomicTest " + techniqueId);
    }

    // Check if prerequisites are satisfied for a given test
    public static String checkPrereqs(String techniqueId, String testName) throws IOException {
        String command = String.format("Invoke-AtomicTest %s -TestName \"%s\" -CheckPrereqs", techniqueId, testName);
        return execute(command);
    }

    public static String checkPrereqs(String techniqueId) throws IOException {
        String command = String.format("Invoke-AtomicTest %s -CheckPrereqs", techniqueId);
        return execute(command);
    }

    // Install missing prerequisites
    public static String installPrereqs(String techniqueId, String testName) throws IOException {
        String command = String.format("Invoke-AtomicTest %s -TestName \"%s\" -GetPrereqs | Invoke-Expression", techniqueId, testName);
        return execute(command);
    }

    public static String installPrereqs(String techniqueId) throws IOException {
        String command = String.format("Invoke-AtomicTest %s -GetPrereqs | Invoke-Expression", techniqueId);
        return execute(command);
    }

    // Run Get-PrereqCommand for technique
    public static String runPrereq(String techniqueId) throws IOException {
        return execute("Get-PrereqCommand " + techniqueId + " | Invoke-Expression");
    }
}