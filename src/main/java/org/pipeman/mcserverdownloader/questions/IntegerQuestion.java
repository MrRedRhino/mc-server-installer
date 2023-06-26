package org.pipeman.mcserverdownloader.questions;

import org.pipeman.mcserverdownloader.util.TerminalUtil;

import java.util.function.Predicate;

public class IntegerQuestion extends Question<Integer> {
    public IntegerQuestion(String task, Predicate<QuestionCtx> condition) {
        super(task, condition);
    }

    @Override
    public Integer getAnswer() {
        return TerminalUtil.readInt();
    }
}
