package com.sigm.fetchyourpet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddDog extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static int GET_FROM_GALLERY = 1;

    ImageView picView;
    Button picButton, createAccount;
    Bitmap bitmap = null;
    EditText nameView, additionalView, healthConcernsView, breedView, vaccinationView;
    Spinner sexView, ageView;
    Boolean edit;
    Boolean uploadedPhoto = false;
    String name, healthConcerns, additionalInfo, vaccinationStatus, breed, sex, age, traits;
    Dog d;
    Uri selectedImage;

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


        if (edit) {
            d = Dog.currentDog;
            nameView.setText(d.getName());
            picView.setImageBitmap(d.getBitmapImage());
            additionalView.setText(d.additionalInfo);
            healthConcernsView.setText(d.healthConcerns);
            breedView.setText(d.breed);
            vaccinationView.setText(d.getVaccStatus());

            Adapter adapter = sexView.getAdapter();
            String tempSex, tempAge;
            tempSex = d.getSex();
            tempAge = d.getAge();
            if (tempSex.equals("M")) {
                tempSex = "Male";
            } else {
                tempSex = "Female";
            }
            for (int i = 0; i < adapter.getCount(); i++) {
                if (tempSex.equals(adapter.getItem(i).toString())) {
                    sexView.setSelection(i);
                }
            }
            adapter = ageView.getAdapter();
            for (int i = 0; i < adapter.getCount(); i++) {
                if (d.getAge().trim().equals(adapter.getItem(i).toString())) {
                    ageView.setSelection(i);
                }
            }
            createAccount.setText("UPDATE DOG PROFILE");
            toolbar.setTitle("EDIT DOG PROFILE");


        } else {
            toolbar.setTitle("ADD DOG PROFILE");

        }

        if (b == null) {
            Glide.with(this)
                    // .using(new FirebaseImageLoader())
                    .load(FirebaseStorage.getInstance().getReference().child(r.getImage()))
                    .into(headerImage);
            if (edit) {

                Glide.with(this)
                        // .using(new FirebaseImageLoader())
                        .load(MainActivity.storageReference.child(d.getImage()))
                        .into(picView);
            }

        } else {
            Glide.with(this)
                    // .using(new FirebaseImageLoader())
                    .load(b)
                    .into(headerImage);

        }
        headerName.setText(r.getOrganization());

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
            SharedPreferences prefs = getSharedPreferences("Account", Context.MODE_PRIVATE);
            prefs.edit().remove("username").apply();

        } else if (id == R.id.view_dogs) {
            startActivity(new Intent(this, Collection.class).putExtra("viewDogs", true).putExtra("user", "rescue"));
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
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addDog(View v) {

        String action = "";


        name = nameView.getText().toString().trim();
        if (name.equals("")) {
            action = "Please enter the dog's name.\n";
        }
        sex = sexView.getSelectedItem().toString();
        age = ageView.getSelectedItem().toString();
        if (sex.equals("Select Sex")) {
            action += "Please select the dog's sex.\n";

        }
        if (age.equals("Select Age")) {
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
                Dog d = Dog.currentDog;
                if (uploadedPhoto) {
                    d.setBitmapImage(bitmap);
                }
                d.setName(name);
                d.setBreed(breed);
                d.setHealthConcerns(healthConcerns);
                d.setVaccinationStatus(vaccinationStatus);
                d.setAdditionalInfo(additionalInfo);
                d.setSex(sex);
                d.setAge(age);


                Map<String, Object> updates = new HashMap<>();

                String path = addPhotoToFirebase();
                String newPath = d.getImage();
                if (path != null) {
                    newPath = path;
                }


                updates.put("name", name);
                updates.put("breed", breed);
                updates.put("image", newPath);
                updates.put("healthConcerns", healthConcerns);
                updates.put("vaccinationStatus", vaccinationStatus);
                updates.put("additionalInfo", additionalInfo);
                updates.put("sex", sex);
                updates.put("age", age);

                MainActivity.firestore
                        .collection("dog")
                        .document(d.id)
                        .update(updates);


            } else {
                String temporaryTraits = "100010001100001010010001010100";

                Rescue r = Rescue.currentRescue;


                DocumentReference newDoc = MainActivity.firestore.collection("dog").document();


                String path = addPhotoToFirebase();
                //  Rescue.currentRescue.setImageStorageReference(addPhotoToFirebase());
                Dog d =
                        new Dog(name, bitmap, breed, vaccinationStatus, healthConcerns, sex, age, additionalInfo, path);
                d.setTraits(temporaryTraits);
                d.setRescueID(r.getRescueID());
                d.setTraits(temporaryTraits);
                Log.d("test",Integer.toString(Dog.dogList.size()));
                Dog.dogList.add(d);
                Log.d("test",Integer.toString(Dog.dogList.size()));

                d.setId(newDoc.getId());
                d.imageStorageReference = MainActivity.storageReference.child(d.image);


                Map<String, Object> newDog = new HashMap<>();
                newDog.put("name", name);
                newDog.put("breed", breed);
                newDog.put("image", path);
                newDog.put("healthConcerns", healthConcerns);
                newDog.put("vaccinationStatus", vaccinationStatus);
                newDog.put("additionalInfo", additionalInfo);
                newDog.put("sex", sex);
                newDog.put("age", age);
                newDog.put("rescueID", r.getRescueID());
                newDog.put("traits", temporaryTraits);

                newDoc.set(newDog);
                Dog.currentDog = d;
                Log.d("test",Integer.toString(Dog.dogList.size()));

            }
            Intent dashboard = new Intent(this, QuizActivity.class).putExtra("user","rescue");
//            Intent dashboard = new Intent(this, RescueDashboard.class);

            startActivity(dashboard);
            finish();

        }


    }

    public void uploadPhoto(View view) {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, GET_FROM_GALLERY);

    }

//    public void onClickTakeQuiz(View view){
//        Intent quiz = new Intent(this, QuizActivity.class).putExtra("user","rescue");
//        startActivity(quiz);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            selectedImage = data.getData();
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


    public String addPhotoToFirebase() {

        if (selectedImage != null) {

            String path = "img/dogs/" + UUID.randomUUID().toString();
            StorageReference reference = MainActivity.storageReference.child(path);
            reference.putFile(selectedImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
            Log.d("test", reference.toString());

            return path;
        }
        return null;


    }

}


