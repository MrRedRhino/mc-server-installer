package org.pipeman.mcserverdownloader.util.api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.pipeman.mcserverdownloader.Requests;

import java.net.URL;
import java.util.ArrayList;

public class VelocityAPI implements IApi {
    public ArrayList<String> getVersions() {
        try {
            JSONObject result = new JSONObject(Requests.get("https://papermc.io/api/v2/projects/velocity"));
            JSONArray versions = new JSONArray(result.getJSONArray("versions"));

            ArrayList<String> out = new ArrayList<>();
            versions.forEach(obj -> out.add(obj.toString()));
            return out;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public URL getDownloadURL(String version) {
        try {
            String r = Requests.get("https://papermc.io/api/v2/projects/velocity/versions/" + version);

            int latestBuild = getLatestBuild(new JSONObject(r).getJSONArray("builds"));

            return new URL("https://papermc.io/api/v2/projects/velocity/versions/"
                    + version + "/builds/" + latestBuild
                    + "/downloads/velocity-" + version + "-" + latestBuild + ".jar");
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
