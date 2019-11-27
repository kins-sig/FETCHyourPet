package com.sigm.fetchyourpet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Collection extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    static String user;
    RecyclerView cardRecycler;
    CaptionedImagesAdapter adapter;
    LinearLayoutManager layoutManager;
    Class c;
    Boolean fromCollectionEnlarge = false;
    static boolean viewDogs = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        Toolbar toolbar = findViewById(R.id.toolbar);


        toolbar.setTitle("AVAILABLE PETS");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        if (user == null) {
            user = getIntent().getStringExtra("user");
        }
        View hView = navigationView.getHeaderView(0);
        ImageView image = hView.findViewById(R.id.headerImageView);
        TextView name = hView.findViewById(R.id.headerTextView);

        if (user.equals("adopter")) {
            c = AdopterDashboard.class;
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.adopter_drawer);
            PotentialAdopter p = PotentialAdopter.currentAdopter;
            Bitmap b = p.getPhoto();
            if (b != null) {
                image.setImageBitmap(p.getPhoto());

            } else {
                image.setImageResource(R.drawable.josiefetch);
            }
            name.setText(p.getFirstName());


        } else if (user.equals("rescue")) {
            c = RescueDashboard.class;

            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.rescue_drawer);


            Rescue r = Rescue.currentRescue;
            Bitmap b = r.getPhoto();
            if (b != null) {
                image.setImageBitmap(r.getPhoto());

            } else {
                image.setImageResource(R.drawable.josiefetch);
            }
            name.setText(r.getOrganization());


        } else {
            c = MainActivity.class;
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        adapter = new CaptionedImagesAdapter();
        cardRecycler = findViewById(R.id.pet_recycler);
        if(getIntent().getBooleanExtra("viewDogs",false)){
            adapter.setDogs(Rescue.currentRescue.dogs);
            adapter.setViewDogs(true);
            viewDogs= true;
            toolbar.setTitle("YOUR DOGS");
        }
        else {
            adapter.setDogs(Dog.dogList);
        }

        layoutManager = new GridLayoutManager(this, 3);
        cardRecycler.setLayoutManager(layoutManager);
        cardRecycler.setAdapter(adapter);
        adapter.setUser(user);

        adapter.notifyDataSetChanged();



        //public Dog(String name, int image, int age, String size, ArrayList<String> traits, int zip, String breed, String vaccinationStatus, String healthConcerns ){

//        ArrayList<Dog> dogs = new ArrayList<>();
//        dogs.add(new Dog("Josie", R.drawable.josiefetch, 3, "Medium", getList("Chill", "loving", "loves treats"), 28403, "Mutt", "All shots are up to date!", "Josie is an overall extremely healthy girl! We currently have no known health conerns."));
//        dogs.add(new Dog("Rex", R.drawable.dog1, 1, "Small", getList("Sleeps a lot", "playful", "unconditional love"), 28465, "Bulldog", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Sailor", R.drawable.dog2, 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Lil' Shit", R.drawable.dog3, 1, "Small", getList("Is a little shit", "playful", "unconditional love"), 28465, "Yapper", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Thicky Vicky", R.drawable.dog4, 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Queenie", R.drawable.dog5, 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Squishy", R.drawable.dog6, 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Duke", R.drawable.dog7, 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Bogue", R.drawable.dog8, 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Walter", R.drawable.dog9, 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Mango", R.drawable.dog10, 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Lance", R.drawable.golden1, 1, "Small and Floofy", getList("Adorable", "can activate 'Attack Mode' instantly", "will cuddle at night"), 28570, "Golden Retriever", "He's a healthy little guy!", "May actually love you too much and never leave your side. You've been warned."));
//        dogs.add(new Dog("Diego", R.drawable.golden2, 1, "Smol and Handsome", getList("Might actually steal your girl", "will attract many hot chicks", "won best dressed in 2019"), 28570, "Golden Retriever", "He's a healthy little guy!", "This little guy gets around a lot. All I'm gonna say."));
//        dogs.add(new Dog("Richie", R.drawable.pug1, 1, "Very smol", getList("Will always put a smile on your face", "you may cry just from looking at him", "will get you a lot of social media clout"), 27502, "Pug", "Snug as a bug", "He will get fat. You will not be able to resist the puppy dog eyes staring at you while eating. He's not fat now, but he will be."));
//        dogs.add(new Dog("Georgie", R.drawable.pug2, 1, "Very smol", getList("Is a pug", "looks derpy sometimes", "goofball"), 27502, "Pug", "Snug as a bug", "Healthy little guy"));
//        dogs.add(new Dog("Josie", R.drawable.josiefetch, 3, "Medium", getList("Chill", "loving", "loves treats"), 28403, "Mutt", "All shots are up to date!", "Josie is an overall extremely healthy girl! We currently have no known health conerns."));
//        dogs.add(new Dog("Rex", R.drawable.dog1, 1, "Small", getList("Sleeps a lot", "playful", "unconditional love"), 28465, "Bulldog", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Sailor", R.drawable.dog2, 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Lil' Shit", R.drawable.dog3, 1, "Small", getList("Is a little shit", "playful", "unconditional love"), 28465, "Yapper", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Thicky Vicky", R.drawable.dog4, 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Queenie", R.drawable.dog5, 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Squishy", R.drawable.dog6, 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Duke", R.drawable.dog7, 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Bogue", R.drawable.dog8, 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Walter", R.drawable.dog9, 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Mango", R.drawable.dog10, 4, "Small", getList("Awesome", "Super Playful", "unconditional love"), 28465, "German Shepard", "All shots are up to date!", "This breed is known to have breathing problems in the future."));
//        dogs.add(new Dog("Lance", R.drawable.golden1, 1, "Small and Floofy", getList("Adorable", "can activate 'Attack Mode' instantly", "will cuddle at night"), 28570, "Golden Retriever", "He's a healthy little guy!", "May actually love you too much and never leave your side. You've been warned."));
//        dogs.add(new Dog("Diego", R.drawable.golden2, 1, "Smol and Handsome", getList("Might actually steal your girl", "will attract many hot chicks", "won best dressed in 2019"), 28570, "Golden Retriever", "He's a healthy little guy!", "This little guy gets around a lot. All I'm gonna say."));
//        dogs.add(new Dog("Richie", R.drawable.pug1, 1, "Very smol", getList("Will always put a smile on your face", "you may cry just from looking at him", "will get you a lot of social media clout"), 27502, "Pug", "Snug as a bug", "He will get fat. You will not be able to resist the puppy dog eyes staring at you while eating. He's not fat now, but he will be."));
//        dogs.add(new Dog("Georgie", R.drawable.pug2, 1, "Very smol", getList("Is a pug", "looks derpy sometimes", "goofball"), 27502, "Pug", "Snug as a bug", "Healthy little guy"));
//



    }


//    @Override
//    protected void onResume() {
//        if(viewDogs){
//            adapter.setDogs(Rescue.currentRescue.dogs);
//
//            adapter.notifyDataSetChanged();
//
//        }
//
//
//        super.onResume();
//    }

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
        user=null;

        int id = item.getItemId();
        if (id == R.id.take_quiz) {
            startActivity(new Intent(this, QuizActivity.class).putExtra("user", user));

        } else if (id == R.id.sign_in) {
            startActivity(new Intent(this, SignInActivity.class));

        } else if (id == R.id.browse_all_animals) {
            if(viewDogs){
                startActivity(new Intent(this, Collection.class).putExtra("user","rescue").putExtra("viewDogs",false));
            }
            //startActivity(new Intent(this, Collection.class));


        } else if (id == R.id.home) {
            //In the future, add code to determine if the user is signed in or not. Then send
            //them to the right location.

            startActivity(new Intent(this, c));
        }
        else if (id == R.id.logout) {
            startActivity(new Intent(this, MainActivity.class));
        }  else if (id == R.id.view_dogs) {
            if(!viewDogs) {
                startActivity(new Intent(this, Collection.class).putExtra("viewDogs", true).putExtra("user", "rescue"));
            }
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if(id!= R.id.view_dogs) {
            viewDogs = false;
        }
        return true;
    }
    public ArrayList<String> getList(String a, String b, String c) {
        ArrayList<String> traits = new ArrayList<String>();

        // Initialize an ArrayList with add()
        traits.add(a);
        traits.add(b);
        traits.add(c);
        return traits;

    }

}