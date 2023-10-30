package edu.uga.cs.csci4830_project4.frontend.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import edu.uga.cs.csci4830_project4.backend.contracts.IAccess;
import edu.uga.cs.csci4830_project4.backend.quizzes.QuizModel;
import edu.uga.cs.csci4830_project4.backend.scores.ScoreModel;
import edu.uga.cs.csci4830_project4.frontend.quizzes.IQuiz;

public class QuizPagerAdapter extends FragmentStateAdapter {

    private final IQuiz quiz;
    private final IAccess<QuizModel> quizzesAccess;
    private final IAccess<ScoreModel> scoresAccess;
    private final List<Integer> imageSources;

    public QuizPagerAdapter(IAccess<QuizModel> quizzesAccess, IAccess<ScoreModel> scoresAccess,
                            IQuiz quiz, List<Integer> imageSources,
                            @NonNull FragmentManager fragmentManager,
                            @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.quiz = quiz;
        this.quizzesAccess = quizzesAccess;
        this.scoresAccess = scoresAccess;
        this.imageSources = imageSources;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position < 0) {
            throw new IllegalArgumentException("Position cannot be less than 0");
        }

        if (position < quiz.getSizeOfQuiz()) {
            return QuizFragment.newInstance(quiz, position, imageSources.get(position));
        } else if (position == quiz.getSizeOfQuiz()) {
            return SubmitQuizFragment.newInstance(quiz.getQuizDTO(), quizzesAccess, scoresAccess);
        }

        throw new IllegalArgumentException("Position cannot be greater than the size of the quiz");
    }

    @Override
    public int getItemCount() {
        return quiz.getSizeOfQuiz();
    }
}
