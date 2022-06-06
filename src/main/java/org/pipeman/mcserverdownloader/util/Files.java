package org.pipeman.mcserverdownloader.util;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Files {
    public static void makeVelocityStartScript(String javaPath) {
        try {
            FileWriter writer = new FileWriter("start.sh");
            writer.write(javaPath + " -Xmx1G -jar velocity.jar");
            writer.close();
        } catch (IOException ignored) {}
    }

    public static void makeFile(String filename, String content) {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(content);
            writer.close();
        } catch (IOException ignored) {}
    }

    public static File[] listDir(File folder, String extension) {
        File[] files = folder.listFiles((dir, name) -> name.endsWith(extension));
        return files == null ? new File[] {} : files;
    }

    public static String readFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            for (;;) {
                String line = reader.readLine();
                if (line == null)
                    break;
                builder.append(line);
            }
            return builder.toString();
        } catch (IOException ignored) {}
        return "";
    }
}
