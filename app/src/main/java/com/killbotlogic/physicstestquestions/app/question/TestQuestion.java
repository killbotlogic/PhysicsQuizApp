package com.killbotlogic.physicstestquestions.app.question;

import java.util.UUID;

public class TestQuestion {

    public int index;
    public UUID uuid = UUID.randomUUID();

    public Test test;
    public Question _question;
    private boolean _answerShown;
    public MultipleChoice userAnswer = MultipleChoice.NotAttempted;

    public double pointsTotal;
    private double pointsEarned;
    private boolean _marked;

    public TestQuestion(Test t, Question q, int index) {

        this.test = t;
        this._question = q;
        this.index = index;
        this.pointsTotal = q.value;
    }

    public TestQuestion getPreviousQuestion() {
        int index = test.getTestQuestions().indexOf(this);
        if (index > 0)
            return test.getTestQuestions().get(index - 1);
        return null;
    }

    public TestQuestion getNextQuestion() {
        int index = test.getTestQuestions().indexOf(this);

        if (index < test.getTestQuestions().size() - 1)
            return test.getTestQuestions().get(index + 1);
        return null;
    }

    public void clear() {
        _marked = false;
        userAnswer = MultipleChoice.NotAttempted;
    }
    public boolean isAttempted() {
        return userAnswer != MultipleChoice.NotAttempted;
    }

    public boolean isMarked() {
        return _marked;
    }

    public boolean isAnswerShown() { return _answerShown; }

    public double getEarnedPoints() {
        if (userAnswer == _question.answer) {
            return pointsTotal;
        }
        return 0d;
    }

    public MultipleChoice getUserAnswer() { return userAnswer; }
    public void setUserAnswer(MultipleChoice ans) { userAnswer = ans; }
    public double mark() {
        _marked = true;
        if (userAnswer == _question.answer) {
            pointsEarned = pointsTotal;
        }
        return pointsEarned;
    }

    @Override
    public String toString() {
        return test.name + " Question " + index;
    }

    public double getTotalPoints() {
        return pointsTotal;
    }

    public void hideAnswer() {
        _answerShown = false;
    }

    public void showAnswer() {
        _answerShown = true;
    }

    public Question getQuestion() {
        return _question;
    }
}