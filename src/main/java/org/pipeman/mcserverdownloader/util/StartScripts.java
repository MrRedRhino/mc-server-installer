package org.pipeman.mcserverdownloader.util;

import java.io.FileWriter;
import java.io.IOException;

public class StartScripts {
    public static void makeVelocityStartScript(String javaPath) {
        try {
            FileWriter myWriter = new FileWriter("start.sh");
            myWriter.write(javaPath + " -Xmx1G -jar velocity.jar");
            myWriter.close();
        } catch (IOException ignored) {}
    }

    public static void makeVanillaStartScript(String javaPath, String args, boolean headless) {
        try {
            FileWriter myWriter = new FileWriter("start.sh");
            myWriter.write(javaPath + " " + args + " -jar server.jar " + (headless ? "nogui" : ""));
            myWriter.close();
        } catch (IOException ignored) {}
    }

    public static void makeEulaFile() {
        try {
            FileWriter myWriter = new FileWriter("eula.txt");
            myWriter.write("eula=true");
            myWriter.close();
        } catch (IOException ignored) {}
    }
}
