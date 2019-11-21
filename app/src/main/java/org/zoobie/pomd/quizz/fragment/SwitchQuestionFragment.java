package org.zoobie.pomd.quizz.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import org.zoobie.pomd.quizz.R;
import org.zoobie.pomd.quizz.data.questions.Question;
import org.zoobie.pomd.quizz.data.questions.SwitchQuestion;

public class SwitchQuestionFragment extends Fragment {
    private SwitchQuestion question;


    private Switch switchButton;
    private TextView switchAnswerOption;

    public SwitchQuestionFragment() {
    }

    public static SwitchQuestionFragment newInstance(Question question) {
        SwitchQuestionFragment fragment = new SwitchQuestionFragment();
        fragment.question = (SwitchQuestion)question;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_switch_question, container, false);

        switchButton = view.findViewById(R.id.switchButton);
        switchAnswerOption = view.findViewById(R.id.switchAnswerOption);

        switchAnswerOption.setText(question.getOffText());
        final String correctAnswer = question.getOnText();
        final String wrongAnswer = question.getOffText();

        switchButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
            {
                switchAnswerOption.setText(correctAnswer);
                question.setSwitchOn(true);
            }
            else {
                switchAnswerOption.setText(wrongAnswer);
                question.setSwitchOn(false);
            }
        });

        return view;
    }

    public Question getQuestion(){
        return question;
    }
}
