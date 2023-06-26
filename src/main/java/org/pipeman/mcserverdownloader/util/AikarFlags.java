package org.pipeman.mcserverdownloader.util;

public class AikarFlags {
    public static String createFlags(int ramAmount) {
        return String.format(
                "-Xms%sM -Xmx%sM -Dterminal.jline=false -Dterminal.ansi=true -XX:+UseG1GC " +
                "-XX:+ParallelRefProcEnabled -XX:MaxGCPauseMillis=200 -XX:+UnlockExperimentalVMOptions " +
                "-XX:+DisableExplicitGC -XX:+AlwaysPreTouch -XX:G1HeapWastePercent=5 " +
                "-XX:G1MixedGCCountTarget=4 -XX:G1MixedGCLiveThresholdPercent=90 " +
                "-XX:G1RSetUpdatingPauseTimePercent=5 -XX:SurvivorRatio=32 -XX:+PerfDisableSharedMem " +
                "-XX:MaxTenuringThreshold=1 -XX:G1NewSizePercent=30 -XX:G1MaxNewSizePercent=40 " +
                "-XX:G1HeapRegionSize=8M -XX:G1ReservePercent=20 -XX:InitiatingHeapOccupancyPercent=15 " +
                "-Dusing.aikars.flags=https://mcflags.emc.gs -Daikars.new.flags=true", ramAmount, ramAmount);
    }

    public static boolean isSupportedBy(ServerType serverType) {
        return serverType != ServerType.FABRIC;
    }
}
