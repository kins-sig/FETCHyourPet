package com.sigm.fetchyourpet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Sign In");
    }

    public void onClickSignIn(View view){
        Intent dashboard = new Intent(this, Dashboard.class);
        startActivity(dashboard);
    }
}
