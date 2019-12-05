package com.sigm.fetchyourpet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
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

//This activity is the main page for Potential Adopters.
public class AdopterDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Finds compatability matches and launches the ViewMatches activity to display corresponding matches.
    //Takes in one variable, the current Context.
    static void viewMatches(Context c) {
        //If the user has already taken the quiz,
        if (!PotentialAdopter.currentAdopter.getTraits().equals("")) {
            //Instantiate a new Algo
            Algo A = new Algo();
            //Set the dog list and the user
            A.all_dogs = Dog.dogList;
            A.current_user = PotentialAdopter.currentAdopter;
            //run the algorithm
            String[] sorted_dog_list = A.run_recommender_system();

            //sorted_dog_list (above) contains the dogs from WORST to BEST
            //Here, we create an algorithm to reverse this list
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

            //Reset the dogList in ViewMatches activity before calling it
            ViewMatches.dogList.clear();
            //Remove any dogs that are DISLIKED by the adopter.
            for (Dog d : dog_list) {
                if (!PotentialAdopter.currentAdopter.dislikedDogsArray.contains(d)) {
                    ViewMatches.dogList.add(d);
                }
            }
            //Start the activity to display the matches
            c.startActivity(new Intent(c, ViewMatches.class));
        } else {
            Toast t = Toast.makeText(c, "Please take the quiz before viewing your matches.",
                    Toast.LENGTH_SHORT);
            t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
            t.show();
        }
    }

    //Initialization and setting the layout
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

        //If the user has already taken the quiz, change the "TAKE QUIZ" text to "RETAKE QUIZ"
        if (p.getTraits() != null) {
            Button b = findViewById(R.id.quiz);
            b.setText("RETAKE QUIZ");


        }

        //If the adopter uploaded a photo in this session, b will not be null. Use that photo if
        //it is not null. Else, use the photo stored in the database. This is done because, in some
        //instances, the photo will not be uploaded to the database quick enough, so we must use
        //the photo from the current session.
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

    //Handle navigation drawer opening/closing
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }
    }



    //Handle all navigation drawer actions
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
        } else if (id == R.id.license) {
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
        } else if (id == R.id.view_your_matchesa) {
            AdopterDashboard.viewMatches(this);

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Launch edit profile activity
    public void editProfile(View v) {

        startActivity(new Intent(this, SignUpActivityAdopter.class).putExtra("editProfile", true));

    }

    //Launch quiz activity
    public void takeQuiz(View v) {
        startActivity(new Intent(this, QuizActivity.class).putExtra("user", "adopter"));

    }
    //Launch viewMatches activity
    public void viewYourMatches(View v) {
        AdopterDashboard.viewMatches(this);


    }



}
