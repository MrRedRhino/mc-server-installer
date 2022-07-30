package org.pipeman.mcserverdownloader.addon_search;

import org.json.JSONArray;
import org.json.JSONObject;
import org.pipeman.mcserverdownloader.util.api.DownloadInfo;
import org.pipeman.mcserverdownloader.util.api.Requests;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ModrinthMod extends IAddon {
    private final String name;
    private final String desription;
    private final String author;
    private final String id;
    private final String mcVersion;
    private List<DownloadInfo> versions = new ArrayList<>();

    public ModrinthMod(String name, String description, String author, String id, String mcVersion) {
        this.name = name;
        this.desription = description;
        this.author = author;
        this.id = id;
        this.mcVersion = mcVersion;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return desription;
    }

    @Override
    public String author() {
        return author;
    }

    @Override
    public List<DownloadInfo> versions() {
        if (versions.isEmpty()) {
            List<DownloadInfo> out = new ArrayList<>();
            try {
                JSONArray vs = new JSONArray(Requests.get("https://api.modrinth.com/v2/project/" + id + "/version"));
                for (Object obj : vs) {
                    if (!(obj instanceof JSONObject)) continue;
                    JSONObject version = ((JSONObject) obj);

                    JSONArray mcVersions = version.getJSONArray("game_versions");

                    for (Object mcVersion : mcVersions) {
                        if (mcVersion.equals(this.mcVersion)) {
                            out.add(new DownloadInfo(
                                    new URL(((JSONObject) version.getJSONArray("files").get(0)).getString("url")),
                                    ((JSONObject) version.getJSONArray("files").get(0)).getString("filename"),
                                    "(" + name() + ") " + version.getString("name")
                            ));
                            break;
                        }
                    }
                }
            } catch (Exception ignored) {
            }
            versions = out;
        }
        return versions;
    }

    @Override
    public String toString() {
        return "ModrinthMod{" +
               "name='" + name + '\'' +
               ", desription='" + desription + '\'' +
               ", author='" + author + '\'' +
               '}';
    }
}
