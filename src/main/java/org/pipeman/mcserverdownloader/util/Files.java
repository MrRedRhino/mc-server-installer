package org.pipeman.mcserverdownloader.util;

import java.io.*;

public class Files {
    public static void makeFile(String filename, String content) {
        makeFile(filename, content, false);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void makeFile(String filename, String content, boolean setExecutable) {
        try {
            FileWriter writer = new FileWriter(filename);
            writer.write(content);
            writer.close();
            if (setExecutable) new File(filename).setExecutable(true);
        } catch (IOException ignored) {
        }
    }

    public static File[] listDir(File folder, String extension) {
        File[] files = folder.listFiles((dir, name) -> name.endsWith(extension));
        return files == null ? new File[]{} : files;
    }

    public static String readFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            for (; ; ) {
                String line = reader.readLine();
                if (line == null)
                    break;
                builder.append(line);
            }
            return builder.toString();
        } catch (IOException ignored) {
        }
        return "";
    }

    public static void copyDirectoryRecursively(File source, File destination) throws IOException {
        if (source.isDirectory()) {
            if (!destination.exists()) destination.mkdir();

            String[] children = source.list();
            if (children == null) return;
            for (String child : children) {
                copyDirectoryRecursively(new File(source, child), new File(destination, child));
            }
        } else {
            java.nio.file.Files.copy(source.toPath(), destination.toPath());
        }
    }
}
