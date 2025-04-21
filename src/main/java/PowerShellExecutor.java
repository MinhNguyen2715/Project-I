import java.io.*;

public class PowerShellExecutor {
    private AtomicTestManager atomicTestManager;
    public PowerShellExecutor(AtomicTestManager atomicTestManager) {
        this.atomicTestManager = atomicTestManager;
    }
    public String execute(String psScriptContent) throws IOException {
        File tempScript = File.createTempFile("atomic_script_", ".ps1");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempScript))) {

            writer.write(atomicTestManager.getImportModule());
            writer.newLine();
            writer.write(psScriptContent);

        }

        ProcessBuilder builder = new ProcessBuilder("pwsh", "-File", tempScript.getAbsolutePath());
        builder.redirectErrorStream(true);
        Process process = builder.start();


        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
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

        // Delete the temp script file after execution
        tempScript.delete();

        return output.toString();
    }
}