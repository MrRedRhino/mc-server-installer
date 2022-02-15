package org.pipeman.mcserverdownloader.util;

public enum ServerType {
    FABRIC(""),
    FORGE(""),
    PAPER("paper.jar"),
    PUFFERFISH(""),
    PURPUR(""),
    VANILLA("server.jar");

    ServerType(String executableJarName) {
        this.executableJarName = executableJarName;
    }

    public final String executableJarName;
}
