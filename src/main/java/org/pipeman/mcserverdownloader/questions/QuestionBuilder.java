package org.pipeman.mcserverdownloader.questions;

import java.util.ArrayList;
import java.util.List;

public class QuestionBuilder {
    private final List<Question<?>> questions = new ArrayList<>();

    public QuestionBuilder addQuestion(Question<?> question) {
        questions.add(question);
        return this;
    }

    public Answer[] done() {
        Answer[] answers = new Answer[questions.size()];

        for (int i = 0; i < questions.size(); i++) {
            Question<?> question = questions.get(i);
            if (question.condition() == null || question.condition().test(new QuestionCtx(answers))) {
                String defaultHint = String.format(" (leave empty to use: %s)", question.defaultValue());
                if(!question.task().isEmpty()) {
                    System.out.println(question.task() + (question.defaultValue() == null ? "" : defaultHint));
                }
                answers[i] = new Answer(question.getAnswer());
            }
        }

        return answers;
    }
}
