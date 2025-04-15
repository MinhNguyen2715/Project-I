import java.io.IOException;

public class AtomicTestManager {
    PowerShellExecutor powerShellExecutor = new PowerShellExecutor();
    // Run test by technique ID
    public String runAtomicTest(String techniqueId) throws IOException {
        return powerShellExecutor.execute("Invoke-AtomicTest " + techniqueId);
    }

    // Check if prerequisites are satisfied for a given test
    public String checkPrereqs(String techniqueId, String testName) throws IOException {
        String command = String.format("Invoke-AtomicTest %s -TestName \"%s\" -CheckPrereqs", techniqueId, testName);
        return powerShellExecutor.execute(command);
    }

    public String checkPrereqs(String techniqueId) throws IOException {
        String command = String.format("Invoke-AtomicTest %s -CheckPrereqs", techniqueId);
        return powerShellExecutor.execute(command);
    }

    // Install missing prerequisites
    public String installPrereqs(String techniqueId, String testName) throws IOException {
        String command = String.format("Invoke-AtomicTest %s -TestName \"%s\" -GetPrereqs | Invoke-Expression", techniqueId, testName);
        return powerShellExecutor.execute(command);
    }

    public String installPrereqs(String techniqueId) throws IOException {
        String command = String.format("Invoke-AtomicTest %s -GetPrereqs | Invoke-Expression", techniqueId);
        return powerShellExecutor.execute(command);
    }

    // Run Get-PrereqCommand for technique
    public String runPrereq(String techniqueId) throws IOException {
        return powerShellExecutor.execute("Get-PrereqCommand " + techniqueId + " | Invoke-Expression");
    }

    public String showDetailsBrief(String techniqueId) throws IOException {
        String command = String.format("Invoke-AtomicTest %s -ShowDetailsBrief", techniqueId);
        return powerShellExecutor.execute(command);
    }

    public String showDetails(String techniqueId) throws IOException {
        String command = String.format("Invoke-AtomicTest %s -ShowDetails", techniqueId);
        return powerShellExecutor.execute(command);
    }
}