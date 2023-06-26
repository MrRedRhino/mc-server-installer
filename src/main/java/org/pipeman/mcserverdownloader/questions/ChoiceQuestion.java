package org.pipeman.mcserverdownloader.questions;

import org.pipeman.mcserverdownloader.util.TerminalUtil;

import java.util.List;
import java.util.function.Predicate;

public class ChoiceQuestion<T> extends Question<Integer> {
    private final List<T> choices;

    public ChoiceQuestion(String task, Integer defaultValue, Predicate<QuestionCtx> condition, List<T> choices) {
        super(task, defaultValue, condition);
        this.choices = choices;
    }

    public ChoiceQuestion(String task, Predicate<QuestionCtx> condition, List<T> choices) {
        this(task, null, condition, choices);
    }

    @Override
    public Integer getAnswer() {
        TerminalUtil.printList(choices, System.out::println);
        int sel = 0;
        while (sel < 1 || sel > choices.size()) {
            while (true) {
                System.out.print("> ");
                String l = TerminalUtil.readLine();
                if (defaultValue() != null && l.isEmpty()) return defaultValue();

                try {
                    sel = Integer.parseInt(l);
                    break;
                } catch (Exception ignored) {
                }
            }
        }
        return sel;
    }
}
