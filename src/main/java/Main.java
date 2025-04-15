import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        PowerShellExecutor powerShellExecutor = new PowerShellExecutor();
        AtomicTestManager atomicTestManager = new AtomicTestManager(powerShellExecutor);

        String res = atomicTestManager.checkPrereqs("T1218.010",1);
        System.out.println(res);
    }
}
