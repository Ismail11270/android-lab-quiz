package org.zoobie.pomd.quizz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import org.zoobie.pomd.quizz.R;
import org.zoobie.pomd.quizz.data.model.question.MultipleChoiceQuestion;
import org.zoobie.pomd.quizz.data.model.question.Question;
import org.zoobie.pomd.quizz.data.model.question.SingleChoiceQuestion;
import org.zoobie.pomd.quizz.data.model.question.SwitchQuestion;
import org.zoobie.pomd.quizz.data.model.question.ToggleQuestion;
import org.zoobie.pomd.quizz.data.sql.QuestionsDbHelper;
import org.zoobie.pomd.quizz.fragment.MultipleChoiceQuestionFragment;
import org.zoobie.pomd.quizz.fragment.SingleChoiceQuestionFragment;
import org.zoobie.pomd.quizz.fragment.SwitchQuestionFragment;
import org.zoobie.pomd.quizz.fragment.ToggleQuestionFragment;

public class EntryActivity extends AppCompatActivity {

    SharedPreferences preferences;

    private final String TAG = "ENTRY_ACTIVITY";

    private QuestionsDbHelper questionsDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        preferences = getSharedPreferences("org.zoobie.pomd.quizz", MODE_PRIVATE);
        if (preferences.getBoolean("first-run", true)) {
            preferences.edit().putBoolean("first-run", false).apply();
            Log.i(TAG, "First run");
            fillQuestionsDb();
        } else Log.i(TAG, "Repeated run");

        startActivity(new Intent(this, QuizActivity.class));

    }

    private void fillQuestionsDb() {
        questionsDbHelper = new QuestionsDbHelper(this);

        Question question = new MultipleChoiceQuestion(
                "Which are the names of mobile operating systems?",
                new String[] {"BreadOS",
                        "IOS",
                        "Android",
                        "Linux"},
                new boolean[]{
                        false,true,true,false
                }
        );
        questionsDbHelper.addQuestion(question);

        question = new MultipleChoiceQuestion(
                "Which are the versions of Windows operation system?",
                new String[] {"Windows  Me",
                        "Windows 8.1",
                        "Windows 98",
                        "Windows 95"},
                new boolean[]{
                        true,true,true,true
                }
        );
        questionsDbHelper.addQuestion(question);

        question = new SingleChoiceQuestion(
                "Why was this quiz created?",
                0,
                new String[] {"University assignment",
                        "Author had a lot of free time",
                        "To get to know you better",
                        "Making quizzes is fun"}
        );
        questionsDbHelper.addQuestion(question);

        question = new SingleChoiceQuestion(
                "What is the most popular programming language for android development",
                3,
                new String[]{"C++",
                        "Python",
                        "PHP",
                        "Java"}
        );
        questionsDbHelper.addQuestion(question);
        question = new SingleChoiceQuestion(
                "What does SUT stand for?",
                1,
                new String[]{"System Under Test",
                        "Silesian University of Techology",
                        "Simply United Together",
                        "Service Unit Teams"}
        );
        questionsDbHelper.addQuestion(question);

        question = new ToggleQuestion(
                "Do you like coding?",
                true
        );
        questionsDbHelper.addQuestion(question);

        question = new ToggleQuestion(
                "Is winter cold?",
                true
        );
        questionsDbHelper.addQuestion(question);

        question = new SwitchQuestion(
                "Java vs Kotlin",
                "Kotlin",
                "Java",
                true
        );
        questionsDbHelper.addQuestion(question);
    }


}
