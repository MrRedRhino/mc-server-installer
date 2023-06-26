package org.pipeman.mcserverdownloader.util.api.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.pipeman.mcserverdownloader.util.api.DownloadInfo;
import org.pipeman.mcserverdownloader.util.api.IApi;
import org.pipeman.mcserverdownloader.util.api.Requests;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class FabricAPI implements IApi {

    @Override
    public ArrayList<String> getVersions() {
        try {
            JSONArray versions = new JSONArray(Requests.get("https://meta.fabricmc.net/v2/versions/game"));
            ArrayList<String> out = new ArrayList<>();
            for (Object o : versions) {
                JSONObject version = new JSONObject(o.toString());
                if (version.getBoolean("stable")) {
                    out.add(version.getString("version"));
                }
            }

            Collections.sort(out);
            return out;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DownloadInfo getDownloadInfo(String version) {
        try {
            JSONArray installers = new JSONArray(Requests.get("https://meta.fabricmc.net/v2/versions/installer"));
            String installerVersion = installers.getJSONObject(0).getString("version");

            JSONArray loaders = new JSONArray(Requests.get("https://meta.fabricmc.net/v2/versions/loader/" + version));
            String loaderVersion = ((JSONObject) loaders.get(0)).getJSONObject("loader").getString("version");

            return new DownloadInfo(new URL("https://meta.fabricmc.net/v2/versions/loader/"
                    + version + "/" + loaderVersion + "/" + installerVersion + "/server/jar"),
                    "fabric-" + version + "-" + loaderVersion + ".jar");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
