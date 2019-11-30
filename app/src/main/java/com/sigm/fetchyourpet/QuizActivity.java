package com.sigm.fetchyourpet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.Arrays;

public class QuizActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Need to decide how many questions our quiz will be, and allocate the correct amount of data (3*numOfQuestions)
    int[] values = new int[30];
    int currentQuestion = 0;
    ArrayList<Question> userQuestions = new ArrayList<>();
    ArrayList<Question> dogQuestions = new ArrayList<>();
    ArrayList<Question> currentQuestions = getCurrentQuestions();

    Class c = MainActivity.class;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("FETCH! QUIZ");

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        type = getIntent().getStringExtra("user");
        View hView = navigationView.getHeaderView(0);
        ImageView image = hView.findViewById(R.id.headerImageView);
        TextView name = hView.findViewById(R.id.headerTextView);
        if (type.equals("adopter")) {
            c = AdopterDashboard.class;
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.adopter_drawer);


            PotentialAdopter p = PotentialAdopter.currentAdopter;
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
        } else if (type.equals("rescue")) {
            c = RescueDashboard.class;

            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.rescue_drawer);

            Rescue r = Rescue.currentRescue;
            Bitmap b = r.getPhoto();
            if (b == null) {
                Glide.with(this)
                        // .using(new FirebaseImageLoader())
                        .load(FirebaseStorage.getInstance().getReference().child(r.getImage()))
                        .into(image);
            } else {
                Glide.with(this)
                        // .using(new FirebaseImageLoader())
                        .load(b)
                        .into(image);
            }
            name.setText(r.getOrganization());
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        userQuestions.add(new Question(0, "How active do you want your dog to be?", "Very active", "Moderately active", "Not active at all"));
        userQuestions.add(new Question(1, "What size do you want your dog to be?", "Large (50+ lbs)", "Medium (25-49 lbs)", "Small (0-24 lbs)"));
        userQuestions.add(new Question(2, "How affectionate do you want your dog to be?", "I want a dog to respect my space", "I want a dog to cuddle with me on the couch", "If it happens, it happens"));
        userQuestions.add(new Question(3, "How trained do you want your dog to be?", "Knows basic commands", "Willing to dedicate some time to training", "Knows or willing to learn advanced commands"));
        userQuestions.add(new Question(4, "How do you feel about shedding?", "I don't mind shedding, bring on the hair!", "A little shedding is okay", "I hate dog hair."));
        userQuestions.add(new Question(5, "Do you want your dog to be around kids?", "Our dog will have little to no exposure to kids", "We will be around kids sometimes", "We want our dog to love kids!"));
        userQuestions.add(new Question(6, "How often will your dog be around other animals?", "Very often", "Every now and then", "Rarely"));
        userQuestions.add(new Question(7, "How often will your dog be alone?", "Pretty often", "Not that often", "Not often at all"));
        userQuestions.add(new Question(8, "How do you respond to stress?", "I like to try to solve problems rationally", "I tend to hide from my problems", "I just don't care"));
        userQuestions.add(new Question(9, "Which would you prefer to do in your free time? (Please select only one)", "Lazy day on the couch", "Outdoor adventure", "Go shopping"));


        dogQuestions.add(new Question(10, "How active is this dog? (Please select only one)", "Very active", "Moderately active", "Not very active"));
        dogQuestions.add(new Question(11, "What size is this dog? (Please select only one)", "Large (50+ lbs)", "Medium (25-49 lbs)", "Small (0-24 lbs)"));
        dogQuestions.add(new Question(12, "How affectionate is this dog? (Please select only one)", "Respects personal space", "Loves to cuddle/show affection", "If it happens, it happens"));
        dogQuestions.add(new Question(13, "How trained is this dog? (Please select only one)", "Knows basic commands", "Needs some work with basic commands", "Knows or willing to learn advanced commands"));
        dogQuestions.add(new Question(14, "How much does this dog shed? (Please select only one)", "Heavy shedder", "Moderate shedder", "Doesn’t shed much"));
        dogQuestions.add(new Question(15, "How good is this dog with kids? (Please select only one)", "Not the best with kids", "Can tolerate kids", "Good with kids!"));
        dogQuestions.add(new Question(16, "How good is this dog with other animals? (Please select only one)", "Good with other animals", "Moderate with other animals", "Not good with other animals"));
        dogQuestions.add(new Question(17, "How good is this dog at being left alone? (Please select only one)", "Fairly good at being left alone", "Somewhat good at being left alone", "Not good at being left alone"));
        dogQuestions.add(new Question(18, "How does this dog respond to stress? (Please select only one)", "Very alert and visibly tries to work through problems", "Scared and hides", "Indifferent, seems relaxed"));
        dogQuestions.add(new Question(19, "What have you noticed this dog doing in its free time/when left alone? (Please select only one)", "Waiting patiently for some attention", "Demanding attention via barking or jumping", "Chilling/indifferent"));

        TextView question = findViewById(R.id.question1);
        CheckBox q1Answer1 = findViewById(R.id.Q1Answer1);
        CheckBox q1Answer2 = findViewById(R.id.q1Answer2);
        CheckBox q1Answer3 = findViewById(R.id.q1Answer3);

        question.setText(currentQuestions.get(0).questionText);

        q1Answer1.setText(currentQuestions.get(0).answer1);
        q1Answer1.setChecked(currentQuestions.get(currentQuestion).answer1check);
        q1Answer2.setText(currentQuestions.get(0).answer2);
        q1Answer1.setChecked(currentQuestions.get(currentQuestion).answer1check);
        q1Answer3.setText(currentQuestions.get(0).answer3);
        q1Answer1.setChecked(currentQuestions.get(currentQuestion).answer1check);
    }

    public void onClickNext(View view) {
        Log.d("PET", Integer.toString(currentQuestion));
        TextView question = findViewById(R.id.question1);
        CheckBox q1Answer1 = findViewById(R.id.Q1Answer1);
        CheckBox q1Answer2 = findViewById(R.id.q1Answer2);
        CheckBox q1Answer3 = findViewById(R.id.q1Answer3);
        currentQuestions.get(currentQuestion).setAnswer2check(q1Answer2.isChecked());
        currentQuestions.get(currentQuestion).setAnswer1check(q1Answer1.isChecked());
        currentQuestions.get(currentQuestion).setAnswer3check(q1Answer3.isChecked());


        if (q1Answer1.isChecked()) {
            values[currentQuestion * 3] = 1;
        } else values[currentQuestion * 3] = 0;

        if (q1Answer2.isChecked()) {
            values[currentQuestion * 3 + 1] = 1;
        } else values[currentQuestion * 3 + 1] = 0;
        if (q1Answer3.isChecked()) {
            values[currentQuestion * 3 + 2] = 1;
        } else values[currentQuestion * 3 + 2] = 0;

        currentQuestion++;

        if (currentQuestion < currentQuestions.size()) {

            ImageButton prev = findViewById(R.id.prevQuestion);
            prev.setVisibility(View.VISIBLE);
            question.setText(currentQuestions.get(currentQuestion).questionText);
            q1Answer1.setText(currentQuestions.get(currentQuestion).answer1);
            q1Answer1.setChecked(currentQuestions.get(currentQuestion).answer1check);
            q1Answer2.setText(currentQuestions.get(currentQuestion).answer2);
            q1Answer2.setChecked(currentQuestions.get(currentQuestion).answer2check);
            q1Answer3.setText(currentQuestions.get(currentQuestion).answer3);
            q1Answer3.setChecked(currentQuestions.get(currentQuestion).answer3check);

        } else {
            ImageButton next = findViewById(R.id.nextQuestion);
            next.setVisibility(View.INVISIBLE);
            Button submit = findViewById(R.id.submitQuiz);
            submit.setVisibility(View.VISIBLE);
        }
    }

    public void onClickPrev(View view) {
        Log.d("PET", Integer.toString(currentQuestion));
        TextView question = findViewById(R.id.question1);
        CheckBox q1Answer1 = findViewById(R.id.Q1Answer1);
        CheckBox q1Answer2 = findViewById(R.id.q1Answer2);
        CheckBox q1Answer3 = findViewById(R.id.q1Answer3);


//        if (q1Answer1.isChecked()) {
//            values[currentQuestion * 3] = 1;
//        } else values[currentQuestion * 3] = 0;
//
//        if (q1Answer2.isChecked()) {
//            values[currentQuestion * 3 + 1] = 1;
//        } else values[currentQuestion * 3 + 1] = 0;
//        if (q1Answer3.isChecked()) {
//            values[currentQuestion * 3 + 2] = 1;
//        } else values[currentQuestion * 3 + 2] = 0;

        currentQuestion--;
        question.setText(currentQuestions.get(currentQuestion).questionText);
        q1Answer1.setText(currentQuestions.get(currentQuestion).answer1);
        q1Answer1.setChecked(currentQuestions.get(currentQuestion).answer1check);
        q1Answer2.setText(currentQuestions.get(currentQuestion).answer2);
        q1Answer2.setChecked(currentQuestions.get(currentQuestion).answer2check);
        q1Answer3.setText(currentQuestions.get(currentQuestion).answer3);
        q1Answer3.setChecked(currentQuestions.get(currentQuestion).answer3check);

        if (currentQuestion <= 0) {

            ImageButton prev = findViewById(R.id.prevQuestion);
            prev.setVisibility(View.INVISIBLE);


        }
        else {
            ImageButton next = findViewById(R.id.nextQuestion);
            next.setVisibility(View.VISIBLE);

            Button submit = findViewById(R.id.submitQuiz);
            submit.setVisibility(View.INVISIBLE);
        }
    }

    public void onClickSubmitQuiz(View view) {
        if(Account.currentAccount.getIsAdopter()) {
            Log.d("PET", PotentialAdopter.currentAdopter.getUsername());
        }
        if(!Account.currentAccount.getIsAdopter()) {
            Log.d("PET", Rescue.currentRescue.getOrganization());
        }
        Log.d("PET", Arrays.toString(values));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
            //startActivity(new Intent(this, QuizActivity.class));

        } else if (id == R.id.browse_all_animals) {
            startActivity(new Intent(this, Collection.class).putExtra("user", type));


        } else if (id == R.id.home) {
            startActivity(new Intent(this, c));
        } else if (id == R.id.logout) {
            startActivity(new Intent(this, MainActivity.class));
            SharedPreferences prefs = getSharedPreferences("Account", Context.MODE_PRIVATE);
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
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void editProfile(View v) {

    }

    public void viewYourMatches(View v) {

    }
    public ArrayList<Question> getCurrentQuestions() {
        if(Account.currentAccount.getIsAdopter()){
            currentQuestions = userQuestions;
        }
        if(!Account.currentAccount.getIsAdopter()){
            currentQuestions = dogQuestions;
        }
        return currentQuestions;

    }


}



