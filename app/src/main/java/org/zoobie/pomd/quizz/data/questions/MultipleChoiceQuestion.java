package org.zoobie.pomd.quizz.data.questions;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;


public class MultipleChoiceQuestion extends Question {

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
