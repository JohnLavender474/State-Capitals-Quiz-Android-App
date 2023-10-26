package edu.uga.cs.csci4830_project4.frontend.quizzes.impl;

import androidx.annotation.NonNull;

import java.util.List;

import edu.uga.cs.csci4830_project4.backend.contracts.IAccess;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizModel;
import edu.uga.cs.csci4830_project4.backend.states.StateModel;
import edu.uga.cs.csci4830_project4.frontend.quizzes.IQuizLogic;

public class CapitalsQuizLogic implements IQuizLogic {

    private final QuizModel quizModel;
    private final List<StateModel> stateModels;
    private final IAccess<QuizModel> quizAccess;
    private final int quizSize;

    private int currentQuestionIndex;
    private int score;

    public CapitalsQuizLogic(@NonNull QuizModel quizModel,
                             @NonNull List<StateModel> stateModels,
                             @NonNull IAccess<QuizModel> quizAccess) {
        this.quizModel = quizModel;
        this.stateModels = stateModels;

        if (quizModel.getResponses().size() != stateModels.size()) {
            throw new IllegalStateException("QuizModel responses and stateModels size mismatch");
        }
        quizSize = stateModels.size();

        this.quizAccess = quizAccess;
        score = quizModel.getScore();
        currentQuestionIndex = 0;
    }

    @Override
    public String getCurrentQuestion() {
        if (currentQuestionIndex < 0 || currentQuestionIndex >= stateModels.size()) {
            throw new IllegalStateException("Invalid question index: " + currentQuestionIndex);
        }
        StateModel currentState = stateModels.get(currentQuestionIndex);
        return "What is the capital of " + currentState.getStateName() + "?";
    }

    @Override
    public boolean setCurrentQuestionIndex(int currentQuestionIndex) {
        if (currentQuestionIndex >= quizSize || currentQuestionIndex < 0) {
            return false;
        }
        this.currentQuestionIndex = currentQuestionIndex;
        return true;
    }

    @Override
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    @Override
    public int getSizeOfQuiz() {
        return quizSize;
    }

    @Override
    public boolean goToNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex >= quizSize) {
            currentQuestionIndex = quizSize - 1;
            return false;
        }
        return true;
    }

    @Override
    public boolean goToPreviousQuestion() {
        currentQuestionIndex--;
        if (currentQuestionIndex < 0) {
            currentQuestionIndex = 0;
            return false;
        }
        return true;
    }

    @Override
    public boolean atEndOfQuiz() {
        return currentQuestionIndex == quizSize - 1;
    }

    @Override
    public boolean atStartOfQuiz() {
        return currentQuestionIndex == 0;
    }

    @Override
    public void submitResponse(String userResponse) {
        if (currentQuestionIndex >= quizSize || currentQuestionIndex < 0) {
            throw new IllegalStateException("Invalid question index: " + currentQuestionIndex);
        }

        List<String> responses = quizModel.getResponses();
        if (userResponse.equals(responses.get(currentQuestionIndex))) {
            return;
        }

        // If the user has already answered this question, decrement the score.
        // If the user's current response is the same as the correct response, then
        // the score will be incremented back to the prior score in the next if block.
        List<Boolean> answeredCorrectly = quizModel.getAnsweredCorrectly();
        if (answeredCorrectly.get(currentQuestionIndex)) {
            answeredCorrectly.set(currentQuestionIndex, false);
            score--;
        }

        // Compare the user's response to the correct answer and update the score.
        StateModel currentState = stateModels.get(currentQuestionIndex);
        if (userResponse.equalsIgnoreCase(currentState.getCapitalCity())) {
            answeredCorrectly.set(currentQuestionIndex, true);
            score++;
        }

        // Update the user's response in the quizModel model.
        responses.set(currentQuestionIndex, userResponse);
        quizModel.setResponses(responses);

        // TODO: do as async task
        quizAccess.update(quizModel);
        /*
        @SuppressLint("StaticFieldLeak") UpdateModelAsyncTask<QuizModel> updateTask =
                new UpdateModelAsyncTask<>(quizAccess) {
                    @Override
                    public void onProgressUpdate(Void... values) {

                    }

                    @Override
                    public void onPostExecute(Boolean success) {

                    }
                };
        updateTask.execute(quizModel);
         */

    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void finishQuiz() {
        quizModel.setFinished(true);
        quizModel.setScore(score);

        // TODO: do as async task
        quizAccess.update(quizModel);
    }
}

