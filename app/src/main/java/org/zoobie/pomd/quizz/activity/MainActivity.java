package org.zoobie.pomd.quizz.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.zoobie.pomd.quizz.R;
import org.zoobie.pomd.quizz.fragment.MultipleChoiceQuestionFragment;
import org.zoobie.pomd.quizz.fragment.SingleChoiceQuestionFragment;
import org.zoobie.pomd.quizz.fragment.SwitchQuestionFragment;
import org.zoobie.pomd.quizz.fragment.ToggleQuestionFragment;
import org.zoobie.pomd.quizz.data.questions.MultipleChoiceQuestion;
import org.zoobie.pomd.quizz.data.questions.Question;
import org.zoobie.pomd.quizz.data.questions.SingleChoiceQuestion;
import org.zoobie.pomd.quizz.data.questions.SwitchQuestion;
import org.zoobie.pomd.quizz.data.questions.ToggleQuestion;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RESULTS_ACTIVITY_CODE = 101;
    public static final int RESULT_RESTART = 201;
    public static final int RESULT_EXIT = 202;
    private TextView questionsNumberTv, questionBodyTv;
    private Button nextQuestionButton, prevQuestionButton, submitButton, restartButton;

    private final int NUMBER_OF_QUESTIONS = 6;

    private FragmentManager fragmentManager;

//    private ArrayList<Question> questions;
    private ArrayList<Question> questions;
    private ArrayList<Fragment> questionFragments;

    private final int ID = R.id.fragmentHolder;


    private int currentQuestion = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        fragmentManager = getSupportFragmentManager();
        questions = new ArrayList<>();
        questionFragments = new ArrayList<>();

        fillQuestions();

        showQuestion(currentQuestion);

        nextQuestionButton.setOnClickListener(v -> {
            if(currentQuestion < NUMBER_OF_QUESTIONS-1) {
                currentQuestion++;
                showQuestion(currentQuestion);
            }
        });

        prevQuestionButton.setOnClickListener(v -> {
            if(currentQuestion > 0) {
                currentQuestion--;
                showQuestion(currentQuestion);
            }
        });
        restartButton.setOnClickListener(v ->{
            restartQuiz();
        });
        submitButton.setOnClickListener(this);
    }

    private void restartQuiz(){
        questions = new ArrayList<>();
        questionFragments = new ArrayList<>();
        fillQuestions();
        showQuestion(0);
        Toast.makeText(this, "Quiz restarted!", Toast.LENGTH_SHORT).show();
    }

    private void showQuestion(int i) {
        Question.Type type = questions.get(i).getType();
        int a = i+1;
        questionsNumberTv.setText("Question\n" + a + "/" + NUMBER_OF_QUESTIONS);
        if (type == Question.Type.SINGLE_OPTION) {
            showSingleChoiceQuestion(i);
        } else if (type == Question.Type.MULTIPLE_OPTION) {
            showMultipleChoiceQuestion(i);
        } else if (type == Question.Type.SWITCH) {
            showSwitchQuestion(i);
        } else if (type == Question.Type.TOGGLE) {
            showToggleQuestion(i);
        }
    }

    private void showToggleQuestion(int i) {
        questionBodyTv.setText(questions.get(i).getQuestionBody());

        fragmentManager.beginTransaction().replace(ID, questionFragments.get(i)).commit();
    }

    private void showSwitchQuestion(int i) {
        questionBodyTv.setText(questions.get(i).getQuestionBody());

        fragmentManager.beginTransaction().replace(ID, questionFragments.get(i)).commit();
    }

    private void showSingleChoiceQuestion(int i) {
        questionBodyTv.setText(questions.get(i).getQuestionBody());

        fragmentManager.beginTransaction().replace(ID, questionFragments.get(i)).commit();

//        for (int i = 0; i < 4; i++) {
//            optionsRadioButtons[i].setText(question.getOptions()[i]);
//        }
//        hideAllLayouts();
//        radioGroup.setVisibility(View.VISIBLE);


    }

    private void showMultipleChoiceQuestion(int i) {
        questionBodyTv.setText(questions.get(i).getQuestionBody());

        fragmentManager.beginTransaction().replace(ID, questionFragments.get(i)).commit();
    }


    private void fillQuestions() {
        Question question = new MultipleChoiceQuestion(
                "Select one of 4 answer to question 1",
                new String[] {"Option A",
                        "Option B",
                        "Option C",
                        "Option D"},
                2,3
        );
        questionFragments.add(MultipleChoiceQuestionFragment.newInstance(question));
        questions.add(question);

        question = new MultipleChoiceQuestion(
                "Select one of 4 answer to question 2",
                new String[] {"Option A",
                        "Option B",
                        "Option C",
                        "Option D"},
                0,3
        );
        questionFragments.add(MultipleChoiceQuestionFragment.newInstance(question));
        questions.add(question);

        question = new SingleChoiceQuestion(
                "Select one of 4 answer to question 3",
                1,
                new String[] {"Option A",
                "Option B",
                "Option C",
                "Option D"}
        );
        questionFragments.add(SingleChoiceQuestionFragment.newInstance(question));
        questions.add(question);

        question = new SingleChoiceQuestion(
                "Select one of 4 answer to question 4",
                2,
                new String[]{"Option AA",
                "Option BB",
                "Option CC",
                "Option DD"}
        );
        questions.add(question);
        questionFragments.add(SingleChoiceQuestionFragment.newInstance(question));

        question = new ToggleQuestion(
                "Are you real?",
                true
        );
        questions.add(question);
        questionFragments.add(ToggleQuestionFragment.newInstance(question));

        question = new SwitchQuestion(
                "Are you real?",
                "I am real",
                "I am not real",
                false
        );
        questions.add(question);
        questionFragments.add(SwitchQuestionFragment.newInstance(question));

    }

    private void findViews() {
        questionsNumberTv = findViewById(R.id.numberOfTasks);
        questionsNumberTv.setText("Question\n1/" + NUMBER_OF_QUESTIONS);

        questionBodyTv = findViewById(R.id.questionBody);
        nextQuestionButton = findViewById(R.id.nextButton);
        prevQuestionButton = findViewById(R.id.prevButton);
        restartButton = findViewById(R.id.restartQuiz);
        submitButton = findViewById(R.id.submitButton);
    }

    @Override
    public void onClick(View v) {
        for(Question q : questions){
            if(q.getType() == Question.Type.MULTIPLE_OPTION) {
                Log.i("MCQ", (Arrays.toString(((MultipleChoiceQuestion)q).getSelectedOptions()) + ""));
                Log.i("MCQ", (Arrays.toString(((MultipleChoiceQuestion)q).getCorrectOptions()) + ""));
            } else if(q.getType() == Question.Type.SINGLE_OPTION){
                Log.i("SCQ", ((SingleChoiceQuestion)q).getSelectedOption() + "");
                Log.i("SCQ", ((SingleChoiceQuestion)q).getCorrectOption() + "");
            } else if(q.getType() == Question.Type.TOGGLE){
                Log.i("TQ", ((ToggleQuestion)q).isToggleOn() + "");
                Log.i("TQ", ((ToggleQuestion)q).getCorrectAnswer()+ "");
            } else if(q.getType() == Question.Type.SWITCH){
                Log.i("SQ", ((SwitchQuestion)q).isSwitchOn() + "");
                Log.i("SQ", ((SwitchQuestion)q).getCorrectAnswer()+ "");
            }



        }
        Intent resultsIntent = new Intent(this,ResultActivity.class);
        resultsIntent.putParcelableArrayListExtra("questions",questions);
//            resultsIntent.putExtra("question",questions.get(0));
        startActivityForResult(resultsIntent,RESULTS_ACTIVITY_CODE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("RESUME");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        System.out.println("RESTART");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULTS_ACTIVITY_CODE) {
            if(resultCode == RESULT_RESTART) {
                restartQuiz();
            } else if(resultCode == RESULT_EXIT){
                finish();
            }
        }
    }
}