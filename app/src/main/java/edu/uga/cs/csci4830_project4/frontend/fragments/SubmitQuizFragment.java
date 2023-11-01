package edu.uga.cs.csci4830_project4.frontend.fragments;

import static edu.uga.cs.csci4830_project4.backend.scores.ScoreModelFactory.*;

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
import edu.uga.cs.csci4830_project4.frontend.async.CreateAndStoreFactoryTask;
import edu.uga.cs.csci4830_project4.frontend.async.DeleteModelByIdTask;
import edu.uga.cs.csci4830_project4.frontend.dto.QuizDTO;
import edu.uga.cs.csci4830_project4.frontend.dto.ScoreDTO;

/**
 * This fragment is responsible for displaying the submit quiz button. When the submit quiz button
 * is clicked, the quiz is deleted from the database, a score model is created and stored, and the
 * user is taken to the score activity.
 */
public class SubmitQuizFragment extends Fragment {

    private static final String TAG = "SubmitQuizFragment";

    /**
     * Required empty public constructor
     */
    public SubmitQuizFragment() {
        // Required empty public constructor
    }

    /**
     * Creates a new instance of this fragment
     *
     * @param quizDTO the quiz dto to use for submitting the quiz.
     * @return a new instance of this fragment.
     */
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
        Log.d(TAG, "Submitting quiz");

        // delete quiz model by id
        long quizId = quizDTO.getQuizId();
        Log.d(TAG, "Deleting quiz with id = " + quizId);
        DeleteModelByIdTask deleteModelByIdTask =
                new DeleteModelByIdTask(new QuizzesAccess(getContext()));
        deleteModelByIdTask.execute(quizId);

        // create score model
        List<String> questions = quizDTO.getQuestions();
        List<String> responses = quizDTO.getResponses();
        List<String> answers = quizDTO.getAnswers();
        int points = 0;
        for (int i = 0; i < responses.size(); i++) {
            String response = responses.get(i);
            String answer = answers.get(i);

            Log.d(TAG, "Question = " + questions.get(i) + ", response = " + response + ", answer "
                    + "= " + answer);

            if (response.equalsIgnoreCase(answer)) {
                points++;
            }
        }

        String score = points + "/" + responses.size();
        Log.d(TAG, "Score = " + score);
        ScoreModelFactoryParams params = new ScoreModelFactoryParams(score, quizDTO.getQuizType());

        CreateAndStoreFactoryTask<ScoreModelFactoryParams, ScoreModel> createAndStoreFactoryTask =
                new CreateAndStoreFactoryTask<>(
                        new ScoreModelFactory(new ScoresAccess(getContext())),
                        scoreModel -> {
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
                        });
        createAndStoreFactoryTask.execute(params);
    }

}
