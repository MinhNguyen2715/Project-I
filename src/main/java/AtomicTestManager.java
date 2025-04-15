import java.io.IOException;

public class AtomicTestManager {
    private final PowerShellExecutor powerShellExecutor;
    private static final String IMPORT_MODULE = "Import-Module \".\\invoke-atomicredteam\\Invoke-AtomicRedTeam.psd1\";";

    public AtomicTestManager(PowerShellExecutor powerShellExecutor) {
        this.powerShellExecutor = powerShellExecutor;
    }

    public String runAtomicTest(String techniqueId) throws IOException {
        String command = String.format("%s Invoke-AtomicTest %s", IMPORT_MODULE, techniqueId);
        return powerShellExecutor.execute(command);
    }

    public String checkPrereqs(String techniqueId, String testName) throws IOException {
        String command = String.format("%s Invoke-AtomicTest %s -TestName \"%s\" -CheckPrereqs", IMPORT_MODULE, techniqueId, testName);
        return powerShellExecutor.execute(command);
    }

    public String checkPrereqs(String techniqueId) throws IOException {
        String command = String.format("%s Invoke-AtomicTest %s -CheckPrereqs", IMPORT_MODULE, techniqueId);
        return powerShellExecutor.execute(command);
    }

    public String installPrereqs(String techniqueId, String testName) throws IOException {
        String command = String.format("%s Invoke-AtomicTest %s -TestName \"%s\" -GetPrereqs | Invoke-Expression", IMPORT_MODULE, techniqueId, testName);
        return powerShellExecutor.execute(command);
    }

    public String installPrereqs(String techniqueId) throws IOException {
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
}
