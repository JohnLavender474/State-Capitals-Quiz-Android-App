package edu.uga.cs.csci4830_project4.frontend.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import edu.uga.cs.csci4830_project4.R;
import edu.uga.cs.csci4830_project4.frontend.quizzes.IQuiz;

public class QuizFragment extends Fragment {

    private IQuiz quiz;
    private int index;
    private int imageSource;

    public QuizFragment() {
        // Required empty public constructor
    }

    public static QuizFragment newInstance(IQuiz quiz, int index, int imageSource) {
        QuizFragment fragment = new QuizFragment();
        Bundle args = new Bundle();
        args.putSerializable("quiz", quiz);
        args.putInt("index", index);
        args.putInt("imageSource", imageSource);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            quiz = (IQuiz) getArguments().getSerializable("quiz");
            index = getArguments().getInt("index");
            imageSource = getArguments().getInt("imageSource");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        if (quiz == null) {
            throw new IllegalStateException("Quiz is null");
        }

        super.onViewCreated(view, savedInstanceState);

        TextView questionTextView = view.findViewById(R.id.tvQuestion);
        ImageView stateImageView = view.findViewById(R.id.ivStateImage);
        RadioButton choice1RadioButton = view.findViewById(R.id.rbChoice1);
        RadioButton choice2RadioButton = view.findViewById(R.id.rbChoice2);
        RadioButton choice3RadioButton = view.findViewById(R.id.rbChoice3);

        questionTextView.setText(quiz.getQuestionAt(index));
        stateImageView.setImageResource(imageSource);
        List<String> choices = quiz.getChoicesAt(index);
        choice1RadioButton.setText(choices.get(0));
        choice2RadioButton.setText(choices.get(1));
        choice3RadioButton.setText(choices.get(2));
    }

    // TODO:
    /*
    In FragmentStateAdapter class (so not this class):

    @Override
    public int getItemCount() {
        return AndroidVersionFragment.getNumberOfVersions();
    }
     */

}
