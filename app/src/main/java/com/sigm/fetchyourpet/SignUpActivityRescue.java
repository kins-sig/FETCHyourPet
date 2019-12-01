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
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public class SignUpActivityRescue extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static int GET_FROM_GALLERY = 1;
    EditText nameView, streetView, cityView, zipView, stateView, emailView, passwordView, password2, usernameView;
    String name, street, city, zip, state, email, password, confirmPassword, username;
    TextView passwordLabel;
    ImageView picView;
    Button picButton, createAccount;
    Boolean edit, uploadedPhoto = false;
    Uri selectedImage;

    Bitmap bitmap = null;
    Class c = MainActivity.class;


    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_rescue);
        Toolbar toolbar = findViewById(R.id.toolbar);
        picView = findViewById(R.id.pic);
        picButton = findViewById(R.id.picButton);

        edit = getIntent().getBooleanExtra("editProfile", false);

//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);

        createAccount = findViewById(R.id.createAccount);
        picView = findViewById(R.id.pic);
        nameView = findViewById(R.id.organization);
        emailView = findViewById(R.id.email);
        passwordView = findViewById(R.id.password);
        passwordLabel = findViewById(R.id.passwordLabel);
        password2 = findViewById(R.id.password2);
        zipView = findViewById(R.id.zip);
        streetView = findViewById(R.id.street);
        stateView = findViewById(R.id.state);
        cityView = findViewById(R.id.city);
        usernameView = findViewById(R.id.username);
        NavigationView navigationView = findViewById(R.id.nav_view);

        View hView = navigationView.getHeaderView(0);
        ImageView image = hView.findViewById(R.id.headerImageView);
        TextView name = hView.findViewById(R.id.headerTextView);


        //boolean test = true;
        boolean test = false;

        if (test) {
            nameView.setText("Paws Place");
            emailView.setText("woofwoofbarkbark@dogsville.com");
            zipView.setText("28403");
            streetView.setText("Bark Street");
            stateView.setText("North Caroline");
            cityView.setText("Barksville");
            //passwordView.setText("Hi");
            //password2.setText("Hi");
        }
        if (edit) {
            c = RescueDashboard.class;
            Rescue r = Rescue.currentRescue;

            toolbar.setTitle("EDIT PROFILE");
            createAccount.setText("UPDATE PROFILE");
            usernameView.setText(r.getUsername());
            usernameView.setEnabled(false);
            nameView.setText(r.getOrganization());
            emailView.setText(r.getEmail());
            passwordLabel.setText("NEW PASSWORD");
            zipView.setText(r.getZip());
            streetView.setText(r.getStreet());
            stateView.setText(r.getState());
            cityView.setText(r.getCity());

            Bitmap b = r.getPhoto();
            if (b == null) {


                Glide.with(this)
                        // .using(new FirebaseImageLoader())
                        .load(MainActivity.storageReference.child(r.getImage()))
                        .into(image);
                if (edit) {
                    Glide.with(this)
                            // .using(new FirebaseImageLoader())
                            .load(MainActivity.storageReference.child(r.getImage()))
                            .into(picView);
                }

            } else {
                Glide.with(this)
                        // .using(new FirebaseImageLoader())
                        .load(b)
                        .into(image);
            }
            name.setText(r.getOrganization());

            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.rescue_drawer);

        } else {
            toolbar.setTitle("SIGN UP!");

        }


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
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.sign_in) {
            Log.d("test", "sign in");
            startActivity(new Intent(this, SignInActivity.class));

        } else if (id == R.id.browse_all_animals) {
            startActivity(new Intent(this, Collection.class).putExtra("user", "rescue"));


        } else if (id == R.id.home) {
            startActivity(new Intent(this, c));
        } else if (id == R.id.logout) {
            startActivity(new Intent(this, MainActivity.class));
            SharedPreferences prefs = getSharedPreferences("Account", Context.MODE_PRIVATE);
            prefs.edit().remove("username").apply();
        } else if (id == R.id.view_dogs) {
            startActivity(new Intent(this, Collection.class).putExtra("viewDogs", true).putExtra("user", "rescue"));
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

    public void createAccount(View view) {
        String action = "";
        Intent dashboard = new Intent(this, RescueDashboard.class);

        if (bitmap == null && !edit) {
            action = "Please select a photo.\n";
        }


        name = nameView.getText().toString().trim();
        if (name.equals("")) {
            action += "Please enter your organization's name.\n";
        }
        street = streetView.getText().toString().trim();
        if (street.equals("")) {
            action += "Please enter the street address of your organization.\n";
        }
        city = cityView.getText().toString().trim();
        if (city.equals("")) {
            action += "Please enter the city in which your organization is located.\n";
        }
        zip = zipView.getText().toString().trim();

        state = stateView.getText().toString().trim();
        if (state.equals("")) {
            action += "Please enter the state in which your organization is located.\n";
        }
        email = emailView.getText().toString().trim();
        //here, we will say:
        //if email not in database, then we will create the account
        //if(db.getAccounts.contains(email)){
        //  action="An account already exists with this email!"
        //}
        if (!isValid(email)) {
            action += "The email address entered is not in a valid format.\n";
        }
        if (!zip.matches("[0-9]+") | zip.length() < 5) {
            action += "Zip code is invalid! Be sure to enter only numbers.\n";

        }
        username = usernameView.getText().toString().trim();
        if (username.equals("")) {
            action += "Please enter a username.\n";
        }

        //we probably need to sanitize the password input
        password = passwordView.getText().toString().trim();
        confirmPassword = password2.getText().toString().trim();

        if (password.equals("") && !edit) {
            action += "Please enter a password\n";
        } else if (!password.equals(confirmPassword)) {
            action += "Passwords do not match. Please enter them again.\n";
            passwordView.setText("");
            password2.setText("");
        }


        if (!action.equals("")) {
            Toast t = Toast.makeText(this, action.trim(),
                    Toast.LENGTH_SHORT);
            t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
            t.show();
        } else {
            //    public Rescue(String name, String street, String city, String state, int zip, String email, String password){
            if (edit) {
                Rescue r = Rescue.currentRescue;
                if (uploadedPhoto) {
                    r.setPhoto(bitmap);
                    //picView.setImageBitmap(bitmap);
                }
                r.setOrganization(name);
                r.setState(state);
                r.setStreet(street);
                r.setZip(zip);
                r.setEmail(email);
                r.setCity(city);

                //DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("rescue").child(r.getRescueID());

                Map<String, Object> updates = new HashMap<>();

                String path = addPhotoToFirebase();
                String newPath = r.getImage();
                if (path != null) {
                    newPath = path;
                }


                updates.put("city", city);
                updates.put("email", email);
                updates.put("image", newPath);
                updates.put("organization", name);
                updates.put("state", state);
                updates.put("street", street);
                updates.put("zip", zip);//etc

                MainActivity.firestore
                        .collection("rescue")
                        .document(r.getRescueID())
                        .update(updates);
                if (!Account.getMD5(password).equals(Account.currentAccount.getPassword()) && !password.equals("") && password.equals(confirmPassword)) {
                    MainActivity.firestore
                            .collection("account")
                            .document(r.getUsername())
                            .update("password", Account.getMD5(password));
                }
                Toast t = Toast.makeText(this, "Rescue profile updated!",
                        Toast.LENGTH_SHORT);

                t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
                t.show();
                startActivity(dashboard);
                finish();


                // ref.updateChildren(updates);
//                if(!password.equals("")) {
//                    r.setPassword(password);
//                }
            } else {

                MainActivity.firestore.collection("account").whereEqualTo("username", username).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                addAccount();
                                Intent dashboard = new Intent(getApplicationContext(), RescueDashboard.class);
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

    public void addAccount() {

        String path = addPhotoToFirebase();
        //  Rescue.currentRescue.setImageStorageReference(addPhotoToFirebase());


        DocumentReference newDoc = MainActivity.firestore.collection("rescue").document();

        Map<String, Object> newRescue = new HashMap<>();
        newRescue.put("city", city);
        newRescue.put("email", email);
        newRescue.put("organization", name);
        newRescue.put("state", state);
        newRescue.put("street", street);
        newRescue.put("username", username);
        newRescue.put("zip", zip);
        newRescue.put("image", path);
        newRescue.put("rescueID", newDoc.getId());

        newDoc.set(newRescue);


        Map<String, Object> newAccount = new HashMap<>();
        newAccount.put("username", username);
        newAccount.put("password", Account.getMD5(password));
        newAccount.put("isAdopter", false);


        MainActivity.firestore.collection("account").document(username)
                .set(newAccount);


        Rescue.currentRescue =
                new Rescue(bitmap, name, street, city, state, zip, email, path);
        Rescue.currentRescue.setRescueID(newDoc.getId());
        Rescue.currentRescue.setUsername(username);
    }

    public String addPhotoToFirebase() {

        if (selectedImage != null) {

            String path = "img/rescues/" + UUID.randomUUID().toString();
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
}