package edu.uga.cs.csci4830_project4.backend.quizzes;

/**
 * This enum represents the different types of quizzes that can be played. The main quiz type
 * is {@link #CAPITAL_QUIZ}, but other types can be added in the future.
 */
public enum QuizType {
    /**
     * The main quiz type. This quiz type asks the user to guess the capital of a state.
     */
    CAPITAL_QUIZ,
    /**
     * This quiz type asks the user to guess the founding year of a state.
     */
    FOUNDING_YEAR_QUIZ,
    /**
     * This quiz type asks the user to guess which city in a set of choices is not in the state.
     */
    CITY_NOT_BELONGING_QUIZ

    // TODO: Add more quiz types here.
}
