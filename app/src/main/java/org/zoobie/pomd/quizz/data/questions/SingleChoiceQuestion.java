package org.zoobie.pomd.quizz.data.questions;

import android.os.Parcel;
import android.os.Parcelable;

public class SingleChoiceQuestion extends Question {
    private int correctOption;
    private String[] optionsText = new String[4];
    private int selectedOption;

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
