package org.pipeman.mcserverdownloader.questions;

import org.pipeman.mcserverdownloader.util.TerminalUtil;

import java.io.File;
import java.util.function.Predicate;

public class DirectoryQuestion extends Question<String> {
    public DirectoryQuestion(String task, String defaultValue, Predicate<QuestionCtx> condition) {
        super(task, defaultValue, condition);
    }

    public DirectoryQuestion(String task, Predicate<QuestionCtx> condition) {
        super(task, System.getProperty("user.dir"), condition);
    }

    @Override
    public String getAnswer() {
        String out = "";
        boolean isCorrect = false;
        while (!isCorrect) {
            System.out.print("> ");
            String line = TerminalUtil.readLine();
            if (line.isEmpty() || line.equals(".")) {
                line = defaultValue();
            }

            out = new File(line).getAbsoluteFile().getPath() + "/";

            File dir = new File(out);
            if (!dir.exists()) {
                System.out.print(out + " does not exist. Create missing directories? (y/n) ");
                if (TerminalUtil.readYesNo()) {
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
                    isCorrect = TerminalUtil.readYesNo();
                } else {
                    isCorrect = true;
                }
            }
        }
        return out;
    }
}
