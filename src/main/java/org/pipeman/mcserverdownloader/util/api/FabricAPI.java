package org.pipeman.mcserverdownloader.util.api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.pipeman.mcserverdownloader.Requests;

import java.net.URL;
import java.util.ArrayList;

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

            return out;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public URL getDownloadURL(String version) {
        try {
            JSONArray installers = new JSONArray(Requests.get("https://meta.fabricmc.net/v2/versions/installer"));
            String installerVersion = installers.getJSONObject(0).getString("version");

            JSONArray loaders = new JSONArray(Requests.get("https://meta.fabricmc.net/v2/versions/loader/" + version));
            String loaderVersion = ((JSONObject) loaders.get(0)).getJSONObject("loader").getString("version");

            return new URL("https://meta.fabricmc.net/v2/versions/loader/"
                    + version + "/" + loaderVersion + "/" + installerVersion + "/server/jar");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    } // 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
}
