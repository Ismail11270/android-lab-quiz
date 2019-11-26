package org.zoobie.pomd.quizz.data.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.widget.Switch;

import androidx.annotation.Nullable;

import org.zoobie.pomd.quizz.data.model.question.MultipleChoiceQuestion;
import org.zoobie.pomd.quizz.data.model.question.Question;
import org.zoobie.pomd.quizz.data.model.question.SingleChoiceQuestion;
import org.zoobie.pomd.quizz.data.model.question.SwitchQuestion;
import org.zoobie.pomd.quizz.data.model.question.ToggleQuestion;

import java.util.ArrayList;
import java.util.List;


public class QuestionsDbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;

    private static final String DB_NAME = "DB_QUESTIONS";

    private final String ARRAY_DIVIDER = "@#@";

    public QuestionsDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MultipleChoiceQuestion.CREATE_TABLE);
        db.execSQL(SingleChoiceQuestion.CREATE_TABLE);
        db.execSQL(SwitchQuestion.CREATE_TABLE);
        db.execSQL(ToggleQuestion.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MultipleChoiceQuestion.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SingleChoiceQuestion.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SwitchQuestion.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ToggleQuestion.TABLE_NAME);

        onCreate(db);
    }


    public List<Integer> getAllIds(Question.Type type) {
        SQLiteDatabase qdb = this.getWritableDatabase();
        String SELECT = "SELECT id FROM ";
        switch(type){
            case MULTIPLE_OPTION:
                SELECT+= MultipleChoiceQuestion.TABLE_NAME;
                break;
            case SINGLE_OPTION:
                SELECT+= SingleChoiceQuestion.TABLE_NAME;
                break;
            case SWITCH:
                SELECT+= SwitchQuestion.TABLE_NAME;
                break;
            case TOGGLE:
                SELECT+= ToggleQuestion.TABLE_NAME;
                break;
        }
        Cursor cursor = qdb.rawQuery(SELECT,null);
        List<Integer> idList = new ArrayList<>();
        if(cursor.moveToFirst()){
            idList.add(cursor.getInt(cursor.getColumnIndex("id")));
            while(cursor.moveToNext()){
                idList.add(cursor.getInt(cursor.getColumnIndex("id")));
            }
        }
        return idList;
    }

    public Question getQuestionById(Question.Type type){
        return null;
    }

    public Question getRandomQuestion(Question.Type type){
        return null;
    }

    public long addQuestion(Question question) {
        SQLiteDatabase qdb = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        switch (question.getType()) {
            case MULTIPLE_OPTION:
                values.put(MultipleChoiceQuestion.COLUMN_QUESTION_BODY, question.getQuestionBody());
                values.put(MultipleChoiceQuestion.COLUMN_OPTIONS_TEXT, serialize(
                        ((MultipleChoiceQuestion) question).getOptionsText()));
                values.put(MultipleChoiceQuestion.COLUMN_CORRECT_OPTIONS, serializeBoolean(
                        ((MultipleChoiceQuestion) question).getCorrectOptions()));
                return qdb.insert(MultipleChoiceQuestion.TABLE_NAME, null, values);

            case SINGLE_OPTION:
                values.put(SingleChoiceQuestion.COLUMN_QUESTION_BODY,
                        question.getQuestionBody());
                values.put(SingleChoiceQuestion.COLUMN_OPTIONS_TEXT, serialize(
                        ((SingleChoiceQuestion) question).getOptionsText()));
                values.put(SingleChoiceQuestion.COLUMN_CORRECT_OPTION,
                        ((SingleChoiceQuestion) question).getCorrectOption());
                return qdb.insert(SingleChoiceQuestion.TABLE_NAME, null, values);
            case SWITCH:
                values.put(SwitchQuestion.COLUMN_QUESTION_BODY,
                        question.getQuestionBody());
                values.put(SwitchQuestion.COLUMN_TEXT_ON,
                        ((SwitchQuestion)question).getOnText());
                values.put(SwitchQuestion.COLUMN_TEXT_OFF,
                        ((SwitchQuestion)question).getOffText());
                values.put(SwitchQuestion.COLUMN_CORRECT_ANSWER,
                        ((SwitchQuestion)question).getCorrectAnswer());
                return 1;
            case TOGGLE:
                values.put(ToggleQuestion.COLUMN_QUESTION_BODY,
                        question.getQuestionBody());
                values.put(ToggleQuestion.COLUMN_CORRECT_ANSWER,
                        ((ToggleQuestion)question).getCorrectAnswer());
                return 1;
            default:
                return -1;
        }
    }

    private String serialize(String[] arr) {
        return TextUtils.join(ARRAY_DIVIDER, arr);
    }

    private String[] deserialize(String arrayString) {
        return arrayString.split(ARRAY_DIVIDER);
    }

    private String serializeBoolean(boolean[] boolArr) {
        String[] arr = new String[boolArr.length];
        for (int i = 0; i < boolArr.length; i++) {
            arr[i] = "false";
            if (boolArr[i]) arr[i] = "true";
        }
        return serialize(arr);
    }

    private boolean[] deserializeBoolean(String arrayString) {
        String[] arr = deserialize(arrayString);
        boolean[] boolArr = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            boolArr[i] = false;
            if (arr[i].equals("true")) boolArr[i] = true;
        }
        return boolArr;
    }

}
