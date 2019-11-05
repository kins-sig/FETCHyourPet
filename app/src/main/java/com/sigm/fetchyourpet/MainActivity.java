package com.sigm.fetchyourpet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void onClickSignIn(View view) {
        Intent signin = new Intent(this, SignInActivity.class);
        startActivity(signin);
    }


    public void onClickSignUp(View view) {
        Intent signup = new Intent(this, SignUpActivity.class);
        startActivity(signup);
    }

    public void onClickMainBrowse(View view) {
        Intent mainCollection = new Intent(this, Collection.class);
        startActivity(mainCollection);
    }
}
