import java.io.IOException;

public class AtomicTestManager {
    private final PowerShellExecutor powerShellExecutor;
    private static final String IMPORT_MODULE =
            "Import-Module \".\\invoke-atomicredteam\\Invoke-AtomicRedTeam.psd1\";";

    private static final String LOG_PATH = "";

    public AtomicTestManager(PowerShellExecutor powerShellExecutor) {
        this.powerShellExecutor = powerShellExecutor;
    }

    public String runAtomicTest(String techniqueId) throws IOException {
        String command = String.format("%s Invoke-AtomicTest %s", IMPORT_MODULE, techniqueId);
        return powerShellExecutor.execute(command);
    }

    public String runAtomicTest(String techniqueId, int testNumber) throws IOException {
        String command = String.format("%s Invoke-AtomicTest %s -TestNumbers %s", IMPORT_MODULE, techniqueId, testNumber);
        return powerShellExecutor.execute(command);
    }

    public String runAtomicTest(String techniqueId, int[] testNumbers) throws IOException {
        StringBuilder testNumbersStr = new StringBuilder();
        for (int num : testNumbers) {
            testNumbersStr.append(num).append(",");
        }

        // Remove trailing comma
        if (testNumbersStr.length() > 0) {
            testNumbersStr.setLength(testNumbersStr.length() - 1);
        }

        String command = String.format("%s Invoke-AtomicTest %s -TestNumbers %s",
                IMPORT_MODULE, techniqueId, testNumbersStr);
        return powerShellExecutor.execute(command);
    }

    public String runAllTestsForTechnique(String techniqueId, String os) throws IOException {
        String psScript = String.join("\n",
                "Import-Module \"C:\\AtomicRedTeam\\invoke-atomicredteam\\Invoke-AtomicRedTeam.psd1\" -Force;",
                // Get the single technique file
                "$technique = Get-AtomicTechnique -Path \"C:\\AtomicRedTeam\\atomics\\" + techniqueId + "\\" + techniqueId + ".yaml\";",
                // Loop through atomic tests
                "foreach ($atomic in $technique.atomic_tests) {",
                "  if ($atomic.supported_platforms -contains '" + os + "' -and ($atomic.executor.name -ne 'manual')) {",
                "    try {",
                "      Write-Output \"----------------------------------------------\"",
                "      Write-Output \"Checking prereqs for test: $($atomic.name) [$($atomic.auto_generated_guid)]\";",
                "      Invoke-AtomicTest " + techniqueId + " -TestGuids $atomic.auto_generated_guid -GetPrereqs | Out-Null;",
                "      Write-Output \"Running: $($atomic.name) [$($atomic.auto_generated_guid)]\";",
                "      Invoke-AtomicTest " + techniqueId + " -TestGuids $atomic.auto_generated_guid;",
                "      Start-Sleep -Seconds 2;",
                "      Write-Output \"Cleaning up test: $($atomic.name) [$($atomic.auto_generated_guid)]\";",
                "      Invoke-AtomicTest " + techniqueId + " -TestGuids $atomic.auto_generated_guid -Cleanup;",
                "      Write-Output \"Done with test: $($atomic.name)\";",
                "    } catch {",
                "      Write-Warning \"Failed test: $($atomic.name)\";",
                "    }",
                "  } else {",
                "    Write-Output \"Skipping test: $($atomic.name) [Unsupported OS or manual executor]\"",
                "  }",
                "}"
        );

        return powerShellExecutor.execute(psScript);
    }


    public String runAtomicTest(String techniqueId, String testNumbersStr) throws IOException {
        // Split the string and parse to int array
        String[] parts = testNumbersStr.split(",");
        int[] testNumbers = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            testNumbers[i] = Integer.parseInt(parts[i].trim());
        }

        // Call the int[] version
        return runAtomicTest(techniqueId, testNumbers);
    }

    public String checkPrereqs(String techniqueId, String testName) throws IOException {
        String command = String.format("%s Invoke-AtomicTest %s -TestName \"%s\" -CheckPrereqs", IMPORT_MODULE, techniqueId, testName);
        return powerShellExecutor.execute(command);
    }

    public String checkPrereqs(String techniqueId, int testNumber) throws IOException {
        String command = String.format("%s Invoke-AtomicTest %s -TestNumbers \"%d\" -CheckPrereqs", IMPORT_MODULE, techniqueId, testNumber);
        return powerShellExecutor.execute(command);
    }

    public String checkPrereqs(String techniqueId) throws IOException {
        String command = String.format("%s Invoke-AtomicTest %s -CheckPrereqs", IMPORT_MODULE, techniqueId);
        return powerShellExecutor.execute(command);
    }


    public String getPrereqs(String techniqueId, String testName) throws IOException {
        String command = String.format("%s Invoke-AtomicTest %s -TestName \"%s\" -GetPrereqs | Invoke-Expression", IMPORT_MODULE, techniqueId, testName);
        return powerShellExecutor.execute(command);
    }

    public String getPrereqs(String techniqueId, int testNumber) throws IOException {
        String command = String.format("%s Invoke-AtomicTest %s -TestNumbers \"%d\" -GetPrereqs | Invoke-Expression", IMPORT_MODULE, techniqueId, testNumber);
        return powerShellExecutor.execute(command);
    }

    public String getPrereqs(String techniqueId) throws IOException {
        String command = String.format("%s Invoke-AtomicTest %s -GetPrereqs | Invoke-Expression", IMPORT_MODULE, techniqueId);
        return powerShellExecutor.execute(command);
    }

    public String runPrereq(String techniqueId) throws IOException {
        String command = String.format("%s Get-PrereqCommand %s | Invoke-Expression", IMPORT_MODULE, techniqueId);
        return powerShellExecutor.execute(command);
    }

    public String showDetailsBrief(String techniqueId) throws IOException {
        String command = String.format("%s Invoke-AtomicTest %s -ShowDetailsBrief", IMPORT_MODULE, techniqueId);
        return powerShellExecutor.execute(command);
    }

    public String showDetails(String techniqueId) throws IOException {
        String command = String.format("%s Invoke-AtomicTest %s -ShowDetails", IMPORT_MODULE, techniqueId);
        return powerShellExecutor.execute(command);
    }

    public String showAll() throws IOException {
        String command = String.format("%s Invoke-AtomicTest All -ShowDetailsBrief", IMPORT_MODULE);
        return powerShellExecutor.execute(command);
    }

    public String showAllOs() throws IOException {
        String command = String.format("%s Invoke-AtomicTest All -ShowDetailsBrief -anyOS", IMPORT_MODULE);
        return powerShellExecutor.execute(command);
    }

    public String cleanupAtomicTest(String techniqueId, String testNumber) throws IOException {
        String command = String.format("%s Invoke-AtomicTest %s -TestNumbers %s -Cleanup", IMPORT_MODULE, techniqueId, testNumber);
        return powerShellExecutor.execute(command);
    }

    public String cleanupAtomicTest(String techniqueId) throws IOException {
        String command = String.format("%s Invoke-AtomicTest %s -Cleanup", IMPORT_MODULE, techniqueId);
        return powerShellExecutor.execute(command);
    }
}
