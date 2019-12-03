package com.sigm.fetchyourpet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Dimension;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Collection extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    static String user;
    static boolean viewDogs = false;
    private String sortBy = "";
    boolean favorited = false;
    RecyclerView cardRecycler;
    CaptionedImagesAdapter adapter;
    LinearLayoutManager layoutManager;
    Menu optionsMenu;
    Class c;
    Rescue r;
    public List<Dog> dogs = new ArrayList<>();


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


        } else if (user.equals("rescue")) {
            c = RescueDashboard.class;

            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.rescue_drawer);


            r = Rescue.currentRescue;
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
        if (getIntent().getBooleanExtra("viewDogs", false)) {
            if (r.dogs != null) {
                r.dogs.clear();

            }
            for (Dog d : Dog.dogList) {
                Log.d("test",d.getName());
                if (d.getRescueID().equals(r.getRescueID())| Rescue.currentRescue.getUsername().equals("admin")) {
                    r.dogs.add(d);
                    this.dogs.add(d);
                }
            }

            adapter.setDogs(Rescue.currentRescue.dogs);
            adapter.setViewDogs(true);
            viewDogs = true;
            toolbar.setTitle("YOUR DOGS");


        } else {
            dogs = Dog.dogList;


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
    protected void onResume() {


        super.onResume();

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
        }else if(id == R.id.favorites){
            List<Dog> dogsCopy = new ArrayList<>();

            if(!favorited){
                for(Dog d:dogs){
                    if(d.favorited){
                        dogsCopy.add(d);
                    }
                }
                optionsMenu.getItem(0).setIcon(R.drawable.like_heart_24dp);
            }else{
                dogsCopy = new ArrayList<>(dogs);
                optionsMenu.getItem(0).setIcon(R.drawable.default_heart_icon_24dp);

            }
            favorited = !favorited;

            adapter.setDogs(dogsCopy);

            adapter.notifyDataSetChanged();

        }else if(id == R.id.sort){
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;

            LayoutInflater li = LayoutInflater.from(this);
            final View promptsView = li.inflate(R.layout.sort_collection, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

//            final EditText userInput = (EditText) promptsView
//                    .findViewById(R.id.editTextDialogUserInput);
            final RadioButton r1 =  promptsView.findViewById(R.id.age);
            final RadioButton r2 =  promptsView.findViewById(R.id.name);
            final RadioButton r3 =  promptsView.findViewById(R.id.sex);
            final RadioButton r4 =  promptsView.findViewById(R.id.size);
            final RadioButton r5 =  promptsView.findViewById(R.id.none);
            final RadioButton ascending =  promptsView.findViewById(R.id.ascending);

            // set dialog message
            alertDialogBuilder
                    .setCancelable(true)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    // get user input and set it to result
                                    // edit text

                                    if(r1.isChecked()){
                                        sortBy = "age";
                                    }
                                    else if(r2.isChecked()){
                                        sortBy = "name";
                                    }
                                    else if(r3.isChecked()){
                                        sortBy = "sex";

                                    }
                                    else if(r4.isChecked()){
                                        sortBy = "size";
                                    }
                                    else if(r5.isChecked()){
                                        sortBy = "";
                                    }
                                    sort(ascending.isChecked());

                                    optionsMenu.getItem(0).setIcon(R.drawable.default_heart_icon_24dp);
                                    favorited = false;


                                    Log.d("test", Boolean.toString(r1.isChecked()));
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });




            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();



            // show it
            alertDialog.show();


            alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, width);

//            optionsMenu.getItem(0).setIcon(R.drawable.default_heart_icon_24dp);
//            favorited = false;



            // alertDialog.getWindow().setLayout((int)(width/1.5), (int)height/2); //Controlling width and height.

        }

        return super.onOptionsItemSelected(item);
    }

    public void sort(final Boolean isAscending){
        List<Dog> dogsCopy = new ArrayList<>(dogs);

        if(sortBy.equals("name")){
            Collections.sort(dogsCopy,new Comparator<Dog>() {
                @Override
                public int compare(Dog o1, Dog o2) {
                    if(!isAscending){
                        return (o2.getName().compareTo(o1.getName()));

                    }

                    return (o1.getName().compareTo(o2.getName()));
                }
            });

        }
        else if(sortBy.equals("age")){

            Collections.sort(dogsCopy,new Comparator<Dog>() {
                @Override
                public int compare(Dog o1, Dog o2) {
                    if(!isAscending){
                        return (o2.getIntAge() - (o1.getIntAge()));

                    }


                    return (o1.getIntAge() - (o2.getIntAge()));
                }
            });
        }
        else if(sortBy.equals("sex")){
            Collections.sort(dogsCopy,new Comparator<Dog>() {
                @Override
                public int compare(Dog o1, Dog o2) {
                    if(!isAscending){
                        return (o2.getSex().compareTo(o1.getSex()));

                    }
                    return (o1.getSex().compareTo(o2.getSex()));
                }
            });
        }
        else if(sortBy.equals("size")){
            Collections.sort(dogsCopy,new Comparator<Dog>() {
                @Override
                public int compare(Dog o1, Dog o2) {
                    if(!isAscending){
                        return (o2.getIntSize() - (o1.getIntSize()));

                    }
                    return (o1.getIntSize() - (o2.getIntSize()));
                }
            });
        }

        if(!sortBy.equals("")) {
            adapter.setDogs(dogsCopy);
        }
        else{
            adapter.setDogs(dogs);
        }
        adapter.notifyDataSetChanged();


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        user = null;

        int id = item.getItemId();
        if (id == R.id.take_quiz) {
            startActivity(new Intent(this, QuizActivity.class).putExtra("user", user));

        } else if (id == R.id.sign_in) {
            startActivity(new Intent(this, SignInActivity.class));

        } else if (id == R.id.browse_all_animals) {
            if (viewDogs) {
                startActivity(new Intent(this, Collection.class).putExtra("user", "rescue").putExtra("viewDogs", false));
               // finish();
            }


        } else if (id == R.id.home) {
            //In the future, add code to determine if the user is signed in or not. Then send
            //them to the right location.

            startActivity(new Intent(this, c));
        } else if (id == R.id.logout) {
            startActivity(new Intent(this, MainActivity.class));
            SharedPreferences prefs = getSharedPreferences("Account", Context.MODE_PRIVATE);
            prefs.edit().remove("username").apply();
        } else if (id == R.id.view_dogs) {
            if (!viewDogs) {
                startActivity(new Intent(this, Collection.class).putExtra("viewDogs", true).putExtra("user", "rescue"));
            }
        }
        else if(id == R.id.license){
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
        if (id != R.id.view_dogs) {
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_menu_option, menu);
        super.onCreateOptionsMenu(menu);
        optionsMenu = menu;



        if(user.equals("adopter")){


            if(PotentialAdopter.currentAdopter.favoritedDogs.trim().equals("")){
                optionsMenu.getItem(0).setVisible(false)    ;
            }
            else{
                optionsMenu.getItem(0).setVisible(true)    ;

            }
        }else{
            optionsMenu.getItem(0).setVisible(false);
        }



        return true;
    }
}



