package org.zoobie.pomd.quizz.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.zoobie.pomd.quizz.R;
import org.zoobie.pomd.quizz.data.questions.Question;
import org.zoobie.pomd.quizz.data.questions.SingleChoiceQuestion;


public class SingleChoiceQuestionFragment extends Fragment {

    private SingleChoiceQuestion question;

    private RadioGroup group;
    private RadioButton[] radioButtons;

    public SingleChoiceQuestionFragment() {
    }

    public static SingleChoiceQuestionFragment newInstance(Question question) {
        SingleChoiceQuestionFragment fragment = new SingleChoiceQuestionFragment();
        fragment.question = (SingleChoiceQuestion) question;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_single_choice_question, container, false);

        radioButtons = new RadioButton[4];
        group = view.findViewById(R.id.radioGroup);
        for (int i = 0; i < 4; i++) {
            radioButtons[i] = group.findViewWithTag(i + "");
            radioButtons[i].setText(question.getOptionsText()[i]);
            radioButtons[i].setOnClickListener(v -> {
                question.setSelectedOption(Integer.parseInt(v.getTag().toString()));
//                Toast.makeText(getContext(),
//                        v.getTag().toString() + " " + question.getSelectedOption(),
//                        Toast.LENGTH_SHORT).show();
            });

        }
        return view;
    }

    public Question getQuestion(){
        return question;
    }
}
