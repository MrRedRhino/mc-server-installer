package org.pipeman.mcserverdownloader.util.api.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import org.pipeman.mcserverdownloader.util.api.DownloadInfo;
import org.pipeman.mcserverdownloader.util.api.IApi;
import org.pipeman.mcserverdownloader.util.api.Requests;

import java.net.URL;
import java.util.ArrayList;

public class PaperMCAPI implements IApi {

    @Override
    public ArrayList<String> getVersions() {
        try {
            JSONObject result = new JSONObject(Requests.get("https://papermc.io/api/v2/projects/paper"));
            JSONArray versions = new JSONArray(result.getJSONArray("versions"));

            ArrayList<String> out = new ArrayList<>();
            versions.forEach(obj -> out.add(obj.toString()));
            return out;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DownloadInfo getDownloadInfo(String version) {
        try {
            String r = Requests.get("https://papermc.io/api/v2/projects/paper/versions/" + version);

            int latestBuild = getLatestBuild(new JSONObject(r).getJSONArray("builds"));

            return new DownloadInfo(new URL("https://papermc.io/api/v2/projects/paper/versions/"
                    + version + "/builds/" + latestBuild + "/downloads/paper-" + version + "-" + latestBuild + ".jar"),
                    "paper-" + version + "-" + latestBuild + ".jar");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getLatestBuild(JSONArray builds) {
        int latestBuild = 0;

        for (int i = 0; i < builds.length(); i++) {
            if (builds.getInt(i) > latestBuild) {
                latestBuild = builds.getInt(i);
            }
        }
        return latestBuild;
    }
}
