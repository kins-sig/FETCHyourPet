package com.sigm.fetchyourpet;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;

import java.util.ArrayList;

public class CollectionEnlarge extends AppCompatActivity {
    public static final String EXTRA_DOG_ID = "id";
    TextView traits, location, breed, vaccinationStatus, healthConcers, name, age, size, additionalInfo;
    ImageView pic;
    String user;
    Menu menu;
    MenuItem menuItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_enlarge);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        location = findViewById(R.id.location);
        breed = findViewById(R.id.breed);
        vaccinationStatus = findViewById(R.id.vaccination);
        healthConcers = findViewById(R.id.healthConcerns);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        size = findViewById(R.id.size);
        pic = findViewById(R.id.dogPic);
        additionalInfo = findViewById(R.id.additionalInfo);

        user = getIntent().getStringExtra("user");


        //here is where we would grab the Dog in the database that has the same ID has EXTRA_DOG_ID.
        //but, for the time being, we will simply retrieve the list of dogs and search through
        //until we find the dog with the correct id.
        String dogID = getIntent().getStringExtra(EXTRA_DOG_ID);
        Dog dog;

        for (Dog d : Dog.dogList) {
            if (d.getId().equals(dogID)) {
                dog = d;
                Dog.currentDog = d;

                Glide.with(this)
                        //.using(new FirebaseImageLoader())
                        .load(d.imageStorageReference)
                        .into(pic);



                pic.setImageBitmap(dog.getImage());

                String locationText = Integer.toString(dog.getLocation());

                breed.setText(dog.getBreed());
                vaccinationStatus.setText(dog.getVaccStatus());
                healthConcers.setText(dog.getHealthConcerns());
                name.setText(dog.getName());
                age.setText("Age: " + dog.getAge());
                size.setText("Size: " + dog.getSize());
                additionalInfo.setText(dog.getAdditionalInfo());
                break;

            }
        }

//        Glide
//                .with(this)
//                .load(fakeDog.getImage())
//                .into(pic);





    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.edit) {
            startActivity(new Intent(this, AddDog.class).putExtra("edit",true));
        }

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.putExtra("user", user);
        setResult(2, intent);


        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(getIntent().getBooleanExtra("viewDogs",false)){
            getMenuInflater().inflate(R.menu.edit_dog_menu, menu);
//            this.menu = menu;
//            this.menuItem = menu.findItem(R.id.edit);
//
        }



        return super.onCreateOptionsMenu(menu);
    }
}
