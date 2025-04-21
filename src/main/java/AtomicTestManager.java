import java.io.IOException;

public class AtomicTestManager {
    private PowerShellExecutor powerShellExecutor;
    private static final String IMPORT_MODULE =
            "Import-Module \".\\invoke-atomicredteam\\Invoke-AtomicRedTeam.psd1\";";
    public AtomicTestManager(PowerShellExecutor powerShellExecutor) {
        this.powerShellExecutor = powerShellExecutor;
    }
    public AtomicTestManager() {

    }
    public void setPowerShellExecutor(PowerShellExecutor powerShellExecutor) {
        this.powerShellExecutor = powerShellExecutor;
    }
    public String showAll() throws IOException {
        return powerShellExecutor.execute("Invoke-AtomicTest -ShowDetails");
    }

    public String showAllOs() throws IOException {
        return powerShellExecutor.execute("Invoke-AtomicTest -ShowDetails -GetAtomicsForAllOS");
    }

    public String showDetails(String techniqueId) throws IOException {
        return powerShellExecutor.execute("Invoke-AtomicTest " + techniqueId + " -ShowDetails");
    }

    public String showDetailsBrief(String techniqueId) throws IOException {
        return powerShellExecutor.execute("Invoke-AtomicTest " + techniqueId + " -ShowDetailsBrief");
    }

    public String checkPrereqs(String techniqueId) throws IOException {
        return powerShellExecutor.execute("Invoke-AtomicTest " + techniqueId + " -CheckPrereqs");
    }

    public String checkPrereqs(String techniqueId, int testNumber) throws IOException {
        return powerShellExecutor.execute("Invoke-AtomicTest " + techniqueId + " -TestNumbers " + testNumber + " -CheckPrereqs");
    }

    public String getPrereqs(String techniqueId) throws IOException {
        return powerShellExecutor.execute("Invoke-AtomicTest " + techniqueId + " -GetPrereqs");
    }

    public String getPrereqs(String techniqueId, int testNumber) throws IOException {
        return powerShellExecutor.execute("Invoke-AtomicTest " + techniqueId + " -TestNumbers " + testNumber + " -GetPrereqs");
    }

    public String runAtomicTest(String techniqueId) throws IOException {
        String command = "Invoke-AtomicTest " + techniqueId;
        String result = powerShellExecutor.execute(command);
        Logger.log(techniqueId, "all", command, result);
        return result;
    }

    public String runAtomicTest(String techniqueId, String testNumbers) throws IOException {
        String command = "Invoke-AtomicTest " + techniqueId + " -TestNumbers " + testNumbers;
        String result = powerShellExecutor.execute(command);
        Logger.log(techniqueId, testNumbers, command, result);
        return result;
    }

    public String cleanupAtomicTest(String techniqueId) throws IOException {
        String command = "Invoke-AtomicTest " + techniqueId + " -Cleanup";
        String result = powerShellExecutor.execute(command);
        Logger.log(techniqueId, "all", command, result);
        return result;
    }

    public String cleanupAtomicTest(String techniqueId, String testNumbers) throws IOException {
        String command = "Invoke-AtomicTest " + techniqueId + " -TestNumbers " + testNumbers + " -Cleanup";
        String result = powerShellExecutor.execute(command);
        Logger.log(techniqueId, testNumbers, command, result);
        return result;
    }

    public String runAllTestsForTechnique(String techniqueId) throws IOException {
        String command = "Invoke-AtomicTest " + techniqueId + " -anyOS";

        String result = powerShellExecutor.execute(command);
        Logger.log(techniqueId, "all", command, result);
        return result;
    }


    public String getImportModule() {
        return IMPORT_MODULE;
    }
}
