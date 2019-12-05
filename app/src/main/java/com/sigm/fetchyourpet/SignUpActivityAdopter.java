package com.sigm.fetchyourpet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QuerySnapshot;
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

import static com.sigm.fetchyourpet.SignUpActivityRescue.isValid;

public class SignUpActivityAdopter extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static int GET_FROM_GALLERY = 1;
    EditText firstNameView, zipView, emailView, passwordView, password2, usernameView;
    String firstName, zip, email, password, confirmPassword, username;
    TextView passwordLabel;
    ImageView picView;
    Button picButton, createAccount;
    PotentialAdopter a;
    Bitmap bitmap = null;
    Boolean edit, uploadedPhoto = false;
    Class c = MainActivity.class;
    Uri selectedImage;

    /**
     * Initialization and setting the layout
     * @param savedInstanceState - saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_adopter);
        edit = getIntent().getBooleanExtra("editProfile", false);
        Toolbar toolbar = findViewById(R.id.toolbar);

        createAccount = findViewById(R.id.createAccount);
        picView = findViewById(R.id.pic);
        picButton = findViewById(R.id.picButton);
        firstNameView = findViewById(R.id.firstName);
        emailView = findViewById(R.id.email);
        passwordView = findViewById(R.id.password);
        passwordLabel = findViewById(R.id.passwordLabel);
        password2 = findViewById(R.id.confirmPassword);
        usernameView = findViewById(R.id.username);
        zipView = findViewById(R.id.zip);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View hView = navigationView.getHeaderView(0);
        ImageView image = hView.findViewById(R.id.headerImageView);
        TextView name = hView.findViewById(R.id.headerTextView);
        if (edit) {
            toolbar.setTitle("EDIT PROFILE");
            createAccount.setText("UPDATE PROFILE");
            passwordLabel.setText("NEW PASSWORD");
            c = AdopterDashboard.class;
            a = PotentialAdopter.currentAdopter;
            firstNameView.setText(a.getFirstName());
            emailView.setText(a.getEmail());
            zipView.setText(a.getZip());
            usernameView.setText(a.getUsername());
            usernameView.setEnabled(false);

            Bitmap b = a.getPhoto();
            if (b == null) {


                Glide.with(this)
                        // .using(new FirebaseImageLoader())
                        .load(FirebaseStorage.getInstance().getReference().child(a.getImage()))
                        .into(image);
                if (edit) {

                    Glide.with(this)
                            // .using(new FirebaseImageLoader())
                            .load(MainActivity.storageReference.child(a.getImage()))
                            .into(picView);
                }

            } else {
                Glide.with(this)
                        // .using(new FirebaseImageLoader())
                        .load(b)
                        .into(image);
            }
            name.setText(a.getFirstName());
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.adopter_drawer);


        } else {
            toolbar.setTitle("SIGN UP!");
        }

        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


    }
    /**
     * Handle navigation drawer opening/closing
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Handles all of the option's onClicks
     * @param item - item selected
     * @return - true to display the item as the selected item
     */
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
    /**
     *
     * @param item - the item that was clicked
     * @return true to display the item as the selected item
     * Handles all actions in the navigation bar.
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sign_in) {
            startActivity(new Intent(this, SignInActivity.class));

        } else if (id == R.id.browse_all_animals) {
            startActivity(new Intent(this, Collection.class).putExtra("user", "adopter"));


        } else if (id == R.id.home) {
            startActivity(new Intent(this, c));
        } else if (id == R.id.logout) {
            startActivity(new Intent(this, MainActivity.class));
            SharedPreferences prefs = getSharedPreferences("Account", Context.MODE_PRIVATE);
            Dog.resetDogList();

            prefs.edit().remove("username").apply();
        } else if (id == R.id.take_quiz) {
            startActivity(new Intent(this, QuizActivity.class).putExtra("user", "adopter"));


        } else if (id == R.id.license) {
            new LibsBuilder()
                    .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                    .withAboutIconShown(true)
                    .withAboutAppName(getString(R.string.app_name))
                    .withAboutVersionShown(true)
                    .withAboutDescription(getString(R.string.appInfo))
                    .withLicenseDialog(true)
                    .withActivityTitle("LICENSES")

                    .withLicenseShown(true)
                    .start(this);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void createAccount(View view) {
        String action = "";
        Intent dashboard = new Intent(this, AdopterDashboard.class);

        if (bitmap == null && !edit) {
            action = "Please select a photo.\n";
        }


        firstName = firstNameView.getText().toString().trim();
        if (firstName.equals("")) {
            action = "Please enter your first name!";
        }

        zip = zipView.getText().toString().trim();

        email = emailView.getText().toString().trim();
        //here, we will say:
        //if email not in database, then we will create the account
        //if(db.getAccounts.contains(email)){
        //  action="An account already exists with this email!"
        //}


        if (!zip.matches("[0-9]+") || zip.length() > 5) {
            action = "Zip code is invalid! Be sure to enter only numbers.";

        }

        username = usernameView.getText().toString().trim();
        if (username.equals("")) {
            action += "Please enter a username.\n";
        }

        if (!isValid(email)) {
            action = "The email address entered is not in a valid format.";
        }


        //we probably need to sanitize the password input
        password = passwordView.getText().toString().trim();
        confirmPassword = password2.getText().toString().trim();
        if (password.equals("") && !edit) {
            action = "Please enter a password";
        } else if (!password.equals(confirmPassword)) {
            action = "Passwords do not match. Please enter them again.";
            passwordView.setText("");
            password2.setText("");

        }
        boolean test = false;

        if (firstName.equals("test")) {
            action = "";
            test = true;
        }


        if (!action.equals("")) {
            Toast t = Toast.makeText(this, action,
                    Toast.LENGTH_SHORT);
            t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
            t.show();
        } else {
            //    public Rescue(String name, String street, String city, String state, int zip, String email, String password){
            //Bitmap bitmap = ((BitmapDrawable) picView.getDrawable()).getBitmap();

            if (edit) {
                if (uploadedPhoto) {
                    a.setPhoto(bitmap);
                }
                a.setFirstName(firstName);
                a.setZip(zip);
                a.setEmail(email);


                Map<String, Object> updates = new HashMap<>();

                String path = addPhotoToFirebase();
                String newPath = a.getImage();
                if (path != null) {
                    newPath = path;
                }
                updates.put("firstName", firstName);
                updates.put("zip", zip);
                updates.put("image", newPath);
                updates.put("email", email);
                MainActivity.firestore
                        .collection("rescue")
                        .document(a.getAdopterID())
                        .update(updates);

                if (!Account.getMD5(password).equals(Account.currentAccount.getPassword()) && !password.equals("") && password.equals(confirmPassword)) {
                    MainActivity.firestore
                            .collection("account")
                            .document(a.getUsername())
                            .update("password", Account.getMD5(password));
                }
                Toast t = Toast.makeText(this, "Profile updated!",
                        Toast.LENGTH_SHORT);

                t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
                t.show();
                startActivity(dashboard);
                finish();


            } else {

                MainActivity.firestore.collection("account").whereEqualTo("username", username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                addAccount();
                                Intent dashboard = new Intent(getApplicationContext(), AdopterDashboard.class);
                                startActivity(dashboard);
                                finish();


                            } else {
                                showToast(true);

                            }

                        } else {
                            showToast(false);
                        }
                    }


                });


            }


        }


    }

    public void showToast(Boolean usernameTaken) {
        String action = "";
        if (usernameTaken) {
            action = "Username is already taken";
        } else {
            action = "An error has occurred. Please try again.";
        }
        Toast t = Toast.makeText(this, action,
                Toast.LENGTH_SHORT);
        t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
        t.show();
    }

    public void uploadPhoto(View view) {
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, GET_FROM_GALLERY);

    }

    public void addAccount() {
        DocumentReference newDoc = MainActivity.firestore.collection("adopter").document();
        String path = addPhotoToFirebase();


        Map<String, Object> newRescue = new HashMap<>();
        newRescue.put("firstName", firstName);
        newRescue.put("zip", zip);
        newRescue.put("image", path);
        newRescue.put("email", email);
        newRescue.put("username", username);
        newRescue.put("adopterID", newDoc.getId());

        newDoc.set(newRescue);


        Map<String, Object> newAccount = new HashMap<>();

        newAccount.put("username", username);
        newAccount.put("password", Account.getMD5(password));
        newAccount.put("isAdopter", true);

        MainActivity.firestore.collection("account").document(username)
                .set(newAccount);

        PotentialAdopter.currentAdopter = new PotentialAdopter(bitmap, firstName, zip, email, path);
        PotentialAdopter.currentAdopter.setAdopterID(newDoc.getId());
        PotentialAdopter.currentAdopter.setUsername(username);
        Account.currentAccount = new Account(username, password, true);


    }

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

            String path = "img/adopters/" + UUID.randomUUID().toString();
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


