package org.pipeman.mcserverdownloader.addon_search;

import org.json.JSONObject;
import org.pipeman.mcserverdownloader.util.api.Requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModrinthImpl implements IAddonSearch {
    public static final String API_URL = "https://api.modrinth.com/v2";

    @Override
    public List<IAddon> search(String query, String mcVersion) throws IOException {
        String url = String.format(
                API_URL + "/search?limit=20&query=%s&facets=[[\"categories:fabric\"], [\"versions:%s\"]]",
                query,
                mcVersion);


        JSONObject resp = new JSONObject(Requests.get(url));
        List<IAddon> out = new ArrayList<>();

        for (Object obj : resp.getJSONArray("hits")) {
            if (!(obj instanceof JSONObject)) continue;
            JSONObject hit = ((JSONObject) obj);

            out.add(new ModrinthMod(hit.getString("title"),
                    hit.getString("description"),
                    hit.getString("author"),
                    hit.getString("project_id"),
                    mcVersion
            ));
        }
        return out;
    }

    @Override
    public String whatAreWeSearching() {
        return "Fabric mods";
    }
}
