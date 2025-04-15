import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        PowerShellExecutor powerShellExecutor = new PowerShellExecutor();
        AtomicTestManager atomicTestManager = new AtomicTestManager(powerShellExecutor);

        String res = atomicTestManager.showDetails("T1001.002");
        System.out.println(res);
    }
}
