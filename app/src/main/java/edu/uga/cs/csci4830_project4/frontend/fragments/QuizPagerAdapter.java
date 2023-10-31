package edu.uga.cs.csci4830_project4.frontend.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

import edu.uga.cs.csci4830_project4.frontend.quizzes.IQuiz;

/**
 * This class provides a pager adapter for a quiz.
 */
public class QuizPagerAdapter extends FragmentStateAdapter {

    private final IQuiz quiz;
    private final List<Integer> imageSources;

    /**
     * Constructs a new {@link QuizPagerAdapter} instance.
     *
     * @param quiz            the quiz.
     * @param imageSources    the image sources.
     * @param fragmentManager the fragment manager.
     * @param lifecycle       the lifecycle.
     */
    public QuizPagerAdapter(IQuiz quiz, List<Integer> imageSources,
                            @NonNull FragmentManager fragmentManager,
                            @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.quiz = quiz;
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
            return SubmitQuizFragment.newInstance(quiz.getQuizDTO());
        }

        throw new IllegalArgumentException("Position cannot be greater than the size of the quiz");
    }

    @Override
    public int getItemCount() {
        return quiz.getSizeOfQuiz() + 1;
    }
}
