package com.sigm.fetchyourpet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CollectionEnlarge extends AppCompatActivity {
    TextView traits, location, breed, vaccinationStatus, healthConcers, name, age, size;
    ImageView pic;
    public static final String EXTRA_DOG_ID = "id";
    public static ArrayList<Dog> dogs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_enlarge);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        traits = findViewById(R.id.topTraits);
        location = findViewById(R.id.location);
        breed = findViewById(R.id.breed);
        vaccinationStatus = findViewById(R.id.vaccination);
        healthConcers = findViewById(R.id.healthConcerns);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        size = findViewById(R.id.size);
        pic = findViewById(R.id.dogPic);



        //here is where we would grab the Dog in the database that has the same ID has EXTRA_DOG_ID.
        //but, for the time being, we will simply retrieve the list of dogs and search through
        //until we find the dog with the correct id.
        long dogID = getIntent().getLongExtra(EXTRA_DOG_ID, -1);
        Dog fakeDog = new Dog();

        for(Dog d:dogs){
            if(d.getId() == dogID){
                fakeDog = d;
            }
        }
        pic.setImageResource(fakeDog.getImage());

//        Glide
//                .with(this)
//                .load(fakeDog.getImage())
//                .into(pic);



        traits.setText(fakeDog.getTraits());
        location.setText(Integer.toString(fakeDog.getLocation()));
        breed.setText(fakeDog.getBreed());
        vaccinationStatus.setText(fakeDog.getVaccStatus());
        healthConcers.setText(fakeDog.getHealthConcerns());
        name.setText(fakeDog.getName());
        age.setText("Age: "+fakeDog.getAgeString());
        size.setText("Size: "+fakeDog.getSize());





    }
    public static void setDogList(ArrayList<Dog> dogs2){
        dogs = dogs2;


    }
}
