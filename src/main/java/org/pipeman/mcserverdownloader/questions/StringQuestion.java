package org.pipeman.mcserverdownloader.questions;

import org.pipeman.mcserverdownloader.util.TerminalUtil;

import java.util.function.Predicate;

public class StringQuestion extends Question<String> {
    public StringQuestion(String task, String defaultValue, Predicate<QuestionCtx> condition) {
        super(task, defaultValue, condition);
    }

    public StringQuestion(String task, Predicate<QuestionCtx> condition) {
        super(task, null, condition);
    }

    @Override
    public String getAnswer() {
        System.out.print("> ");
        String line = TerminalUtil.readLine();
        if (defaultValue() != null && line.isEmpty()) return defaultValue();
        return line;
    }
}
