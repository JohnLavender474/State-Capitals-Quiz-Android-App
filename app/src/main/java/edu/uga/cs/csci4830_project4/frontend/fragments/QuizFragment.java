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
    private int questionIndex;
    private int imageSource;

    public QuizFragment() {
        // Required empty public constructor
    }

    public static QuizFragment newInstance(IQuiz quiz, int questionIndex, int imageSource) {
        QuizFragment fragment = new QuizFragment();
        fragment.quiz = quiz;
        fragment.questionIndex = questionIndex;
        fragment.imageSource = imageSource;
        return fragment;
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

        // set text of question
        TextView questionTextView = view.findViewById(R.id.tvQuestion);
        questionTextView.setText(quiz.getQuestionAt(questionIndex));

        // set state image
        ImageView stateImageView = view.findViewById(R.id.ivStateImage);
        stateImageView.setImageResource(imageSource);

        // collect radio choice buttons
        List<RadioButton> choiceButtons = List.of(
                view.findViewById(R.id.rbChoice1),
                view.findViewById(R.id.rbChoice2),
                view.findViewById(R.id.rbChoice3)
        );

        // fill in choice buttons and add on click listener that sets the response for the quiz
        List<String> choices = quiz.getChoicesAt(questionIndex);
        for (int i = 0; i < choices.size(); i++) {
            String choice = choices.get(i);
            RadioButton choiceButton = choiceButtons.get(i);
            choiceButton.setText(choice);
            choiceButton.setOnClickListener(v -> quiz.setResponse(questionIndex, choice));
        }
    }

}
