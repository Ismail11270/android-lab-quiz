package org.zoobie.pomd.quizz.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.zoobie.pomd.quizz.R;
import org.zoobie.pomd.quizz.adapter.RecyclerViewAdapter;
import org.zoobie.pomd.quizz.data.model.question.Question;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    private Button exitButton, restartButton;
    private TextView resultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        ArrayList<Question> questions = intent.getParcelableArrayListExtra("questions");

        //find views
        recyclerView = findViewById(R.id.recyclerView);
        exitButton = findViewById(R.id.buttonExit);
        restartButton = findViewById(R.id.buttonRestart);
        resultTv = findViewById(R.id.results);

        adapter = new RecyclerViewAdapter(getApplicationContext(), questions);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //calculate result
        int x = 0;
        for (Question q : questions) {
            boolean correctAnswer = q.isAnsweredCorrectly();
            if (correctAnswer) {
                x++;
            }
        }
        String result = x + "/" + questions.size();
        resultTv.setText(result);


        //buttons
        exitButton.setOnClickListener(v -> {
            setResult(MainActivity.RESULT_EXIT);
            finish();
        });

        restartButton.setOnClickListener(v -> {
            setResult(MainActivity.RESULT_RESTART);
            finish();
        });


    }
}
