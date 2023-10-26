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

    private int currentQuestionIndex;
    private int score;

    public CapitalsQuizLogic(@NonNull QuizModel quizModel,
                             @NonNull List<StateModel> stateModels,
                             @NonNull IAccess<QuizModel> quizAccess) {
        this.quizModel = quizModel;
        this.stateModels = stateModels;
        this.quizAccess = quizAccess;
        currentQuestionIndex = 0;
        score = 0;
    }

    @Override
    public void startQuiz() {
        // Load the quizModel progress from the database (if any).
        // Initialize quizModel state based on the loaded quizModel model.
        // Set the currentQuestionIndex, user responses, etc.

    }

    @Override
    public String getCurrentQuestion() {
        // Return the current question (e.g., state capital question).
        if (currentQuestionIndex < stateModels.size()) {
            StateModel currentState = stateModels.get(currentQuestionIndex);
            return "What is the capital of " + currentState.getStateName() + "?";
        }
        return "Quiz Completed!";
    }

    @Override
    public void goToNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex >= stateModels.size()) {
            finishQuiz();
        }
    }

    @Override
    public boolean hasMoreQuestions() {
        return currentQuestionIndex < stateModels.size();
    }

    @Override
    public void submitResponse(String userResponse) {
        // Compare the user's response to the correct answer and update the score.
        StateModel currentState = stateModels.get(currentQuestionIndex);
        if (userResponse.equalsIgnoreCase(currentState.getCapitalCity())) {
            score++;
        }

        // Update the user's response in the quizModel model.
        String[] responses = quizModel.getResponses();
        responses[currentQuestionIndex] = userResponse;
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
        quizModel.setScore(String.valueOf(score));

        // TODO: do as async task
        quizAccess.update(quizModel);
    }
}

