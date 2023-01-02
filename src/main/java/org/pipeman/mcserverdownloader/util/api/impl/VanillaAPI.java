package org.pipeman.mcserverdownloader.util.api.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.pipeman.mcserverdownloader.util.api.DownloadInfo;
import org.pipeman.mcserverdownloader.util.api.IApi;
import org.pipeman.mcserverdownloader.util.api.Requests;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class VanillaAPI implements IApi {
    private final HashMap<String, String> versions = new HashMap<>();

    public ArrayList<String> getVersions() {
        try {
            JSONArray allVersions =
                    new JSONObject(Requests.get("https://launchermeta.mojang.com/mc/game/version_manifest_v2.json"))
                            .getJSONArray("versions");

            ArrayList<String> out = new ArrayList<>();

            for (Object obj : allVersions) {
                JSONObject version = new JSONObject(obj.toString());

                if (version.getString("type").equals("release")) {
                    versions.put(version.getString("id"), version.getString("url"));
                    out.add(version.getString("id"));
                    if (version.getString("id").equals("1.8.9")) {
                        break;
                    }
                }
            }

            return out;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DownloadInfo getDownloadInfo(String version) {
        try {
            URL download = new URL(new JSONObject(Requests.get(versions.get(version)))
                    .getJSONObject("downloads").getJSONObject("server").getString("url"));

            return new DownloadInfo(download, "vanilla-" + version + ".jar");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

