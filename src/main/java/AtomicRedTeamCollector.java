import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.util.List;
import java.util.Map;

public class AtomicRedTeamCollector {
    // URL lấy nội dung của folder "atomics"
    private static final String GITHUB_API_URL = "https://api.github.com/repos/%s/%s/contents/%s";

    private OkHttpClient client;

    public AtomicRedTeamCollector() {
        this.client = new OkHttpClient();
    }

    /**
     * Lấy nội dung repository theo owner, repo và path được chỉ định.
     *
     * @param owner Tên chủ sở hữu repository (ví dụ: redcanaryco)
     * @param repo  Tên repository (ví dụ: atomic-red-team)
     * @param path  Đường dẫn bên trong repository (ví dụ: atomics)
     * @return Nội dung JSON trả về từ GitHub API
     * @throws Exception Nếu có lỗi khi thực hiện request
     */
    public String getRepositoryContents(String owner, String repo, String path) throws Exception {
        String url = String.format(GITHUB_API_URL, owner, repo, path);
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Request failed: " + response);
            }
            return response.body().string();
        }
    }

    /**
     * Lấy nội dung file YAML của một technique cụ thể từ Atomic Red Team.
     */
    public String getTechniqueData(String techniqueId) throws Exception {
        String path = "atomics/" + techniqueId + "/" + techniqueId + ".yaml";
        return getRepositoryContents("redcanaryco", "atomic-red-team", path);
    }

    @Nullable
    public static Map<String, Object> getTestCaseByIndex(String yamlContent, int index) {
        LoaderOptions options = new LoaderOptions();
        Yaml yaml = new Yaml(new SafeConstructor(options));
        Map<String, Object> parsed = yaml.load(yamlContent);

        if (parsed.containsKey("atomic_tests")) {
            List<Map<String, Object>> tests = (List<Map<String, Object>>) parsed.get("atomic_tests");
            if (index >= 0 && index < tests.size()) {
                return tests.get(index);
            }
        }
        return null;
    }




}
