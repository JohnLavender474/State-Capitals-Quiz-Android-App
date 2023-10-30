package edu.uga.cs.csci4830_project4.frontend.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import edu.uga.cs.csci4830_project4.R;
import edu.uga.cs.csci4830_project4.backend.contracts.IAccess;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizModel;
import edu.uga.cs.csci4830_project4.backend.scores.ScoreModel;
import edu.uga.cs.csci4830_project4.backend.scores.ScoreModelFactory;
import edu.uga.cs.csci4830_project4.frontend.activities.MainActivity;
import edu.uga.cs.csci4830_project4.frontend.dto.QuizDTO;

public class SubmitQuizFragment extends Fragment {

    private static final String TAG = "SubmitQuizFragment";

    private ScoreModelFactory scoreModelFactory;
    private IAccess<QuizModel> quizzesAccess;
    private QuizDTO quizDTO;

    public SubmitQuizFragment() {
        // Required empty public constructor
    }

    public static SubmitQuizFragment newInstance(QuizDTO quizDTO,
                                                 IAccess<QuizModel> quizzesAccess,
                                                 IAccess<ScoreModel> scoresAccess) {
        SubmitQuizFragment fragment = new SubmitQuizFragment();
        fragment.quizzesAccess = quizzesAccess;
        fragment.scoreModelFactory = new ScoreModelFactory(scoresAccess);
        fragment.quizDTO = quizDTO;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_submit_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        if (quizDTO == null) {
            throw new IllegalStateException("Quiz dto is null");
        }

        super.onViewCreated(view, savedInstanceState);

        // set listener for the submit quiz button
        Button submitQuizButton = view.findViewById(R.id.btnSubmitQuiz);
        submitQuizButton.setOnClickListener(v -> submitQuiz());
    }

    private void submitQuiz() {
        // TODO: do asynchronously
        Log.d(TAG, "Submitting quiz");

        // delete quiz model
        long quizId = quizDTO.getQuizId();
        Log.d(TAG, "Deleting quiz with id = " + quizId);
        quizzesAccess.delete(quizId);

        // create score model
        List<String> questions = quizDTO.getQuestions();
        List<String> responses = quizDTO.getResponses();
        List<String> answers = quizDTO.getAnswers();
        int points = 0;
        for (int i = 0; i < responses.size(); i++) {
            String response = responses.get(i);
            String answer = answers.get(i);

            Log.d(TAG, "Question = " + questions.get(i) + ", response = " + response + ", answer " +
                    "= " + answer);

            if (response.equalsIgnoreCase(answer)) {
                points++;
            }
        }
        String score = points + "/" + responses.size();
        Log.d(TAG, "Score = " + score);
        scoreModelFactory.createScoreModel(score);

        // start main activity with toast message
        Activity activity = getActivity();
        if (activity == null) {
            Log.e(TAG, "Activity is null");
            throw new IllegalStateException("Activity is null");
        }
        Log.d(TAG, "Finishing quiz activity, starting main activity");
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("message", "Quit submitted!");
        startActivity(intent);

        // finish the quiz activity
        activity.finish();
    }

}
