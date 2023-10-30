package edu.uga.cs.csci4830_project4.frontend.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.uga.cs.csci4830_project4.R;
import edu.uga.cs.csci4830_project4.frontend.dto.QuizDTO;

public class SubmitQuizFragment extends Fragment {

    private QuizDTO quizDTO;

    public SubmitQuizFragment() {
        // Required empty public constructor
    }

    public static SubmitQuizFragment newInstance(QuizDTO quizDTO) {
        SubmitQuizFragment fragment = new SubmitQuizFragment();
        Bundle args = new Bundle();
        args.putSerializable("quizDto", quizDTO);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quizDTO = (QuizDTO) getArguments().getSerializable("quizDto");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        if (quizDTO == null) {
            throw new IllegalStateException("Quiz dto is null");
        }

        super.onViewCreated(view, savedInstanceState);

        // TODO:
        // implement button that sets finished to true, deletes the quiz model from the database,
        // stores a new score model into the database, and then returns to the MainActivity
    }

}
