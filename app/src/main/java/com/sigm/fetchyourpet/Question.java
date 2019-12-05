package com.sigm.fetchyourpet;

/**
 * Models the Question object
 * @author Kinsley
 */
public class Question {

    int questionID;
    String questionText;
    String answer1;
    boolean answer1check;
    String answer2;
    boolean answer2check;
    String answer3;
    boolean answer3check;

    /**
     *
     * @param questionID - the id of the question
     * @param questionText - The string
     * @param answer1 - the first answer choice
     * @param answer2 - the second answer choice
     * @param answer3 - the third answer choice
     */
    public Question(int questionID, String questionText, String answer1, String answer2, String answer3) {
        this.questionID = questionID;
        this.questionText = questionText;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
    }

    public void setAnswer1check(boolean answer1check) {
        this.answer1check = answer1check;
    }

    public void setAnswer2check(boolean answer2check) {
        this.answer2check = answer2check;
    }

    public void setAnswer3check(boolean answer3check) {
        this.answer3check = answer3check;
    }
}
