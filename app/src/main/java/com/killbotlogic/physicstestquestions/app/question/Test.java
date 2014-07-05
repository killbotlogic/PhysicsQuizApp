package com.killbotlogic.physicstestquestions.app.question;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Test {
    public String name;
    //        private List<Question> questions = new ArrayList<Question>();
    public UUID uuid = UUID.randomUUID();

    private List<TestQuestion> _testQuestions = new ArrayList<TestQuestion>();
    public LinkedList<TestQuestion> testQuestionLinkedList = new LinkedList<TestQuestion>();
    private int numQuestions = 0;
    private boolean _marked;
    private boolean _answersShown;

    public Test(String name) {
        this.name = name;
    }

    public TestQuestion addQuestion(Question q) {
        TestQuestion tq = new TestQuestion(this, q, ++numQuestions);
        _testQuestions.add(tq);
        testQuestionLinkedList.add(tq);
        return tq;
    }

    public void showAnswers() {
        _answersShown = true;
        for (TestQuestion q : _testQuestions) {
            q.showAnswer();
        }
    }
    public void hideAnswers() {
        _answersShown = false;
        for (TestQuestion q : _testQuestions) {
            q.hideAnswer();
        }
    }
    public void clear() {
        _marked = false;


        for (TestQuestion q : _testQuestions) {
            q.clear();
        }
        hideAnswers();
    }


    public boolean isMarked() {
        return _marked;
    }
    public boolean isAnswersShown() { return _answersShown; }

    public double getEarnedPoints() {
        double total = 0.0;
        for (TestQuestion q : _testQuestions) {
            total += q.getEarnedPoints();
        }
        return total;
    }

    public double getTotalPoints() {
        double total = 0.0;
        for (TestQuestion q : _testQuestions) {
            total += q.getTotalPoints();
        }
        return total;
    }

    public void mark() {
        _marked = true;
        for (TestQuestion q : _testQuestions) {
            q.mark();
        }
    }

    public List<TestQuestion> getTestQuestions() {
        return _testQuestions;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isStarted() {
        for (TestQuestion q : _testQuestions) {
            if (q.isAttempted())
                return true;
        }
        return false;
    }
}