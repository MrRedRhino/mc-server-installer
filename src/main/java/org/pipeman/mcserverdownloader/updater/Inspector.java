package org.pipeman.mcserverdownloader.updater;

import org.pipeman.mcserverdownloader.util.Files;
import org.pipeman.mcserverdownloader.util.ServerType;

import java.io.File;
import java.nio.file.Paths;

public class Inspector {
    public static InspectionResult inspectFolder(File folder) {

        ServerType serverType;
        String mcVersion;
        File startScript;
        File serverJar;

        for (File f : Files.listDir(folder, ".sh")) {
            String content = Files.readFile(f);
            String[] split = content.split(" ");

            for (int i = 0; i < split.length; i++) {
                if (split[i].equals("-jar")) {
                    File jar = Paths.get(split[i + 1]).toFile();
                    if (jar.exists()) serverJar = jar;
                    System.out.println("Using server jar: " + jar.getName() + " used in start-script: " + f.getName());
                    System.out.println("Using start script: " + f.getName());
                    startScript = f;
                    break;
                }
            }
        }

//        return new InspectionResult(serverType, mcVersion, build, startScript, serverJar);
        return null;
    }

    public static class InspectionResult {
        private final ServerType serverType;
        private final String mcVersion;
        private final File startScript;
        private final File serverJar;

        public InspectionResult(ServerType type, String mcVersion, File startScript, File serverJar) {
            this.serverType = type;
            this.mcVersion = mcVersion;
            this.startScript = startScript;
            this.serverJar = serverJar;
        }

        public ServerType getServerType() {
            return serverType;
        }

        public String getMcVersion() {
            return mcVersion;
        }

        public File getStartScript() {
            return startScript;
        }

        public File getServerJar() {
            return serverJar;
        }
    }
}
