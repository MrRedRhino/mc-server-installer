package org.pipeman.mcserverdownloader.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;

import static java.lang.System.in;

public class TerminalUtil {
    public static String readLine() {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(in));
        try {
            String line = reader.readLine();
            if (line.equalsIgnoreCase("q")) System.exit(0);
            return line;
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
        printList(a, System.out::println);
        return readRange(1, a.size());
    }

    public static void printList(List<?> a, Consumer<Object> print) {
        for (int i = 0; i < a.size(); i++) {
            System.out.print("  " + (i + 1) + ": ");
            print.accept(a.get(i));
        }
    }

    public static int readInt() {
        while (true) {
            System.out.print("> ");
            String l = readLine();
            if (l == null) continue;

            try {
                return Integer.parseInt(l);
            } catch (Exception ignored) {
            }
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

    public static String getInstallDir(String what) {
        String out = "";
        boolean isCorrect = false;
        while (!isCorrect) {
            System.out.print("Enter the directory to install the " + what + " in.\n" +
                    "Leave empty to use the directory this program runs in.\n> ");

            String line = readLine();
            if (line == null || line.isEmpty() || line.equals(".")) {
                line = System.getProperty("user.dir");
            }

            out = new File(line).getAbsoluteFile().getPath() + "/";

            File dir = new File(out);
            if (!dir.exists()) {
                System.out.print(out + " does not exist. Create missing directories? (y/n) ");
                if (readYesNo()) {
                    if (!dir.mkdirs()) {
                        System.out.println("Creating the directories failed. Choose a different path.");
                    } else {
                        isCorrect = true;
                    }
                }
            } else if (dir.isFile()) {
                System.out.println("This is a file!");
            } else {
                File[] files = dir.listFiles(File::isFile);
                if (files != null && files.length > 0) {
                    System.out.print("The directory is not empty. Continue anyway? (y/n) ");
                    isCorrect = readYesNo();
                } else {
                    isCorrect = true;
                }
            }
        }
        return out;
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
