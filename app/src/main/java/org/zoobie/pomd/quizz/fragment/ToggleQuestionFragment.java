package org.zoobie.pomd.quizz.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.zoobie.pomd.quizz.R;
import org.zoobie.pomd.quizz.data.questions.Question;
import org.zoobie.pomd.quizz.data.questions.ToggleQuestion;

public class ToggleQuestionFragment extends Fragment implements View.OnClickListener {
    private ToggleQuestion question;
    private ToggleButton toggleButton;

    public static ToggleQuestionFragment newInstance(Question question) {
        ToggleQuestionFragment fragment = new ToggleQuestionFragment();
        fragment.question = (ToggleQuestion)question;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_toggle_question, container, false);

        toggleButton = view.findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        question.setToggleOn(((ToggleButton)v).isChecked());
//        Toast.makeText(getContext(), question.isToggleOn()+"", Toast.LENGTH_SHORT).show();
    }

    public Question getQuestion(){
        return question;
    }
}
