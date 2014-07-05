//package com.killbotlogic.physicstestquestions.app.dummy;
//
//import com.killbotlogic.physicstestquestions.app.question.MultipleChoice;
//import com.killbotlogic.physicstestquestions.app.question.Question;
//import com.killbotlogic.physicstestquestions.app.question.QuestionCategory;
//import com.killbotlogic.physicstestquestions.app.question.Test;
//import com.killbotlogic.physicstestquestions.app.question.TestQuestion;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//public class DummyContent {
//
//
//    /**
//     * An array of sample (dummy) items.
//     */
//
//    public static List<Question> QUESTIONS = new ArrayList<Question>();
//    public static List<Test> TESTS = new ArrayList<Test>();
//
//    public static Map<String, Question> QUESTION_MAP = new HashMap<String, Question>();
//    public static HashMap<String, Test> TEST_MAP = new HashMap<String, Test>();
//    public static HashMap<String, TestQuestion> TEST_QUESTION_MAP =
//            new HashMap<String, TestQuestion>();
//
//    static {
//
//        addTest("Motion");
//        addTest("Force");
//        addTest("Work, Energy, Momentum");
//        addTest("Rotation, Equilibrium");
//        addTest("Thermal");
//        addTest("Miscellaneous");
//
//
//        int qNumber = 1;
//        addQuestion(new Question("Suppose it takes 10.0 hours to drain a container of 5700 m³" +
//                        " of water. " +
//                        "Assume that each cubic centimeter of water has a mass of exactly 1.00 g. " +
//                        "How much mass of water in kg drains from the container each second?",
//                MultipleChoice.E, "0.58", "1.35", "57.5", "135", "158", QuestionCategory.Miscellaneous
//        ));
//
//        addQuestion(new Question("A movie stuntman is to run across and directly off a rooftop (no jump), to " +
//                        "land on the roof of the next building as shown in the figure. " +
//                        "Find the minimum speed with which the stuntman has to run in order to " +
//                        "reach next rooftop.",
//                MultipleChoice.B, "2.4 m/s", "4.5 m/s", "4.8 m/s", "6.8 m/s", "9.8 m/s", QuestionCategory.Miscellaneous, "q2"
//        ));
//
//
//        addQuestion(new Question("The figure below shows a train of four blocks being pulled across a " +
//                        "frictionless floor by force F. " +
//                        "Rank the blocks according to their " +
//                        "accelerations, greatest first.",
//                MultipleChoice.B, "(2 kg block) > (3 kg block) > (5 kg block) > (10 kg block)",
//                "(10 kg block) = (3 kg block) = (5 kg block) = (2 kg block)",
//                "(10 kg block) > (3 kg block) > (5 kg block) > (2 kg block)",
//                "(2 kg block) > (5 kg block) > (3 kg block) > (10 kg block)",
//                "(10 kg block) > (5 kg block) > (3 kg block) > (2 kg block)", QuestionCategory.Miscellaneous, "q3"
//        ));
//
//        addQuestion(new Question("A worker drags a crate across a factory floor by pulling on a rope tied to the " +
//                        "crate. The worker pulls on the rope with a force of magnitude F = 450 N and " +
//                        "the rope is inclined at angle θ = 38° to the horizontal. There is a frictional " +
//                        "force f = 125 N between crate and floor. Calculate the magnitude of the " +
//                        "acceleration of the crate if its mass is 31.6 kg.",
//                MultipleChoice.D, "-4.9 m/s²", "0.74 m/s²", "0.0 m/s²", "7.3 m/s²", "9.8 m/s²", QuestionCategory.Miscellaneous
//        ));
//
//        addQuestion(new Question("The figure shows four choices for the direction of a force of magnitude F to be " +
//                        "applied to a block on an inclined plane. The directions are either horizontal or " +
//                        "vertical. For choices a and b, the force is not enough to lift the block off the " +
//                        "plane. Rank the choices according to the magnitude of the normal force on " +
//                        "the block from the plane, greatest first.", MultipleChoice.D,
//                "d > c > b > a", "c > d > b > a", "a > b > c > d", "d > c > a > b",
//                "b > c > a > d", QuestionCategory.Miscellaneous, "q5"
//        ));
//
//        addQuestion(new Question("The figure shows a box (m1 = 3.0 kg) on a frictionless plane inclined at angle " +
//                        "θ1 = 30°. The box is connected via a cord of negligible mass to another box " +
//                        "(m2 = 2.0 kg) on a frictionless plane inclined at angle θ2 = 60°. The pulley is " +
//                        "frictionless and has negligible mass. The tension in the cord is closest to:",
//                MultipleChoice.A, "16 N", "18 N", "22 N", "23 N", "29 N", QuestionCategory.Miscellaneous, "q6"
//        ));
//    }
//
//    private static void addQuestion(Question item) {
//        QUESTIONS.add(item);
//        QUESTION_MAP.put(item.uuid.toString(), item);
//
//
//        for (Test test : TESTS) {
//            TestQuestion tq = test.addQuestion(item);
//            TEST_QUESTION_MAP.put(tq.uuid.toString(), tq);
//        }
//    }
//
//    private static void addTest(String name) {
//
//
//        Test test = new Test(name);
//        TESTS.add(test);
//        TEST_MAP.put(test.uuid.toString(), test);
//    }
//
//
//
//    public static class User {
//
//    }
//}
