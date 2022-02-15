package org.pipeman.mcserverdownloader.installer;

public class MCVersion {
    public String url;
    public final String id;

    public MCVersion(String url, String id) {
        this.url = url;
        this.id = id;
    }

    public MCVersion(String id) {
        this.id = id;
    }
}
