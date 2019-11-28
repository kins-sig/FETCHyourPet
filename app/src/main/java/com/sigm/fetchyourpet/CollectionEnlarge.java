package com.sigm.fetchyourpet;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CollectionEnlarge extends AppCompatActivity {
    public static final String EXTRA_DOG_ID = "id";
    TextView traits, location, breed, vaccinationStatus, healthConcers, name, age, sizeView, additionalInfo,trainedView,kidView,aloneView,weightView;
    ImageView pic;
    String user,zip,trained,size,kids,alone,weight;
    Menu menu;
    MenuItem menuItem;
    Dog dog;




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
        sizeView = findViewById(R.id.size);
        pic = findViewById(R.id.dogPic);
        additionalInfo = findViewById(R.id.additionalInfo);
        trainedView = findViewById(R.id.trainedView);
        kidView = findViewById(R.id.kidView);
        aloneView = findViewById(R.id.aloneView);
        weightView = findViewById(R.id.weight);


        user = getIntent().getStringExtra("user");
        ArrayList<TextView> viewsAdded = new ArrayList<>();
        viewsAdded.add(sizeView);
        viewsAdded.add(trainedView);
        viewsAdded.add(kidView);
        viewsAdded.add(aloneView);
        sizeView.setText("hello");



        //here is where we would grab the Dog in the database that has the same ID has EXTRA_DOG_ID.
        //but, for the time being, we will simply retrieve the list of dogs and search through
        //until we find the dog with the correct id.
        String dogID = getIntent().getStringExtra(EXTRA_DOG_ID);

        for (Dog d : Dog.dogList) {
            if (d.getId().equals(dogID)) {
                dog = d;
                Dog.currentDog = d;
                char[] chars = d.getTraits().toCharArray();
                int viewCounter = 0;
                int charCounter = 0;
                for(char c : chars){
                    if(Character.toString(c).equals("1")){
                        if(Dog.traitsTextValue.get(charCounter)!=null) {
                            Log.d("test", Character.toString(c));
                            viewsAdded.get(viewCounter).setText(Dog.traitsTextValue.get(charCounter));
                            Log.d("test",Dog.traitsTextValue.get(charCounter));
                            viewCounter++;
                        }

                    }
                    charCounter++;
                }






                break;

            }
        }

        MainActivity.firestore.collection("rescue")
                .whereEqualTo("rescueID", dog.getRescueID())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {


                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Rescue r = document.toObject(Rescue.class);

                                zip = r.getZip();
                                loadImage();



                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }


                });







    }

    public void loadImage(){
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
        setUI();


        GlideApp.with(this)
                //.using(new FirebaseImageLoader())
                .load(dog.imageStorageReference)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        setUI();
                        return false;
                    }
                })
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(pic);






       // pic.setImageBitmap(dog.getImage());



    }
    public void setUI(){
        breed.setText(dog.getBreed());
        vaccinationStatus.setText(dog.getVaccStatus());
        healthConcers.setText(dog.getHealthConcerns());
        name.setText(dog.getName());
        age.setText("Age: " + dog.getAge());
        if(TextUtils.isEmpty(dog.getAdditionalInfo())){
            dog.setAdditionalInfo("None");
        }
        additionalInfo.setText(dog.getAdditionalInfo());
        location.setText(zip);
        size = sizeView.getText().toString();
        if(size.equals("Size: Large")){
            weight = "50+ lbs";
        }
        else if(size.equals("Size: Medium")){
            weight="25-49 lbs";
        }
        else if(size.equals("Size: Small")){
            weight ="< 24 lbs";
        }
        weightView.setText(weight);
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
