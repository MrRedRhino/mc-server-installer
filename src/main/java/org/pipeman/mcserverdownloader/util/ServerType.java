package org.pipeman.mcserverdownloader.util;


public enum ServerType {
    FABRIC("fabric_server.jar"),
    FORGE(""),
    PAPER("paper.jar"),
    PUFFERFISH(""),
    PURPUR("purpur.jar"),
    VANILLA("server.jar"),
    VELOCITY("velocity.jar");

    ServerType(String executableJarName) {
        this.executableJarName = executableJarName;
    }

    public final String executableJarName;
}
