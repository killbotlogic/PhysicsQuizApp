package com.killbotlogic.physicstestquestions.app.question;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Database {

    private List<Question> questions = new ArrayList<Question>();
    private List<Test> tests = new ArrayList<Test>();
    private HashMap<String, Test> testMap = new HashMap<String, Test>();
    private HashMap<String, TestQuestion> testQuestionMap = new HashMap<String, TestQuestion>();
    private HashMap<QuestionCategory, List<Question>> questionsByCategory = new HashMap<QuestionCategory, List<Question>>();


    public Database(Context context) throws JSONException {
        JSONObject rootJson = new JSONObject(loadJsonFile(context));
        JSONArray testsJson = rootJson.getJSONArray("tests");


        for (int i = 0; i < testsJson.length(); i++) {
            JSONObject testJson = testsJson.getJSONObject(i);
            JSONArray questionsJson = testJson.getJSONArray("questions");
            Test test = new Test(testJson.getString("name"));
            tests.add(test);
            testMap.put(test.uuid.toString(), test);

            for (int j = 0; j < questionsJson.length(); j++) {


                JSONObject questionJson = questionsJson.getJSONObject(j);

                JSONObject choices = questionJson.getJSONObject("choices");
                tests.get(i);

                //String image = "";
                //if (questionJson.get("image") != null)
                String image = questionJson.getString("picture");
                String text = questionJson.getString("text");

                double value = Double.parseDouble(questionJson.getString("value"));
                MultipleChoice answer = MultipleChoice.valueOf(questionJson.getString("solution"));
                QuestionCategory category = QuestionCategory.fromString(questionJson.getString("category"));

                if (!questionsByCategory.containsKey(category))
                    questionsByCategory.put(category, new ArrayList<Question>());
                Question q = new Question(
                        text,
                        answer,
                        choices.getString("a"),
                        choices.getString("b"),
                        choices.getString("c"),
                        choices.getString("d"),
                        choices.getString("e"),
                        value,
                        category,
                        image);

                questions.add(q);
                questionsByCategory.get(category).add(q);


                TestQuestion tq = test.addQuestion(q);

                testQuestionMap.put(tq.uuid.toString(), tq);

            }
        }


        for (QuestionCategory category : questionsByCategory.keySet()) {

            Test test = new Test(category.toString());
            tests.add(test);
            testMap.put(test.uuid.toString(), test);

            List<Question> questions = questionsByCategory.get(category);
            // tests by category
            for (Question q : questions) {
                TestQuestion tq = test.addQuestion(q);
                testQuestionMap.put(tq.uuid.toString(), tq);
            }
        }
        // sorts tests in alphabetical order
        Collections.sort(tests,
                new Comparator<Test>() {
                    @Override
                    public int compare(Test t1, Test t2) {
                        return t1.toString().compareTo(t2.toString());
                    }
                }
        );
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public List<Test> getTests() {
        return tests;
    }

    public HashMap<String, Test> getTestMap() {
        return testMap;
    }

    public HashMap<String, TestQuestion> getTestQuestionMap() {
        return testQuestionMap;
    }

    private String loadJsonFile(Context context) {
        String json;
        try {

            InputStream stream = context.getAssets().open("sample.json");

            int size = stream.available();

            byte[] buffer = new byte[size];

            stream.read(buffer);
            stream.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return json;

    }
}
