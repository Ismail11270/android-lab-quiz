package org.zoobie.pomd.quizz.data.questions;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

public class MultipleChoiceQuestion extends Question {

    private int[] correctOptions;

    private boolean[] selectedOptions = {false, false, false, false};

    public String[] optionsText = new String[4];

    public MultipleChoiceQuestion(String questionBody, String[] options, int... correctOptions) {
        super(questionBody,Type.MULTIPLE_OPTION);
        optionsText = options;
        this.correctOptions = correctOptions;
    }

    public MultipleChoiceQuestion(Parcel parcel){
//        int i = parcel.readInt();
//        System.out.println(i+" ");
//        type = Type.getTypeById(i);
        type = Type.MULTIPLE_OPTION;
        questionBody = parcel.readString();
        System.out.println(questionBody);
        int a = parcel.readInt();
        correctOptions = new int[a];
        parcel.readIntArray(correctOptions);
        parcel.readBooleanArray(selectedOptions);
        parcel.readStringArray(optionsText);
    }
    public String[] getOptionsText(){
        return optionsText;
    }
    public boolean[] getSelectedOptions(){
        return selectedOptions;
    }
    public int[] getCorrectOptions(){
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
        dest.writeInt(correctOptions.length);
        dest.writeIntArray(correctOptions);
        dest.writeBooleanArray(selectedOptions);
        dest.writeStringArray(optionsText);
    }

    public boolean isAnsweredCorrectly(){
        int j = correctOptions.length;
        for(int i = 0; i < 4; i++){
            if(selectedOptions[i]) {
                for(int a : correctOptions){
                    if(a == i) {
                        j--;
                        continue;
                    }
                }
                break;
            }
        }
        return j==1;
    }
}
