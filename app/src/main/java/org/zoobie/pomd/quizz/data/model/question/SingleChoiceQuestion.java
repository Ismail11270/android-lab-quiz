package org.zoobie.pomd.quizz.data.model.question;

import android.os.Parcel;
import android.os.Parcelable;

public class SingleChoiceQuestion extends Question {

    public static final String TABLE_NAME = "SCquestion";
    public static final String COLUMN_QUESTION_BODY = "question_body";
    public static final String COLUMN_CORRECT_OPTION = "correct_option";
    public static final String COLUMN_SELECTED_OPTION = "selected_option";
    public static final String COLUMN_OPTIONS_TEXT = "options_text";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_QUESTION_BODY + " varchar(250)," +
            COLUMN_OPTIONS_TEXT + " varchar(250)," +
            COLUMN_CORRECT_OPTION + " INTEGER" +
            ")";

    private int correctOption;
    private String[] optionsText = new String[4];
    private int selectedOption = -1;

    public SingleChoiceQuestion(String questionBody, int correctOption, String[] optionsText) {
        super(questionBody, Type.SINGLE_OPTION);
        this.correctOption = correctOption;
        this.optionsText = optionsText;
    }

    public SingleChoiceQuestion(Parcel parcel){
//        type = Type.getTypeById(parcel.readInt());
        type = Type.SINGLE_OPTION;
        questionBody = parcel.readString();
        correctOption = parcel.readInt();
        selectedOption = parcel.readInt();
        parcel.readStringArray(optionsText);
    }
    public String[] getOptionsText(){
        return optionsText;
    }

    public int getCorrectOption(){
        return correctOption;
    }

    public int getSelectedOption(){
        return selectedOption;
    }
    public void setSelectedOption(int option){
        selectedOption = option;
    }

    public boolean isAnsweredCorrectly(){
        return correctOption == selectedOption;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type.id);
        dest.writeString(questionBody);
        dest.writeInt(correctOption);
        dest.writeInt(selectedOption);
        dest.writeStringArray(optionsText);
    }

    public static final Parcelable.Creator CREATOR = Question.CREATOR;
}
