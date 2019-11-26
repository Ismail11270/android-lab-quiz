package org.zoobie.pomd.quizz.data.model.question;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;


public class MultipleChoiceQuestion extends Question {

    public static final String TABLE_NAME = "MCquestion";
    public static final String COLUMN_QUESTION_BODY = "question_body";
    public static final String COLUMN_CORRECT_OPTIONS = "correct_options";
    public static final String COLUMN_SELECTED_OPTIONS = "selected_options";
    public static final String COLUMN_OPTIONS_TEXT = "options_text";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_QUESTION_BODY + " varchar(250)," +
            COLUMN_OPTIONS_TEXT + " varchar(250)," +
            COLUMN_CORRECT_OPTIONS + " varchar(50) " +
            ")";

    private boolean[] correctOptions = new boolean[4];

    private boolean[] selectedOptions = {false, false, false, false};


    public String[] optionsText = new String[4];

    public MultipleChoiceQuestion(String questionBody, String[] options, boolean[] correctOptions) {
        super(questionBody,Type.MULTIPLE_OPTION);
        optionsText = options;
        this.correctOptions = correctOptions;
    }

    public MultipleChoiceQuestion(Parcel parcel){
        type = Type.MULTIPLE_OPTION;
        questionBody = parcel.readString();
        parcel.readBooleanArray(correctOptions);
        parcel.readBooleanArray(selectedOptions);
        parcel.readStringArray(optionsText);
    }
    public String[] getOptionsText(){
        return optionsText;
    }
    public boolean[] getSelectedOptions(){
        return selectedOptions;
    }
    public boolean[] getCorrectOptions(){
        return correctOptions;
    }
    public void setSelectedOption(int i, boolean selected){
        selectedOptions[i] = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR = Question.CREATOR;
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type.id);

        dest.writeString(questionBody);
        dest.writeBooleanArray(correctOptions);
        dest.writeBooleanArray(selectedOptions);
        dest.writeStringArray(optionsText);
    }

    //{true,true,true,true}
    //0,1,2,3 correct
    public boolean isAnsweredCorrectly(){
        return Arrays.equals(correctOptions,selectedOptions);
    }
}
