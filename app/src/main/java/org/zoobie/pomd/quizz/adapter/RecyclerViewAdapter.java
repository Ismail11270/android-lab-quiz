package org.zoobie.pomd.quizz.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.zoobie.pomd.quizz.R;
import org.zoobie.pomd.quizz.data.model.question.Question;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<Question> questions;
    Context ctx;
    public int correctAnswersCounter = 0;

    public RecyclerViewAdapter(Context ctx, ArrayList<Question> questions){
        this.ctx = ctx;
        this.questions = questions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_result_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int size = ctx.getResources().getInteger(R.integer.answer_result_size);
        String title = position+1 + ") " + questions.get(position).getQuestionBody();
        if(title.length() > size) {
            title=title.substring(0,size-3);
            title+="...";
        }
        holder.questionTitle.setText(title);
        boolean correctAnswer = questions.get(position).isAnsweredCorrectly();
        if(correctAnswer)
        {
            holder.checkmarkImage.setImageResource(R.drawable.btn_check_buttonless_on);
            correctAnswersCounter++;
        }
        else holder.checkmarkImage.setImageResource(R.drawable.btn_check_buttonless_off);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView questionTitle;
        ImageView checkmarkImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            questionTitle = itemView.findViewById(R.id.questionTitle);
            checkmarkImage = itemView.findViewById(R.id.checkMark);
        }
    }
}
