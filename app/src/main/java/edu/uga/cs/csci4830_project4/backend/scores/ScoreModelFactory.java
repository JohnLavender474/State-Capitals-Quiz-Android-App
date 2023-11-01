package edu.uga.cs.csci4830_project4.backend.scores;

import java.time.LocalDateTime;

import edu.uga.cs.csci4830_project4.backend.contracts.IAccess;
import edu.uga.cs.csci4830_project4.backend.contracts.IModelFactory;
import edu.uga.cs.csci4830_project4.common.QuizType;

/**
 * Factory for creating ScoreModel objects.
 */
public class ScoreModelFactory implements IModelFactory<ScoreModel,
        ScoreModelFactory.ScoreModelFactoryParams> {

    /**
     * Parameters for creating a score model.
     *
     * @param score    the score.
     * @param quizType the quiz type.
     */
    public record ScoreModelFactoryParams(String score, QuizType quizType) {
    }

    private final IAccess<ScoreModel> scoresAccess;

    /**
     * Constructor for this factory.
     *
     * @param scoresAccess The scores access to use for storing the score model. It is opened and
     *                     closed in the {@link #createAndStore(ScoreModelFactoryParams)} method.
     */
    public ScoreModelFactory(IAccess<ScoreModel> scoresAccess) {
        this.scoresAccess = scoresAccess;
    }

    /**
     * Creates a new score model with the provided params.
     *
     * @param params the parameters to use for creating the score model.
     * @return the score model.
     */
    @Override
    public ScoreModel createAndStore(ScoreModelFactoryParams params) {
        ScoreModel model = new ScoreModel();
        model.setScore(params.score());
        model.setQuizType(params.quizType());
        model.setTimeCompleted(LocalDateTime.now());
        scoresAccess.open();
        ScoreModel storedModel = scoresAccess.store(model);
        scoresAccess.close();
        return storedModel;
    }
}
