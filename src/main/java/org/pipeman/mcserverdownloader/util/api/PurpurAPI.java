package org.pipeman.mcserverdownloader.util.api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.pipeman.mcserverdownloader.Requests;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class PurpurAPI implements IApi {
    @Override
    public ArrayList<String> getVersions() {
        try {
            JSONArray versions = new JSONObject(Requests.get("https://api.purpurmc.org/v2/purpur")).getJSONArray(
                    "versions");

            ArrayList<String> out = new ArrayList<>();
            versions.forEach(obj -> out.add(obj.toString()));
            return out;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DownloadInfo getDownloadInfo(String version) {
        try {
            return new DownloadInfo(new URL("https://api.purpurmc.org/v2/purpur/" + version + "/latest/download"),
                    "purpur-" + version + ".jar");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
