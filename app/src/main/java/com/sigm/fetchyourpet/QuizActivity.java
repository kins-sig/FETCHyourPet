package com.sigm.fetchyourpet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sigm.fetchyourpet.Question;

public class QuizActivity extends AppCompatActivity {

    // Need to decide how many questions our quiz will be, and allocate the correct amount of data (3*numOfQuestions)
    int[] values = new int [50];
    int currentQuestion = 0;
    ArrayList<Question> userQuestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        userQuestions.add(new Question(0, "How active do you want your dog to be?", "Very active","kinda active", "not active at all"));
        userQuestions.add(new Question(1, "How sleepy do you want your dog to be?", "Very sleepy","kinda sleepy", "not sleepy at all"));
        userQuestions.add(new Question(3, "How happy do you want your dog to be?", "Very happy","kinda happy", "not happy at all"));
        userQuestions.add(new Question(4, "How badly do you want to go to bed right now?", "I am sleep","Plz lemme sleep", "I simply cannot go on any longer"));


        TextView question = findViewById(R.id.question1);
        CheckBox q1Answer1 = findViewById(R.id.Q1Answer1);
        CheckBox q1Answer2 = findViewById(R.id.q1Answer2);
        CheckBox q1Answer3 = findViewById(R.id.q1Answer3);

        question.setText(userQuestions.get(0).questionText);

        q1Answer1.setText(userQuestions.get(0).answer1);
        q1Answer1.setChecked(false);
        q1Answer2.setText(userQuestions.get(0).answer2);
        q1Answer1.setChecked(false);
        q1Answer3.setText(userQuestions.get(0).answer3);
        q1Answer1.setChecked(false);
    }
    public void onClickNext(View view) {
        Log.d("PET", Integer.toString(currentQuestion));
        TextView question = findViewById(R.id.question1);
        CheckBox q1Answer1 = findViewById(R.id.Q1Answer1);
        CheckBox q1Answer2 = findViewById(R.id.q1Answer2);
        CheckBox q1Answer3 = findViewById(R.id.q1Answer3);


            if (q1Answer1.isChecked()) {
                values[currentQuestion * 3] = 1;
            } else values[currentQuestion* 3] = 0;

            if (q1Answer2.isChecked()) {
                values[currentQuestion * 3 + 1] = 1;
            } else values[currentQuestion * 3 + 1] = 0;
            if (q1Answer3.isChecked()) {
                values[currentQuestion * 3 + 2] = 1;
            } else values[currentQuestion * 3 + 2] = 0;

        currentQuestion ++;

        if(currentQuestion < userQuestions.size()) {



            question.setText(userQuestions.get(currentQuestion).questionText);
            q1Answer1.setText(userQuestions.get(currentQuestion).answer1);
            q1Answer1.setChecked(false);
            q1Answer2.setText(userQuestions.get(currentQuestion).answer2);
            q1Answer2.setChecked(false);
            q1Answer3.setText(userQuestions.get(currentQuestion).answer3);
            q1Answer3.setChecked(false);

        }
        else {
            Button next = findViewById(R.id.nextQuestion);
            next.setVisibility(View.INVISIBLE);
        }
    }

    public void onClickSubmitQuiz(View view){
        Log.d("PET", Arrays.toString(values));

    }
}
