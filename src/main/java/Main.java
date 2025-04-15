import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        PowerShellExecutor powerShellExecutor = new PowerShellExecutor();
        String result = powerShellExecutor.execute("Get-Process | Select-Object -First 5");
        System.out.println(result);
    }
}
