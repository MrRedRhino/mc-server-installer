package org.pipeman.mcserverdownloader.util;

import java.io.FileWriter;
import java.io.IOException;

public class Files {
    public static void makeVelocityStartScript(String javaPath) {
        try {
            FileWriter myWriter = new FileWriter("start.sh");
            myWriter.write(javaPath + " -Xmx1G -jar velocity.jar");
            myWriter.close();
        } catch (IOException ignored) {}
    }

    public static void makeFile(String filename, String content) {
        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write(content);
            myWriter.close();
        } catch (IOException ignored) {}
    }
}
