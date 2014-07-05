package com.killbotlogic.physicstestquestions.app.question;

public enum QuestionCategory {
    Motion,
    Force,
    Work,
    Energy,
    Momentum,
    Rotation,
    RotationEquilibrium("Rotation, Equilibrium"),
    WorkEnergyMomentum("Work, Energy, Momentum"),
    Equilibrium,
    Thermodynamics,
    Statics("Statics"),
    StaticsAngularMomentum("Statics, Angular Momentum"),
    Thermal,
    Miscellaneous,
    ExperimentalSkills("Experimental Skills"),
    None;

    private String _text;

    QuestionCategory() {
        _text = name();
    }

    QuestionCategory(String text) {
        _text = text;
    }

    @Override
    public String toString() {
        return _text;
    }
    public static QuestionCategory fromString(String text) {
        if (text != null) {
            for (QuestionCategory cat : QuestionCategory.values()) {
                if (text.equalsIgnoreCase(cat.toString())) {
                    return cat;
                }
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}