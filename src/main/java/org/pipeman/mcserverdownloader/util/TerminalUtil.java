package org.pipeman.mcserverdownloader.util;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

import static java.lang.System.in;

public class TerminalUtil {
    public static String readLine() {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(in));
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean readYesNo() {
        while (true) {
            String l = readLine();
            if (l == null) continue;

            if (l.equalsIgnoreCase("y")) {
                return true;
            } else if (l.equalsIgnoreCase("n")) {
                return false;
            }
        }
    }

    public static int readRange(int lower, int upper) {
        int sel = 0;
        while (sel < lower || sel > upper) {
            sel = TerminalUtil.readInt();
        }
        return sel;
    }

    public static int readRange(List<String> a) {
        for (int i = 0; i < a.size(); i++) {
            System.out.println("  " + (i + 1) + ": " + a.get(i));
        }
        return readRange(1, a.size());
    }

    public static int readInt() {
        while (true) {
            System.out.print("> ");
            String l = readLine();
            if (l == null) continue;

            try {
                return Integer.parseInt(l);
            } catch (Exception ignored) {}
        }
    }

    public static File readJavaPath() throws IOException, InterruptedException {
        String input = readLine();
        if (input != null) {
            File file = Paths.get(input).toAbsolutePath().toFile();
            ProcessBuilder pBuilder = new ProcessBuilder(file.toString(), "-version");
            Process p = pBuilder.start();
            p.waitFor();
            System.out.println(p.exitValue());
        }
        return null;
    }

    public static class Colors {
        public static final String HEADER = "\033[95m";
        public static final String BLUE = "\033[94m";
        public static final String CYAN = "\033[96m";
        public static final String GREEN = "\033[92m";
        public static final String WARNING = "\033[93m";
        public static final String RED = "\033[91m";
        public static final String RESET = "\033[0m";
        public static final String BOLD = "\033[1m";
        public static final String UNDERLINE = "\033[4m";
    }
}
