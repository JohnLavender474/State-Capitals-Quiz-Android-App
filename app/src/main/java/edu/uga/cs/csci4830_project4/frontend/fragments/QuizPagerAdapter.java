package edu.uga.cs.csci4830_project4.frontend.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import edu.uga.cs.csci4830_project4.frontend.quizzes.IQuiz;

public class QuizPagerAdapter extends FragmentStateAdapter {

    private final IQuiz quiz;

    public QuizPagerAdapter(IQuiz quiz,
                            @NonNull FragmentManager fragmentManager,
                            @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.quiz = quiz;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return QuizFragment.newInstance(quiz, position);
    }

    @Override
    public int getItemCount() {
        return quiz.getSizeOfQuiz();
    }
}
