package edu.uga.cs.csci4830_project4.frontend.fragments;

import android.graphics.Color;
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
import java.util.Locale;

import edu.uga.cs.csci4830_project4.R;
import edu.uga.cs.csci4830_project4.frontend.quizzes.IQuiz;

/**
 * This class provides a fragment for a quiz.
 */
public class QuizFragment extends Fragment {

    private IQuiz quiz;
    private int questionIndex;
    private int imageSource;

    /**
     * Constructs a new {@link QuizFragment} instance.
     */
    public QuizFragment() {
        // Required empty public constructor
    }

    /**
     * Constructs a new {@link QuizFragment} instance.
     *
     * @param quiz          the quiz.
     * @param questionIndex the index of the question.
     * @param imageSource   the image source.
     * @return the new {@link QuizFragment} instance.
     */
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

        TextView questionNumberView = view.findViewById(R.id.tvQuestionNumber);
        questionNumberView.setText(String.format(Locale.getDefault(), "Question %d of %d",
                questionIndex + 1, quiz.getQuizDTO().getQuestions().size()));

        // set text of question
        TextView questionTextView = view.findViewById(R.id.tvQuestion);
        questionTextView.setText(quiz.getQuestionAt(questionIndex));

        // set state image
        ImageView stateImageView = view.findViewById(R.id.ivStateImage);
        stateImageView.setImageResource(imageSource);

        // collect radio choice buttons
        final List<RadioButton> choiceButtons = List.of(view.findViewById(R.id.rbChoice1),
                view.findViewById(R.id.rbChoice2), view.findViewById(R.id.rbChoice3));

        // fill in choice buttons and add on click listener that sets the response for the quiz
        List<String> choices = quiz.getChoicesAt(questionIndex);
        for (int i = 0; i < choices.size(); i++) {
            String choice = choices.get(i);

            final RadioButton choiceButton = choiceButtons.get(i);
            choiceButton.setText(choice);

            String color;
            if (choice.equals(quiz.getResponseAt(questionIndex))) {
                color = "#008000";
            } else {
                color = "#808080";
            }
            choiceButton.setBackgroundColor(Color.parseColor(color));

            choiceButton.setOnClickListener(v -> {
                // set all buttons to gray
                choiceButtons.forEach(button -> button.setBackgroundColor(Color.parseColor(
                        "#808080")));

                // set selected button to green
                choiceButton.setBackgroundColor(Color.parseColor("#008000"));

                // set response for question
                quiz.setResponse(questionIndex, choice);
            });
        }
    }

}
