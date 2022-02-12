package org.pipeman.mcserverdownloader.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TerminalUtil {
    public static String readLine() {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

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

    public static int readInt() {
        while (true) {
            String l = readLine();
            if (l == null) continue;

            try {
                return Integer.parseInt(l);
            } catch (Exception ignored) {}
        }
    }

    public static class Colors {
        public static final String HEADER = "\033[95m";
        public static final String BLUE = "\033[94m";
        public static final String CYAN = "\033[96m";
        public static final String GREEN = "\033[92m";
        public static final String WARNING = "\033[93m";
        public static final String FAIL = "\033[91m";
        public static final String ENDC = "\033[0m";
        public static final String BOLD = "\033[1m";
        public static final String UNDERLINE = "\033[4m";
    }
}
