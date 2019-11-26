package org.zoobie.pomd.quizz.data.model.question;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class Question implements Parcelable {
    String questionBody;
    Type type;

    Question(String questionBody, Type type) {
        this.questionBody = questionBody;
        this.type = type;
    }

    protected Question() {
    }


    public abstract boolean isAnsweredCorrectly();

    public String getQuestionBody(){
        return questionBody;
    }

    public Type getType(){
        return type;
    }

    public enum Type {
        SINGLE_OPTION(0), MULTIPLE_OPTION(1), TOGGLE(2), SWITCH(3);
        int id;
        Type(int id){
            this.id = id;
        }

        public static Type getTypeById(int id) {
            switch(id){
                case 0:
                    return SINGLE_OPTION;
                case 1:
                    return MULTIPLE_OPTION;
                case 2:
                    return TOGGLE;
                case 3:
                    return SWITCH;
                default:
                    return null;
            }
        }
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Question>() {

        @Override
        public Question createFromParcel(Parcel source) {
            int typeId = source.readInt();
            Type type = Type.getTypeById(typeId);
            switch(type){
                case MULTIPLE_OPTION:
                    return new MultipleChoiceQuestion(source);
                case SINGLE_OPTION:
                    return  new SingleChoiceQuestion(source);
                case TOGGLE:
                    return new ToggleQuestion(source);
                case SWITCH:
                    return new SwitchQuestion(source);
                default:
                    return null;
            }
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[0];
        }
    };


}
