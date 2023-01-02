package org.pipeman.mcserverdownloader.meta;

import org.json.JSONObject;
import org.pipeman.mcserverdownloader.util.Files;
import org.pipeman.mcserverdownloader.util.ServerType;

public class ServerMeta {
    private final ServerType serverType;
    private final String minecraftVersion;

    public ServerMeta(ServerType serverType, String minecraftVersion) {
        this.serverType = serverType;
        this.minecraftVersion = minecraftVersion;
    }

    public String minecraftVersion() {
        return minecraftVersion;
    }

    public ServerType serverType() {
        return serverType;
    }

    public static ServerMeta deserialize(JSONObject object) {
        try {
            ServerType serverType = ServerType.fromString(object.getString("server-type"));
            if (serverType == null) return null;
            String minecraftVersion = object.getString("minecraft-version");

            return new ServerMeta(serverType, minecraftVersion);
        } catch (Exception ignored) {
            return null;
        }
    }

    public JSONObject serialize() {
        return new JSONObject()
                .put("server-type", serverType().name())
                .put("minecraft-version", minecraftVersion());
    }

    public void save() {
        Files.makeFile("mcsi-meta.json", serialize().toString(2));
    }
}
