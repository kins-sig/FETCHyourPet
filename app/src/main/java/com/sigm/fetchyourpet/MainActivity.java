package com.sigm.fetchyourpet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import java.util.ArrayList;

// ...
// When the user selects an option to see the licenses:


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String ACCESS_TOKEN = "zQ0PTVsAoiAAAAAAAAAAGBBC3SX0o2N1bk2odWZjy5iRyN9DoFuWE-hB1u82jYHW";
    static boolean firstRun = true;
    static FirebaseFirestore firestore;
    static StorageReference storageReference;
    final Thread t = null;
    String username;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("Account", Context.MODE_PRIVATE);
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        username = prefs.getString("username", "-1");

        if (!username.equals("-1")) {

            login();
            dialog = new ProgressDialog(this);
            dialog.setMessage("Signing In");
            dialog.setCancelable(false);
            dialog.setInverseBackgroundForced(false);
            dialog.show();


        }


        if (firstRun) {
            //addDogs();
            firstRun = false;

            firestore.collection("dog")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                Dog d;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // Log.d("test",document.getData().get("name")+document.getData().get("traits").toString());
                                    d = document.toObject(Dog.class);
                                    Dog.dogList.add(d);
                                    d.imageStorageReference = storageReference.child(d.image);
                                    d.id = document.getId();


                                }
                            } else {
                                Log.d("test", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }


        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("FETCH! YOUR PET");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Dog.c = this;


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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

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

        if (id == R.id.sign_in) {
            Log.d("test", "sign in");
            startActivity(new Intent(this, SignInActivity.class));

        } else if (id == R.id.browse_all_animals) {
            startActivity(new Intent(this, Collection.class).putExtra("user", "none"));


        } else if (id == R.id.home) {
            // startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.take_quiz) {
            startActivity(new Intent(this, QuizActivity.class).putExtra("user", "none"));
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

    public void browsePets(View v) {
        startActivity(new Intent(this, Collection.class).putExtra("user", "none"));

    }

    public void signIn(View v) {
        startActivity(new Intent(this, SignInActivity.class));

    }

    public void signUp(View v) {


        startActivity(new Intent(this, SignUpAccountType.class));

    }

    //    public void addDogs(){
//        Dog.dogList.add(new Dog("Josie", getBitmap(R.drawable.josiefetch), 3, "Medium", getList("Chill", "loving", "loves treats"), 28403, "Mutt", "All shots are up to date!", "Josie is an overall extremely healthy girl! We currently have no known health conerns."));
//        Dog.dogList.add(new Dog("Rex", getBitmap(R.drawable.dog1), 1, "Small", getList("Sleeps a lot", "playful", "unconditional love"), 28465, "Bulldog", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Sailor", getBitmap(R.drawable.dog2), 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Lil' Shit", getBitmap(R.drawable.dog3), 1, "Small", getList("Is a little shit", "playful", "unconditional love"), 28465, "Yapper", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Thicky Vicky", getBitmap(R.drawable.dog4), 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Queenie", getBitmap(R.drawable.dog5), 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Squishy", getBitmap(R.drawable.dog6), 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Duke", getBitmap(R.drawable.dog7), 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Bogue", getBitmap(R.drawable.dog8), 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Walter", getBitmap(R.drawable.dog9), 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Mango", getBitmap(R.drawable.dog10), 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Lance", getBitmap(R.drawable.golden1), 1, "Small and Floofy", getList("Adorable", "can activate 'Attack Mode' instantly", "will cuddle at night"), 28570, "Golden Retriever", "He's a healthy little guy!", "May actually love you too much and never leave your side. You've been warned."));
//        Dog.dogList.add(new Dog("Diego", getBitmap(R.drawable.golden2), 1, "Smol and Handsome", getList("Might actually steal your girl", "will attract many hot chicks", "won best dressed in 2019"), 28570, "Golden Retriever", "He's a healthy little guy!", "This little guy gets around a lot. All I'm gonna say."));
//        Dog.dogList.add(new Dog("Richie", getBitmap(R.drawable.pug1), 1, "Very smol", getList("Will always put a smile on your face", "you may cry just from looking at him", "will get you a lot of social media clout"), 27502, "Pug", "Snug as a bug", "He will get fat. You will not be able to resist the puppy dog eyes staring at you while eating. He's not fat now, but he will be."));
//        Dog.dogList.add(new Dog("Georgie", getBitmap(R.drawable.pug2), 1, "Very smol", getList("Is a pug", "looks derpy sometimes", "goofball"), 27502, "Pug", "Snug as a bug", "Healthy little guy"));
//        Dog.dogList.add(new Dog("Josie", getBitmap(R.drawable.josiefetch), 3, "Medium", getList("Chill", "loving", "loves treats"), 28403, "Mutt", "All shots are up to date!", "Josie is an overall extremely healthy girl! We currently have no known health conerns."));
//        Dog.dogList.add(new Dog("Rex", getBitmap(R.drawable.dog1), 1, "Small", getList("Sleeps a lot", "playful", "unconditional love"), 28465, "Bulldog", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Sailor", getBitmap(R.drawable.dog2), 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Lil' Shit", getBitmap(R.drawable.dog3), 1, "Small", getList("Is a little shit", "playful", "unconditional love"), 28465, "Yapper", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Thicky Vicky", getBitmap(R.drawable.dog4), 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Queenie", getBitmap(R.drawable.dog5), 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Squishy", getBitmap(R.drawable.dog6), 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Duke", getBitmap(R.drawable.dog7), 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Bogue", getBitmap(R.drawable.dog8), 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Walter", getBitmap(R.drawable.dog9), 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Mango", getBitmap(R.drawable.dog10), 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        Dog.dogList.add(new Dog("Lance", getBitmap(R.drawable.golden1), 1, "Small and Floofy", getList("Adorable", "can activate 'Attack Mode' instantly", "will cuddle at night"), 28570, "Golden Retriever", "He's a healthy little guy!", "May actually love you too much and never leave your side. You've been warned."));
//        Dog.dogList.add(new Dog("Diego", getBitmap(R.drawable.golden2), 1, "Smol and Handsome", getList("Might actually steal your girl", "will attract many hot chicks", "won best dressed in 2019"), 28570, "Golden Retriever", "He's a healthy little guy!", "This little guy gets around a lot. All I'm gonna say."));
//        Dog.dogList.add(new Dog("Richie", getBitmap(R.drawable.pug1), 1, "Very smol", getList("Will always put a smile on your face", "you may cry just from looking at him", "will get you a lot of social media clout"), 27502, "Pug", "Snug as a bug", "He will get fat. You will not be able to resist the puppy dog eyes staring at you while eating. He's not fat now, but he will be."));
//        Dog.dogList.add(new Dog("Georgie", getBitmap(R.drawable.pug2), 1, "Very smol", getList("Is a pug", "looks derpy sometimes", "goofball"), 27502, "Pug", "Snug as a bug", "Healthy little guy"));
//
//
//    }
    public ArrayList<String> getList(String a, String b, String c) {
        ArrayList<String> traits = new ArrayList<String>();

        // Initialize an ArrayList with add()
        traits.add(a);
        traits.add(b);
        traits.add(c);
        return traits;

    }

    public Bitmap getBitmap(int image) {
        return BitmapFactory.decodeResource(getResources(),
                image);
    }

    public void login() {

        MainActivity.firestore.collection("account")
                .whereEqualTo("username", username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        boolean stop = false;
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                            }
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (!stop) {
                                    Account a = document.toObject(Account.class);
                                    Account.currentAccount = a;
                                    stop = true;
                                    String collectionpath = "rescue";
                                    if (a.getIsAdopter()) {
                                        collectionpath = "adopter";
                                    }

                                    MainActivity.firestore.collection(collectionpath)
                                            .whereEqualTo("username", username)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        if (task.getResult().isEmpty()) {
                                                        }
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            if (Account.currentAccount.getIsAdopter()) {
                                                                PotentialAdopter.currentAdopter = document.toObject(PotentialAdopter.class);
                                                                startActivity(new Intent(getApplicationContext(), AdopterDashboard.class));
                                                                dialog.dismiss();

                                                                //finish();


                                                            } else {
                                                                Rescue.currentRescue = document.toObject(Rescue.class);
                                                                startActivity(new Intent(getApplicationContext(), RescueDashboard.class));
                                                                dialog.dismiss();
                                                                //finish();


                                                            }

                                                        }
                                                    }
                                                }


                                            });


                                }

                            }
                        }
                    }


                });
    }

}




