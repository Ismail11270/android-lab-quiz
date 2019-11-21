package org.zoobie.pomd.quizz.data.questions;

import android.os.Parcel;
import android.os.Parcelable;

public class ToggleQuestion extends Question {

    private boolean correctAnswer;
    private boolean toggleOn = false;

    public ToggleQuestion(String questionBody, boolean b) {
        super(questionBody,Type.TOGGLE);
        correctAnswer = b;
    }

    public ToggleQuestion(Parcel parcel){
//        type = Type.getTypeById(parcel.readInt());
        type = Type.TOGGLE;
        questionBody = parcel.readString();
        boolean[] arr = new boolean[2];
        parcel.readBooleanArray(arr);
        toggleOn = arr[0];
        correctAnswer = arr[1];
    }

    public boolean getCorrectAnswer(){
        return correctAnswer;
    }
    public void setToggleOn(boolean toggleOn){
        this.toggleOn = toggleOn;
    }


    public boolean isToggleOn(){
        return toggleOn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type.id);
        dest.writeString(questionBody);
        dest.writeBooleanArray(new boolean[] {toggleOn,correctAnswer});
    }

    public static final Parcelable.Creator CREATOR = Question.CREATOR;

    @Override
    public boolean isAnsweredCorrectly() {
        return toggleOn==correctAnswer;
    }
}
