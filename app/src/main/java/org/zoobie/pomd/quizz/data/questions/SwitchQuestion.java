package org.zoobie.pomd.quizz.data.questions;

import android.os.Parcel;
import android.os.Parcelable;

public class SwitchQuestion extends Question {

    private final String onText, offText;
    private boolean switchOn = false;
    private boolean correctAnswer;

    public SwitchQuestion(String questionBody, String onText, String offText, boolean correctAnswer) {
        super(questionBody,Type.SWITCH);
        this.onText = onText;
        this.offText = offText;
        this.correctAnswer = correctAnswer;
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
