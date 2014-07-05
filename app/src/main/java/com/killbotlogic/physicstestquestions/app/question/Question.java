package com.killbotlogic.physicstestquestions.app.question;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Question {
    public UUID uuid = UUID.randomUUID();
    public String question;
    public String image;
    public MultipleChoice answer;
    public QuestionCategory category;
    public double value;

    public Map<MultipleChoice, String> answers = new HashMap<MultipleChoice, String>();

//    public Question(String question, MultipleChoice answer, String optionA,
//                    String optionB, String optionC, String optionD, String optionE) {
//
//        this(question, answer, optionA, optionB, optionC, optionD, optionE, null);
//    }

    public Question(String question, MultipleChoice answer, String optionA,
                    String optionB, String optionC, String optionD, String optionE, double value, QuestionCategory qc) {
        this(question, answer, optionA, optionB, optionC, optionD, optionE, value, qc, null);
    }
    public Question(String question, MultipleChoice answer, String optionA,
                    String optionB, String optionC, String optionD, String optionE, double value, QuestionCategory qc, String image) {


        this.question = question;
        this.answer = answer;

        this.answers.put(MultipleChoice.A, optionA);
        this.answers.put(MultipleChoice.B, optionB);
        this.answers.put(MultipleChoice.C, optionC);
        this.answers.put(MultipleChoice.D, optionD);
        this.answers.put(MultipleChoice.E, optionE);
        this.value = value;
        this.image = image;
        this.category = qc;
    }


    @Override
    public String toString() {
        return "Question " + uuid;
    }

    public MultipleChoice getSolution() {
        return answer;
    }
}
