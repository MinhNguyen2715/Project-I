import java.io.IOException;

public class AtomicTestManager {

    public static String listAvailableTests() throws IOException {
        return PowerShellExecutor.execute("Get-AtomicTechnique");
    }

    public static String getTestDetails(String techniqueId) throws IOException {
        return PowerShellExecutor.execute("Get-AtomicTechnique " + techniqueId);
    }

    public static String runTest(String techniqueId) throws IOException {
        return PowerShellExecutor.runAtomicTest(techniqueId);
    }

    public static String runPrerequisites(String techniqueId) throws IOException {
        return PowerShellExecutor.runPrereq(techniqueId);
    }
}