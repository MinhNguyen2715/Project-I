import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        AtomicRedTeamCollector collector = new AtomicRedTeamCollector();
        try {
            String technique = collector.getTechniqueData("T1001.002");
            System.out.println(technique);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
