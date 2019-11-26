package org.zoobie.pomd.quizz.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import org.zoobie.pomd.quizz.R;
import org.zoobie.pomd.quizz.data.model.question.MultipleChoiceQuestion;
import org.zoobie.pomd.quizz.data.model.question.Question;

public class MultipleChoiceQuestionFragment extends Fragment implements View.OnClickListener {

    private CheckBox[] checkBoxes;
    private MultipleChoiceQuestion question;

    private boolean[] checked = {false, false, false, false};

    public static MultipleChoiceQuestionFragment newInstance(Question question) {
        MultipleChoiceQuestionFragment fragment = new MultipleChoiceQuestionFragment();
        fragment.question = (MultipleChoiceQuestion)question;
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.multiple_choice_question_fragment, container, false);


        checkBoxes = new CheckBox[4];
        for (int i = 0; i < 4; i++) {
            checkBoxes[i] = view.findViewWithTag(i + "");
            checkBoxes[i].setText(question.getOptionsText()[i]);
            checkBoxes[i].setOnClickListener(this);
        }

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        for(int i = 0; i < checkBoxes.length; i++){
            checkBoxes[i].setChecked(question.getSelectedOptions()[i]);
        }
    }

    @Override
    public void onClick(View v) {
        CheckBox cb = (CheckBox)v;
        question.setSelectedOption(Integer.parseInt(cb.getTag().toString()),
                cb.isChecked());
    }

    public Question getQuestion(){
        return question;
    }
}
