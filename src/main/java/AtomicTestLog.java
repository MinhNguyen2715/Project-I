public class AtomicTestLog {
    private String executionTimeUtc;
    private String executionTimeLocal;
    private String technique;
    private String testNumber;
    private String hostname;
    private String ipAddress;
    private String username;
    private String guid;
    private String tag;
    private String customTag;
    private String command;
    private String result;

    // Setters
    public void setExecutionTimeUtc(String executionTimeUtc) { this.executionTimeUtc = executionTimeUtc; }
    public void setExecutionTimeLocal(String executionTimeLocal) { this.executionTimeLocal = executionTimeLocal; }
    public void setTechnique(String technique) { this.technique = technique; }
    public void setTestNumber(String testNumber) { this.testNumber = testNumber; }
    public void setHostname(String hostname) { this.hostname = hostname; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public void setUsername(String username) { this.username = username; }
    public void setGuid(String guid) { this.guid = guid; }
    public void setTag(String tag) { this.tag = tag; }
    public void setCustomTag(String customTag) { this.customTag = customTag; }
    public void setCommand(String command) { this.command = command; }
    public void setResult(String result) { this.result = result; }

    // Getters
    public String getExecutionTimeUtc() { return executionTimeUtc; }
    public String getExecutionTimeLocal() { return executionTimeLocal; }
    public String getTechnique() { return technique; }
    public String getTestNumber() { return testNumber; }
    public String getHostname() { return hostname; }
    public String getIpAddress() { return ipAddress; }
    public String getUsername() { return username; }
    public String getGuid() { return guid; }
    public String getTag() { return tag; }
    public String getCustomTag() { return customTag; }
    public String getCommand() { return command; }
    public String getResult() { return result; }
}
