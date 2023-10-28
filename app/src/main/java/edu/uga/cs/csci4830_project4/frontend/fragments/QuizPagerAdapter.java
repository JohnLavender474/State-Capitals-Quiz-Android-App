package edu.uga.cs.csci4830_project4.frontend.fragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import edu.uga.cs.csci4830_project4.frontend.quizzes.IQuizLogic;

public class QuizPagerAdapter extends FragmentStateAdapter {

    private final IQuizLogic quizLogic;

    public QuizPagerAdapter(IQuizLogic quizLogic,
                            @NonNull FragmentManager fragmentManager,
                            @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.quizLogic = quizLogic;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return null;
    }

    @Override
    public int getItemCount() {
        return quizLogic.getSizeOfQuiz();
    }
}
