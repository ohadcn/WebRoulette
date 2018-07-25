package net.ninjas.libninja;

public class ninja {
    public long minTime = 0;
    public long maxTime = Long.MAX_VALUE;
    public long nextWorkTime = 0;

    private String ninjaId = "";

    private String[] _capabilities = {"url_retrieve"};
    public String[] allowed_capabilities = _capabilities;
    public String[] getAvailableCapabilities() {
        return _capabilities;
    }

    public boolean stop() {

        return true;
    }

    public boolean start(String ninjaId){
        this.ninjaId = ninjaId;
        try {

        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
