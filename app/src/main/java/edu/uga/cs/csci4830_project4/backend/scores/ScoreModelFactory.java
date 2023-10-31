package edu.uga.cs.csci4830_project4.backend.scores;

import edu.uga.cs.csci4830_project4.backend.contracts.IAccess;
import edu.uga.cs.csci4830_project4.backend.contracts.IModelFactory;

/**
 * Factory for creating ScoreModel objects.
 */
public class ScoreModelFactory implements IModelFactory<ScoreModel, String> {

    private final IAccess<ScoreModel> scoresAccess;

    /**
     * Constructor for this factory.
     *
     * @param scoresAccess the scores access object to use for storing the score model. This access
     *                     is opened and closed in the {@link #createAndStore(String)} method.
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
    @Override
    public ScoreModel createAndStore(String score) {
        ScoreModel model = new ScoreModel();
        model.setScore(score);
        scoresAccess.open();
        ScoreModel storedModel = scoresAccess.store(model);
        scoresAccess.close();
        return storedModel;
    }
}
