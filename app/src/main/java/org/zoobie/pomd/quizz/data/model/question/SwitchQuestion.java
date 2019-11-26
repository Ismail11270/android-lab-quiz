package org.zoobie.pomd.quizz.data.model.question;

import android.os.Parcel;
import android.os.Parcelable;

public class SwitchQuestion extends Question {

    public static final String TABLE_NAME = "SWquestion";
    public static final String COLUMN_QUESTION_BODY = "question_body";
    public static final String COLUMN_TEXT_ON = "text_on";
    public static final String COLUMN_TEXT_OFF = "text_off";
    public static final String COLUMN_CORRECT_ANSWER = "correct_answer";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_QUESTION_BODY + " varchar(250)," +
            COLUMN_TEXT_ON + " varchar(50)," +
            COLUMN_TEXT_OFF + " varchar(50)," +
            COLUMN_CORRECT_ANSWER + " INTEGER " +
            ")";

    private final String onText, offText;
    private boolean switchOn = false;
    private boolean correctAnswer;

    public SwitchQuestion(String questionBody, String onText, String offText, boolean correctAnswer) {
        super(questionBody,Type.SWITCH);
        this.onText = onText;
        this.offText = offText;
        this.correctAnswer = correctAnswer;
    }

    public SwitchQuestion(int id, String questionBody, String onText, String offText, int correctAnswer){
        this(questionBody,onText,offText,false);
        if(correctAnswer==1) this.correctAnswer = true;
        this.id = id;
    }
    public SwitchQuestion(Parcel parcel){
//        type = Type.getTypeById(parcel.readInt());
        type = Type.SWITCH;
        questionBody = parcel.readString();
        onText = parcel.readString();
        offText = parcel.readString();
        boolean[] arr = new boolean[2];
        parcel.readBooleanArray(arr);
        switchOn = arr[0];
        correctAnswer = arr[1];
    }

    public String getOnText() {
        return onText;
    }

    public String getOffText() {
        return offText;
    }

    public boolean getCorrectAnswer(){
        return correctAnswer;
    }
    public int getCorrectIntAnswer(){
        return correctAnswer ? 1 : 0;
    }
    public void setSwitchOn(boolean switchOn){
        this.switchOn = switchOn;
    }

    public boolean isSwitchOn(){
        return switchOn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type.id);
        dest.writeString(questionBody);
        dest.writeString(onText);
        dest.writeString(offText);
        dest.writeBooleanArray(new boolean[] {switchOn,correctAnswer});
    }

    public static final Parcelable.Creator CREATOR = Question.CREATOR;

    @Override
    public boolean isAnsweredCorrectly() {
        return switchOn==correctAnswer;
    }
}
