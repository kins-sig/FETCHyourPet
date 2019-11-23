package com.sigm.fetchyourpet;

public class Question {

    int questionID;
    String questionText;
    String answer1;
    String answer2;
    String answer3;


    public Question(int questionID, String questionText, String answer1, String answer2, String answer3) {
        this.questionID = questionID;
        this.questionText = questionText;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
    }

}
