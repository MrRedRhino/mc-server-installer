package org.pipeman.mcserverdownloader.questions;

public class Answer {
    private final Object value;

    public Answer(Object value) {
        this.value = value;
    }

    public int getAsInt() {
        return (int) value;
    }

    public String getAsString() {
        return (String) value;
    }

    public boolean getAsBoolean() {
        return (boolean) value;
    }
}
