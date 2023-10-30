package edu.uga.cs.csci4830_project4.backend.scores;

import edu.uga.cs.csci4830_project4.backend.contracts.IAccess;

/**
 * Factory for creating ScoreModel objects.
 */
public class ScoreModelFactory {

    private final IAccess<ScoreModel> scoresAccess;

    /**
     * Constructor for this factory.
     *
     * @param scoresAccess the scores access object to use for storing the score model.
     */
    public ScoreModelFactory(IAccess<ScoreModel> scoresAccess) {
        this.scoresAccess = scoresAccess;
    }

    /**
     * Creates a new score model with the provided score.
     *
     * @param score the score to store in the model.
     * @return the score model.
     */
    public ScoreModel createAndStore(String score) {
        ScoreModel model = new ScoreModel();
        model.setScore(score);
        return scoresAccess.store(model);
    }
}
