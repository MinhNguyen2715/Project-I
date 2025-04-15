import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String command = "echo 'hello'";
        String result = PowerShellExecutor.execute("Get-Process | Select-Object -First 5");
        System.out.println(result);
    }
}
