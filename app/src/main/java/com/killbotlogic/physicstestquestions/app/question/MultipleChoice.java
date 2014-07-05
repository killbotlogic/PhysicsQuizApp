package com.killbotlogic.physicstestquestions.app.question;

public enum MultipleChoice {
    A, B, C, D, E, NotAttempted("Not Attempted");

    private String _text;

    MultipleChoice() {
        _text = name();
    }

    MultipleChoice(String text) {
        _text = text;
    }

    public String toString() {
        return _text;
    }
}