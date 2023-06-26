package org.pipeman.mcserverdownloader.questions;

import org.pipeman.mcserverdownloader.util.TerminalUtil;

import java.util.function.Predicate;

public class BooleanQuestion extends Question<Boolean> {
    public BooleanQuestion(String task, Boolean defaultValue, Predicate<QuestionCtx> condition) {
        super(task, defaultValue, condition);
    }

    public BooleanQuestion(String task, Predicate<QuestionCtx> condition) {
        super(task, condition);
    }

    @Override
    public Boolean getAnswer() {
        while (true) {
            System.out.print("> ");
            String l = TerminalUtil.readLine();
            if (defaultValue() != null && l.isEmpty()) return defaultValue();

            if (l.equalsIgnoreCase("y")) {
                return true;
            } else if (l.equalsIgnoreCase("n")) {
                return false;
            }
        }
    }
}
