package edu.uga.cs.csci4830_project4.frontend.quizzes.impl;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uga.cs.csci4830_project4.backend.contracts.IAccess;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizModel;
import edu.uga.cs.csci4830_project4.backend.states.StateModel;
import edu.uga.cs.csci4830_project4.frontend.quizzes.IQuizLogic;

public class CapitalsQuizLogic implements IQuizLogic {

    private final QuizModel quizModel;
    private final List<StateModel> stateModels;
    private final IAccess<QuizModel> quizAccess;
    private final Map<String, List<String>> choices;
    private final int quizSize;
    private int score;

    public CapitalsQuizLogic(@NonNull QuizModel quizModel,
                             @NonNull List<StateModel> stateModels,
                             @NonNull IAccess<QuizModel> quizAccess) {
        this.quizAccess = quizAccess;
        this.quizModel = quizModel;
        this.stateModels = stateModels;

        List<String> responses = quizModel.getResponses();
        if (responses == null) {
            throw new IllegalStateException("QuizModel responses is null");
        }
        if (responses.size() != stateModels.size()) {
            throw new IllegalStateException("QuizModel responses and stateModels size mismatch");
        }

        quizSize = stateModels.size();
        score = quizModel.getScore();

        choices = new HashMap<>();
        for (StateModel stateModel : stateModels) {
            List<String> stateChoices = Arrays.asList(
                    stateModel.getCapitalCity(),
                    stateModel.getSecondCity(),
                    stateModel.getThirdCity()
            );
            Collections.shuffle(stateChoices);
            choices.put(stateModel.getStateName(), stateChoices);
        }
    }

    @Override
    public String getCurrentStateName() {
        StateModel currentState = stateModels.get(quizModel.getCurrentQuestion());
        return currentState.getStateName();
    }

    @Override
    public String getCurrentQuestion() {
        if (quizModel.getCurrentQuestion() < 0 || quizModel.getCurrentQuestion() >= stateModels.size()) {
            throw new IllegalStateException("Invalid question index: " + quizModel.getCurrentQuestion());
        }
        return "What is the capital of " + getCurrentStateName() + "?";
    }

    @Override
    public List<String> getCurrentChoices() {
        return choices.get(getCurrentStateName());
    }

    @Override
    public String getCurrentResponse() {
        List<String> responses = quizModel.getResponses();
        if (responses == null) {
            throw new IllegalStateException("QuizModel responses is null");
        }
        return responses.get(quizModel.getCurrentQuestion());
    }

    @Override
    public boolean setCurrentQuestionIndex(int currentQuestionIndex) {
        if (currentQuestionIndex >= quizSize || currentQuestionIndex < 0) {
            return false;
        }
        this.quizModel.setCurrentQuestion(currentQuestionIndex);
        quizModel.setCurrentQuestion(quizModel.getCurrentQuestion());
        return true;
    }

    @Override
    public int getCurrentQuestionIndex() {
        return quizModel.getCurrentQuestion();
    }

    @Override
    public int getSizeOfQuiz() {
        return quizSize;
    }

    @Override
    public boolean goToNextQuestion() {
        int currentQuestion = quizModel.getCurrentQuestion();
        currentQuestion++;
        if (currentQuestion >= quizSize) {
            quizModel.setCurrentQuestion(quizSize - 1);
            return false;
        }
        quizModel.setCurrentQuestion(currentQuestion);
        return true;
    }

    @Override
    public boolean goToPreviousQuestion() {
        int currentQuestion = quizModel.getCurrentQuestion();
        currentQuestion--;
        if (currentQuestion < 0) {
            quizModel.setCurrentQuestion(0);
            return false;
        }
        quizModel.setCurrentQuestion(currentQuestion);
        return true;
    }

    @Override
    public boolean atEndOfQuiz() {
        return quizModel.getCurrentQuestion() == quizSize - 1;
    }

    @Override
    public boolean atStartOfQuiz() {
        return quizModel.getCurrentQuestion() == 0;
    }

    @Override
    public void submitResponse(String userResponse) {
        if (quizModel.getCurrentQuestion() >= quizSize || quizModel.getCurrentQuestion() < 0) {
            throw new IllegalStateException("Invalid question index: " + quizModel.getCurrentQuestion());
        }

        List<String> responses = quizModel.getResponses();
        if (responses == null) {
            throw new IllegalStateException("QuizModel responses is null");
        }
        if (userResponse.equals(responses.get(quizModel.getCurrentQuestion()))) {
            return;
        }

        // If the user has already answered this question, decrement the score.
        // If the user's current response is the same as the correct response, then
        // the score will be incremented back to the prior score in the next if block.
        List<Boolean> answeredCorrectly = quizModel.getAnsweredCorrectly();
        if (answeredCorrectly == null) {
            throw new IllegalStateException("QuizModel answeredCorrectly is null");
        }
        if (answeredCorrectly.get(quizModel.getCurrentQuestion())) {
            answeredCorrectly.set(quizModel.getCurrentQuestion(), false);
            score--;
        }

        // Compare the user's response to the correct answer and update the score.
        StateModel currentState = stateModels.get(quizModel.getCurrentQuestion());
        if (userResponse.equalsIgnoreCase(currentState.getCapitalCity())) {
            answeredCorrectly.set(quizModel.getCurrentQuestion(), true);
            score++;
        }

        // Update the user's response in the quizModel model.
        responses.set(quizModel.getCurrentQuestion(), userResponse);
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

