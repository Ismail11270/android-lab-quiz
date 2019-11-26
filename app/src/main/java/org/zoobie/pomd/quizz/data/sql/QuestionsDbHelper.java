package org.zoobie.pomd.quizz.data.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;
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
        switch (type) {
            case MULTIPLE_OPTION:
                SELECT += MultipleChoiceQuestion.TABLE_NAME;
                break;
            case SINGLE_OPTION:
                SELECT += SingleChoiceQuestion.TABLE_NAME;
                break;
            case SWITCH:
                SELECT += SwitchQuestion.TABLE_NAME;
                break;
            case TOGGLE:
                SELECT += ToggleQuestion.TABLE_NAME;
                break;
        }
        Cursor cursor = qdb.rawQuery(SELECT, null);
        List<Integer> idList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            idList.add(cursor.getInt(cursor.getColumnIndex("id")));
            while (cursor.moveToNext()) {
                idList.add(cursor.getInt(cursor.getColumnIndex("id")));
            }
        }
        return idList;
    }

    public Question getQuestionById(Question.Type type, int id) {
        SQLiteDatabase qdb = this.getWritableDatabase();
        Cursor c;
        Question question = null;
        switch (type) {
            case MULTIPLE_OPTION:
                c = qdb.query(
                        MultipleChoiceQuestion.TABLE_NAME,
                        new String[]{"id", MultipleChoiceQuestion.COLUMN_QUESTION_BODY, MultipleChoiceQuestion.COLUMN_OPTIONS_TEXT, MultipleChoiceQuestion.COLUMN_CORRECT_OPTIONS},
                        "id=?",
                        new String[]{id + ""},
                        null, null, null, null);
                if (c != null) {
                    c.moveToFirst();
                    question = new MultipleChoiceQuestion(
                            c.getInt(c.getColumnIndex("id")),
                            c.getString(c.getColumnIndex(MultipleChoiceQuestion.COLUMN_QUESTION_BODY)),
                            deserialize(c.getString(c.getColumnIndex(MultipleChoiceQuestion.COLUMN_OPTIONS_TEXT))),
                            deserializeBoolean(c.getString(c.getColumnIndex(MultipleChoiceQuestion.COLUMN_CORRECT_OPTIONS))));
                    c.close();
                }
                break;

            case SINGLE_OPTION:
                c = qdb.query(
                        SingleChoiceQuestion.TABLE_NAME,
                        new String[]{"id", SingleChoiceQuestion.COLUMN_QUESTION_BODY, SingleChoiceQuestion.COLUMN_OPTIONS_TEXT, SingleChoiceQuestion.COLUMN_CORRECT_OPTION},
                        "id=?",
                        new String[]{id + ""},
                        null, null, null, null);
                if (c != null) {
                    c.moveToFirst();
                    question = new SingleChoiceQuestion(
                            c.getInt(c.getColumnIndex("id")),
                            c.getString(c.getColumnIndex(SingleChoiceQuestion.COLUMN_QUESTION_BODY)),
                            deserialize(c.getString(c.getColumnIndex(SingleChoiceQuestion.COLUMN_OPTIONS_TEXT))),
                            c.getInt(c.getColumnIndex(SingleChoiceQuestion.COLUMN_CORRECT_OPTION)));
                    c.close();
                }
                break;
            case SWITCH:
                c = qdb.query(
                        SwitchQuestion.TABLE_NAME,
                        new String[]{"id", SwitchQuestion.COLUMN_QUESTION_BODY, SwitchQuestion.COLUMN_TEXT_ON, SwitchQuestion.COLUMN_TEXT_ON, SwitchQuestion.COLUMN_CORRECT_ANSWER},
                        "id=?",
                        new String[]{id + ""},
                        null, null, null, null);
                if (c != null) {
                    c.moveToFirst();
                    question = new SwitchQuestion(
                            c.getInt(c.getColumnIndex("id")),
                            c.getString(c.getColumnIndex(SwitchQuestion.COLUMN_QUESTION_BODY)),
                            c.getString(c.getColumnIndex(SwitchQuestion.COLUMN_TEXT_ON)),
                            c.getString(c.getColumnIndex(SwitchQuestion.COLUMN_TEXT_OFF)),
                            c.getInt(c.getColumnIndex(SwitchQuestion.COLUMN_CORRECT_ANSWER)));
                }
                break;
            case TOGGLE:
                c = qdb.query(
                        ToggleQuestion.TABLE_NAME,
                        new String[]{"id", ToggleQuestion.COLUMN_QUESTION_BODY, ToggleQuestion.COLUMN_CORRECT_ANSWER},
                        "id=?",
                        new String[]{id + ""},
                        null, null, null, null);
                if (c != null) {
                    c.moveToFirst();
                    question = new ToggleQuestion(
                            c.getInt(c.getColumnIndex("id")),
                            c.getString(c.getColumnIndex(ToggleQuestion.COLUMN_QUESTION_BODY)),
                            c.getInt(c.getColumnIndex(ToggleQuestion.COLUMN_CORRECT_ANSWER)));
                }
                break;
        }
        return question;
    }

    public List<Question> getAllQuestions(Question.Type type) {
        SQLiteDatabase qdb = this.getWritableDatabase();
        List<Question> questions = new ArrayList<>();
        Cursor c;

        switch (type) {
            case MULTIPLE_OPTION: {
                c = qdb.rawQuery("SELECT * FROM " + MultipleChoiceQuestion.TABLE_NAME, null);
                if (c.moveToFirst()) {
                    do {
                        Question question = new MultipleChoiceQuestion(
                                c.getInt(c.getColumnIndex("id")),
                                c.getString(c.getColumnIndex(MultipleChoiceQuestion.COLUMN_QUESTION_BODY)),
                                deserialize(c.getString(c.getColumnIndex(MultipleChoiceQuestion.COLUMN_OPTIONS_TEXT))),
                                deserializeBoolean(c.getString(c.getColumnIndex(MultipleChoiceQuestion.COLUMN_CORRECT_OPTIONS))));
                        questions.add(question);
                    } while (c.moveToNext());
                    c.close();
                }
                break;
            }
            case SINGLE_OPTION:
                c = qdb.rawQuery("SELECT * FROM " + SingleChoiceQuestion.TABLE_NAME, null);
                if (c.moveToFirst()) {
                    do {
                        Question question = new SingleChoiceQuestion(
                                c.getInt(c.getColumnIndex("id")),
                                c.getString(c.getColumnIndex(SingleChoiceQuestion.COLUMN_QUESTION_BODY)),
                                deserialize(c.getString(c.getColumnIndex(SingleChoiceQuestion.COLUMN_OPTIONS_TEXT))),
                                c.getInt(c.getColumnIndex(SingleChoiceQuestion.COLUMN_CORRECT_OPTION)));
                        questions.add(question);
                    } while (c.moveToNext());
                    c.close();
                }
                break;
            case SWITCH:
                c = qdb.rawQuery("SELECT * FROM " + SwitchQuestion.TABLE_NAME, null);
                if (c.moveToFirst()) {
                    do {
                        Log.i("swetch", "1");
                        Question question = new SwitchQuestion(
                                c.getInt(c.getColumnIndex("id")),
                                c.getString(c.getColumnIndex(SwitchQuestion.COLUMN_QUESTION_BODY)),
                                c.getString(c.getColumnIndex(SwitchQuestion.COLUMN_TEXT_ON)),
                                c.getString(c.getColumnIndex(SwitchQuestion.COLUMN_TEXT_OFF)),
                                c.getInt(c.getColumnIndex(SwitchQuestion.COLUMN_CORRECT_ANSWER)));
                        questions.add(question);
                        Log.i("swetch", question.getQuestionBody());
                    } while (c.moveToNext());
                }
                break;
            case TOGGLE:
                c = qdb.rawQuery("SELECT * FROM " + ToggleQuestion.TABLE_NAME, null);
                if (c.moveToFirst()) {
                    do {
                        Log.i("swetch", "1");
                        Question question = new ToggleQuestion(
                                c.getInt(c.getColumnIndex("id")),
                                c.getString(c.getColumnIndex(ToggleQuestion.COLUMN_QUESTION_BODY)),
                                c.getInt(c.getColumnIndex(ToggleQuestion.COLUMN_CORRECT_ANSWER)));
                        questions.add(question);
                        Log.i("swetch", question.getQuestionBody());
                    } while (c.moveToNext());
                }
                break;
        }
        return questions;

    }

    public List<Question> getAllQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.addAll(getAllQuestions(Question.Type.MULTIPLE_OPTION));
        questions.addAll(getAllQuestions(Question.Type.SINGLE_OPTION));
        questions.addAll(getAllQuestions(Question.Type.SWITCH));
        questions.addAll(getAllQuestions(Question.Type.TOGGLE));
        return questions;
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
                        ((SwitchQuestion) question).getOnText());
                values.put(SwitchQuestion.COLUMN_TEXT_OFF,
                        ((SwitchQuestion) question).getOffText());
                values.put(SwitchQuestion.COLUMN_CORRECT_ANSWER,
                        ((SwitchQuestion) question).getCorrectAnswer() ? 1 : 0);
                return qdb.insert(SwitchQuestion.TABLE_NAME, null, values);
            case TOGGLE:
                values.put(ToggleQuestion.COLUMN_QUESTION_BODY,
                        question.getQuestionBody());
                values.put(ToggleQuestion.COLUMN_CORRECT_ANSWER,
                        ((ToggleQuestion) question).getCorrectAnswer() ? 1 : 0);
                return qdb.insert(ToggleQuestion.TABLE_NAME, null, values);
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
