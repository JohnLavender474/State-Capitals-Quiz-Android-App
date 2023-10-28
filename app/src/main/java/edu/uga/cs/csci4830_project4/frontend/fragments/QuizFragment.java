package edu.uga.cs.csci4830_project4.frontend.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import edu.uga.cs.csci4830_project4.R;
import edu.uga.cs.csci4830_project4.frontend.quizzes.IQuizLogic;

public class QuizFragment extends Fragment {

    private final IQuizLogic quizLogic;

    public QuizFragment(IQuizLogic quizLogic, int questionIndex) {
        quizLogic.setCurrentQuestionIndex(questionIndex);
        this.quizLogic = quizLogic;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_activity_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        //public void onActivityCreated(Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView questionView = view.findViewById(R.id.tvQuestion);
        questionView.setText(quizLogic.getCurrentQuestion());

        Button choice1Button = view.findViewById(R.id.bChoice1);

        // TODO:
        /*
        TextView titleView = view.findViewById(R.id.titleView);
        TextView highlightsView = view.findViewById(R.id.highlightsView);

        titleView.setText(androidVersions[questionIndex]);
        highlightsView.setText(androidVersionsInfo[questionIndex]);
         */
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
