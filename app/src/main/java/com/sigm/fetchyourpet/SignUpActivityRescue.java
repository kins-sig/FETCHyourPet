package com.sigm.fetchyourpet;

import android.app.Activity;
import android.content.Intent;
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

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

public class SignUpActivityRescue extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static int GET_FROM_GALLERY = 1;
    EditText nameView, streetView, cityView, zipView, stateView, emailView, passwordView, password2;
    String name, street, city, zip, state, email, password, confirmPassword;
    TextView passwordLabel;
    ImageView picView;
    Button picButton, createAccount;
    Boolean edit, uploadedPhoto = false;

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
        NavigationView navigationView = findViewById(R.id.nav_view);

        View hView = navigationView.getHeaderView(0);
        ImageView image = hView.findViewById(R.id.headerImageView);
        TextView name = hView.findViewById(R.id.headerTextView);
        if(edit){
            c = AdopterDashboard.class;
            toolbar.setTitle("EDIT PROFILE");
            createAccount.setText("UPDATE PROFILE");

            Rescue r = Rescue.currentRescue;
            nameView.setText(r.getOrganization());
            emailView.setText(r.getEmail());
            passwordLabel.setText("NEW PASSWORD");
            zipView.setText(r.getZip());
            streetView.setText(r.getStreet());
            stateView.setText(r.getState());
            cityView.setText(r.getCity());
            Bitmap b = r.getPhoto();
            if (b != null) {
                picView.setImageBitmap(b);
                image.setImageBitmap(b);

            }

            else {
                image.setImageResource(R.drawable.josiefetch);
            }
            name.setText(r.getOrganization());

            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.rescue_drawer);

        }
        else{
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
            startActivity(new Intent(this, Collection.class).putExtra("user","rescue"));


        } else if (id == R.id.home) {
            startActivity(new Intent(this, c));
        } else if (id == R.id.logout) {
            startActivity(new Intent(this, MainActivity.class));
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

    public void createAccount(View view) {
        String action = "";


        name = nameView.getText().toString().trim();
        if (name.equals("")) {
            action = "Please enter your organization's name.";
        }
        street = streetView.getText().toString().trim();
        if (street.equals("")) {
            action = "Please enter the street address of your organization.";
        }
        city = cityView.getText().toString().trim();
        if (city.equals("")) {
            action = "Please enter the city in which your organization is located.";
        }
        zip = zipView.getText().toString().trim();

        state = stateView.getText().toString().trim();
        if (state.equals("")) {
            action = "Please enter the state in which your organization is located.";
        }
        email = emailView.getText().toString().trim();
        //here, we will say:
        //if email not in database, then we will create the account
        //if(db.getAccounts.contains(email)){
        //  action="An account already exists with this email!"
        //}
        if (!isValid(email)) {
            action = "The email address entered is not in a valid format.";
        }
        if (!zip.matches("[0-9]+") | zip.length() < 5) {
            action = "Zip code is invalid! Be sure to enter only numbers.";

        }


        //we probably need to sanitize the password input
        password = passwordView.getText().toString().trim();
        if (password.equals("") && !edit) {
            action = "Please enter a password";
        } else if (!password.equals(password2.getText().toString().trim())) {
            action = "Passwords do not match. Please enter them again.";
            passwordView.setText("");
            password2.setText("");
        }


        if (!action.equals("")) {
            Toast t = Toast.makeText(this, action,
                    Toast.LENGTH_SHORT);
            t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
            t.show();
        } else {
            //    public Rescue(String name, String street, String city, String state, int zip, String email, String password){
            if(edit){
                Rescue r = Rescue.currentRescue;
                if(uploadedPhoto) {
                    r.setPhoto(bitmap);
                }
                r.setName(name);
                r.setState(state);
                r.setStreet(street);
                r.setZip(Integer.parseInt(zip));
                r.setEmail(email);
                r.setCity(city);
                if(!password.equals("")) {
                    r.setPassword(password);
                }
            }else {
                Rescue.currentRescue =
                        new Rescue(bitmap, name, street, city, state, Integer.parseInt(zip), email, password);
            }
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