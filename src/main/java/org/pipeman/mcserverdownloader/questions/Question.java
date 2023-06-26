package org.pipeman.mcserverdownloader.questions;

import java.util.function.Predicate;

public abstract class Question<T> {
    private final String task;
    private final T defaultValue;
    private final Predicate<QuestionCtx> condition;

    public Question(String task, T defaultValue, Predicate<QuestionCtx> condition) {
        this.task = task;
        this.defaultValue = defaultValue;
        this.condition = condition;
    }

    public Question(String task, Predicate<QuestionCtx> condition) {
        this(task, null, condition);
    }

    public abstract T getAnswer();

    public String task() {
        return task;
    }

    public T defaultValue() {
        return defaultValue;
    }

    public Predicate<QuestionCtx> condition() {
        return condition;
    }
}
