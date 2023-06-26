package org.pipeman.mcserverdownloader.questions;

public class QuestionCtx {
    private final Answer[] answers;

    public QuestionCtx(Answer[] answers) {
        this.answers = answers;
    }

    public Answer answer(int index) {
        return answers[index];
    }
}
