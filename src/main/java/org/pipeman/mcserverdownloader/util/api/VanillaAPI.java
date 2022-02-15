package org.pipeman.mcserverdownloader.util.api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.pipeman.mcserverdownloader.installer.MCVersion;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class VanillaAPI {
    public static ArrayList<MCVersion> getVersions() throws IOException {
        JSONObject launcherMeta = new JSONObject(
                Requests.get("https://launchermeta.mojang.com/mc/game/version_manifest_v2.json"));
        JSONArray allVersions = launcherMeta.getJSONArray("versions");
        ArrayList<MCVersion> filteredVersions = new ArrayList<>();

        for (int i = 0; i < allVersions.length(); i++) {
            JSONObject version = allVersions.getJSONObject(i);
            if (version.getString("type").equals("release")) {
                filteredVersions.add(new MCVersion(version.getString("url"), version.getString("id")));
                if (version.getString("id").equals("1.8.9")) {
                    break;
                }
            }
        }
        return filteredVersions;
    }

    public static URL getDownloadURL(MCVersion v) throws IOException {
        String r = Requests.get(v.url);

        JSONObject o = new JSONObject(r);
        return new URL(o.getJSONObject("downloads").getJSONObject("server").getString("url"));
    }
}

