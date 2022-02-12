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
}
