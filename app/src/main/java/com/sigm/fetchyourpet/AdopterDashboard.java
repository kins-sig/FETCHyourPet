package com.sigm.fetchyourpet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import java.util.Arrays;
import java.util.Collections;

public class AdopterDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopter_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        View hView = navigationView.getHeaderView(0);
        ImageView image = hView.findViewById(R.id.headerImageView);
        TextView name = hView.findViewById(R.id.headerTextView);
        PotentialAdopter p = PotentialAdopter.currentAdopter;
        if(p.getTraits() != null){
            Button b = findViewById(R.id.quiz);
            b.setText("RETAKE QUIZ");


        }

        Bitmap b = p.getPhoto();
        if (b == null) {


            Glide.with(this)
                    // .using(new FirebaseImageLoader())
                    .load(FirebaseStorage.getInstance().getReference().child(p.getImage()))
                    .into(image);

        } else {
            Glide.with(this)
                    // .using(new FirebaseImageLoader())
                    .load(b)
                    .into(image);
        }
        name.setText(p.getFirstName());


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        PotentialAdopter.currentAdopter.setDislikedDogsArray();



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.take_quiz) {
            startActivity(new Intent(this, QuizActivity.class).putExtra("user", "adopter"));


        } else if (id == R.id.browse_all_animals) {
            Intent i = new Intent(this, Collection.class);
            i.putExtra("user", "adopter");
            startActivity(i);


        } else if (id == R.id.home) {
            //  startActivity(new Intent(this, AdopterDashboard.class));
        } else if (id == R.id.logout) {
            startActivity(new Intent(this, MainActivity.class));
            SharedPreferences prefs = getSharedPreferences("Account", Context.MODE_PRIVATE);
            Dog.resetDogList();

            prefs.edit().remove("username").apply();
        }else if(id == R.id.license){
            new LibsBuilder()
                    .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                    .withAboutIconShown(true)
                    .withAboutAppName(getString(R.string.app_name))
                    .withAboutVersionShown(true)
                    .withAboutDescription(getString(R.string.appInfo))
                    .withLicenseDialog(true)
                    .withLicenseShown(true)
                    .withActivityTitle("LICENSES")

                    .start(this);
        }else if(id == R.id.view_your_matchesa){
            AdopterDashboard.viewMatches(this);

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void editProfile(View v) {

        startActivity(new Intent(this, SignUpActivityAdopter.class).putExtra("editProfile", true));

    }

    public void takeQuiz(View v) {
        startActivity(new Intent(this, QuizActivity.class).putExtra("user", "adopter"));

    }

    public void viewYourMatches(View v) {
        AdopterDashboard.viewMatches(this);


    }
    static void viewMatches(Context c){
        if(!PotentialAdopter.currentAdopter.getTraits().equals("")) {
            Algo A = new Algo();
            A.all_dogs = Dog.dogList;
            A.current_user = PotentialAdopter.currentAdopter;
            String[] sorted_dog_list = A.run_recommender_system();

            //dog_list contains the list of BEST fitting dogs from BEST to WORST
            Dog[] dog_list = new Dog[sorted_dog_list.length];

            int i = dog_list.length - 1;
            for (String s : sorted_dog_list) {
                for (Dog d : A.all_dogs) {
                    if (d.getId().equals(s)) {
                        dog_list[i] = d;
                        i--;
                    }
                }
            }
            String s = "According to the quiz results, the best dogs for you are:\n";
            StringBuilder s2 = new StringBuilder(s);
            i = 0;
            while (i < 10) {

                s2.append(dog_list[i].getName());
                if (i != 9) {
                    s2.append(", ");
                }
                i++;

            }
//        Toast t = Toast.makeText(this, s2,
//                Toast.LENGTH_LONG);
//        t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
//        t.show();
            ViewMatches.dogList.clear();
            for (Dog d : dog_list) {
                if (!PotentialAdopter.currentAdopter.dislikedDogsArray.contains(d)) {
                    ViewMatches.dogList.add(d);
                }
            }

            c.startActivity(new Intent(c, ViewMatches.class));
        }else{
            Toast t = Toast.makeText(c, "Please take the quiz before viewing your matches.",
                    Toast.LENGTH_SHORT);
            t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
            t.show();
        }
    }

    double getTopMargin(Double size){
        return size + 10;
    }

}
