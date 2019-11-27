package com.sigm.fetchyourpet;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.provider.MediaStore;
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class AddDog extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static int GET_FROM_GALLERY = 1;

    ImageView picView;
    Button picButton, createAccount;
    Bitmap bitmap = null;
    EditText nameView, additionalView, healthConcernsView, breedView, vaccinationView;
    Spinner sexView,ageView;
    Boolean edit;
    Boolean uploadedPhoto;
    String name, healthConcerns, additionalInfo, vaccinationStatus, breed,sex,age;
    Dog d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog);
        Toolbar toolbar = findViewById(R.id.toolbar);


        Rescue r = Rescue.currentRescue;
        Bitmap b = r.getPhoto();
        NavigationView navigationView = findViewById(R.id.nav_view);

        View hView = navigationView.getHeaderView(0);
        ImageView headerImage = hView.findViewById(R.id.headerImageView);
        TextView headerName = hView.findViewById(R.id.headerTextView);
        if (b != null) {
            headerImage.setImageBitmap(r.getPhoto());

        } else {
            headerImage.setImageResource(R.drawable.josiefetch);
        }
        headerName.setText(r.getOrganization());


        createAccount = findViewById(R.id.createAccount);
        nameView = findViewById(R.id.name);
        picView = findViewById(R.id.pic);
        picButton = findViewById(R.id.picButton);
        additionalView = findViewById(R.id.additionalInfo);
        additionalView.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        additionalView.setRawInputType(InputType.TYPE_CLASS_TEXT);
        healthConcernsView = findViewById(R.id.healthConcerns);
        healthConcernsView.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        healthConcernsView.setRawInputType(InputType.TYPE_CLASS_TEXT);
        breedView = findViewById(R.id.breed);
        vaccinationView = findViewById(R.id.vaccination);
        vaccinationView.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        vaccinationView.setRawInputType(InputType.TYPE_CLASS_TEXT);
        sexView = findViewById(R.id.sex);
        ageView = findViewById(R.id.age);

        edit = getIntent().getBooleanExtra("edit", false);


        if(edit){
            d = Dog.currentDog;
            nameView.setText(d.getName());
            picView.setImageBitmap(d.getImage());
            additionalView.setText(d.additionalInfo);
            healthConcernsView.setText(d.healthConcerns);
            breedView.setText(d.breed);
            vaccinationView.setText(d.getVaccStatus());

            Adapter adapter = sexView.getAdapter();
            for(int i = 0; i < adapter.getCount(); i++){
                if(d.getSex().equals(adapter.getItem(i).toString())){
                    sexView.setSelection(i);
                }
            }
            adapter = ageView.getAdapter();
            for(int i = 0; i < adapter.getCount(); i++){
                if(d.getAge().equals(adapter.getItem(i).toString())){
                    ageView.setSelection(i);
                }
            }
            createAccount.setText("UPDATE DOG PROFILE");
            toolbar.setTitle("EDIT DOG PROFILE");



        }
        else{
            toolbar.setTitle("ADD DOG PROFILE");

        }
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


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

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.take_quiz) {
            startActivity(new Intent(this, QuizActivity.class).putExtra("user", "rescue"));


        } else if (id == R.id.browse_all_animals) {
            Intent i = new Intent(this, Collection.class);
            i.putExtra("user", "rescue");
            startActivity(i);


        } else if (id == R.id.home) {
            startActivity(new Intent(this, RescueDashboard.class));
        } else if (id == R.id.logout) {
            startActivity(new Intent(this, MainActivity.class));
        }
        else if (id == R.id.view_dogs) {
            startActivity(new Intent(this, Collection.class).putExtra("viewDogs", true).putExtra("user","rescue"));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addDog(View v){

        String action = "";


        name = nameView.getText().toString().trim();
        if (name.equals("")) {
            action = "Please enter the dog's name.\n";
        }
        sex = sexView.getSelectedItem().toString();
        age = ageView.getSelectedItem().toString();
        if(sex.equals("Select Sex")){
            action += "Please select the dog's sex.\n";

        }
        if(age.equals("Select Age")){
            action += "Please select the dog's age.\n";

        }




        breed = breedView.getText().toString().trim();
        if (breed.equals("")) {
            action += "Please enter the dog's breed! If unknown, just enter  'Mixed'.";
        }
        healthConcerns = healthConcernsView.getText().toString().trim();
        if (healthConcerns.equals("")) {
            healthConcerns = "None";
        }
        vaccinationStatus = vaccinationView.getText().toString().trim();

        additionalInfo = additionalView.getText().toString().trim();
        if (additionalInfo.equals("")) {
            additionalInfo = "None";
        }





        if (!action.equals("")) {
            Toast t = Toast.makeText(this, action,
                    Toast.LENGTH_SHORT);
            t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
            t.show();
        } else {
            //    public Rescue(String name, String street, String city, String state, int zip, String email, String password){
            if (edit) {
                Dog d  = Dog.currentDog;
                if(uploadedPhoto) {
                    d.setBitmapImage(bitmap);
                }
                d.setName(name);
                d.setBreed(breed);
                d.setHealthConcerns(healthConcerns);
                d.setVaccinationStatus(vaccinationStatus);
                d.setAdditionalInfo(additionalInfo);
                d.setSex(sex);
                d.setAge(age);

            } else {
                Dog d =
                        new Dog(name, bitmap, breed, vaccinationStatus, healthConcerns, sex, age,additionalInfo);
                Dog.currentDog = d;
                Rescue.currentRescue.dogs.add(d);
            }
            //Intent dashboard = new Intent(this, QuizActivity.class).putExtra("user","rescue");
            Intent dashboard = new Intent(this, RescueDashboard.class);

            startActivity(dashboard);
            finish();

        }


    }

    public void uploadPhoto(View view) {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, GET_FROM_GALLERY);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                Glide
                        .with(this)
                        .load(bitmap)
                        .into(picView);
                picButton.setText("CHANGE PHOTO");
                uploadedPhoto = true;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}


