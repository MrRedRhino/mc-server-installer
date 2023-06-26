package org.pipeman.mcserverdownloader.util;

import org.pipeman.mcserverdownloader.questions.LineEmptyException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static java.lang.System.in;

public class TerminalUtil {
    public static String readLine() {
        return readLine(false);
    }

    public static String readLine(boolean reportEmptiness) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            String line = reader.readLine();
            if (reportEmptiness && line.isEmpty()) throw new LineEmptyException();
            return line;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean readYesNo() {
        while (true) {
            String l = readLine();

            if (l.equalsIgnoreCase("y")) {
                return true;
            } else if (l.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.print("> ");
        }
    }

    public static int readChoice(int lower, int upper) {
        int sel = 0;
        while (sel < lower || sel > upper) {
            sel = TerminalUtil.readInt();
        }
        return sel;
    }

    public static int readChoice(List<String> list) {
        printList(list, System.out::println);
        return readChoice(1, list.size());
    }

    public static int readChoice(String... choices) {
        return readChoice(Arrays.asList(choices));
    }

    public static <T> void printList(List<T> a, Consumer<T> print) {
        for (int i = 0; i < a.size(); i++) {
            System.out.print("  " + (i + 1) + ": ");
            print.accept(a.get(i));
        }
    }

    public static <T> void printList(T[] a, Consumer<T> print) {
        for (int i = 0; i < a.length; i++) {
            System.out.print("  " + (i + 1) + ": ");
            print.accept(a[i]);
        }
    }

    public static int readInt() {
        while (true) {
            System.out.print("> ");
            String l = readLine();

            try {
                return Integer.parseInt(l);
            } catch (Exception ignored) {
            }
        }
    }

    public static String getInstallDir(String what) {
        String out = "";
        boolean isCorrect = false;
        while (!isCorrect) {
            System.out.print("Enter the directory to install the " + what + " in.\n" +
                             "Leave empty to use the directory this program runs in.\n> ");

            String line = readLine();
            if (line.isEmpty() || line.equals(".")) {
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
