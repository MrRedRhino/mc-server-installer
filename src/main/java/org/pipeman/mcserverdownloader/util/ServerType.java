package org.pipeman.mcserverdownloader.util;


public enum ServerType {
    FABRIC("fabric_server.jar", "mods"),
    PAPER("paper.jar", "plugins"),
    PURPUR("purpur.jar", "plugins"),
    VANILLA("server.jar", "plugins"),
    VELOCITY("velocity.jar", "plugins");

    ServerType(String executableJarName, String addonFolder) {
        this.executableJarName = executableJarName;
        this.addonFolder = addonFolder;
    }

    public static ServerType fromString(String name) {
        if (name == null) return null;
        for (ServerType value : values()) if (value.name().equals(name)) return value;
        return null;
    }

    public final String executableJarName;
    public final String addonFolder;
}
