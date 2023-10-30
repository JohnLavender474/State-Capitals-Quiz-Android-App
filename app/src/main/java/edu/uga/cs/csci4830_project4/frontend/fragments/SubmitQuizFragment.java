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
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizzesAccess;
import edu.uga.cs.csci4830_project4.backend.scores.ScoreModel;
import edu.uga.cs.csci4830_project4.backend.scores.ScoreModelFactory;
import edu.uga.cs.csci4830_project4.backend.scores.ScoresAccess;
import edu.uga.cs.csci4830_project4.frontend.activities.ScoreActivity;
import edu.uga.cs.csci4830_project4.frontend.dto.QuizDTO;
import edu.uga.cs.csci4830_project4.frontend.dto.ScoreDTO;

public class SubmitQuizFragment extends Fragment {

    private static final String TAG = "SubmitQuizFragment";

    public SubmitQuizFragment() {
        // Required empty public constructor
    }

    public static SubmitQuizFragment newInstance(QuizDTO quizDTO) {
        SubmitQuizFragment fragment = new SubmitQuizFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("quizDTO", quizDTO);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_submit_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        if (getArguments() == null) {
            throw new IllegalStateException("Arguments are null");
        }
        QuizDTO quizDTO = (QuizDTO) getArguments().getSerializable("quizDTO");
        if (quizDTO == null) {
            throw new IllegalStateException("Quiz dto is null");
        }

        super.onViewCreated(view, savedInstanceState);

        // set listener for the submit quiz button
        Button submitQuizButton = view.findViewById(R.id.btnSubmitQuiz);
        submitQuizButton.setOnClickListener(v -> submitQuiz(quizDTO));
    }

    private void submitQuiz(QuizDTO quizDTO) {
        // TODO: do asynchronously
        Log.d(TAG, "Submitting quiz");

        // delete quiz model
        long quizId = quizDTO.getQuizId();
        Log.d(TAG, "Deleting quiz with id = " + quizId);
        QuizzesAccess quizzesAccess = new QuizzesAccess(getContext());
        quizzesAccess.open();
        quizzesAccess.delete(quizId);
        quizzesAccess.close();

        // create score model
        List<String> questions = quizDTO.getQuestions();
        List<String> responses = quizDTO.getResponses();
        List<String> answers = quizDTO.getAnswers();
        int points = 0;
        for (int i = 0; i < responses.size(); i++) {
            String response = responses.get(i);
            String answer = answers.get(i);

            Log.d(TAG, "Question = " + questions.get(i) + ", response = " + response +
                    ", answer = " + answer);

            if (response.equalsIgnoreCase(answer)) {
                points++;
            }
        }
        String score = points + "/" + responses.size();
        Log.d(TAG, "Score = " + score);

        // TODO: should do asynchronously
        ScoresAccess scoresAccess = new ScoresAccess(getContext());
        ScoreModelFactory scoreModelFactory = new ScoreModelFactory(scoresAccess);
        scoresAccess.open();
        ScoreModel scoreModel = scoreModelFactory.createAndStore(score);
        scoresAccess.close();
        Log.d(TAG, "Created and stored score model = " + scoreModel);

        // go to score activity
        Activity activity = getActivity();
        if (activity == null) {
            Log.e(TAG, "Activity is null");
            throw new IllegalStateException("Activity is null");
        }

        Log.d(TAG, "Finishing quiz activity, starting score activity");

        Intent intent = new Intent(activity, ScoreActivity.class);
        intent.putExtra("scoreDTO", ScoreDTO.fromModel(scoreModel));
        startActivity(intent);

        // finish the quiz activity
        activity.finish();
    }

}
