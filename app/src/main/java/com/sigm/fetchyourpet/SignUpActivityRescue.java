package com.sigm.fetchyourpet;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
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

    EditText nameView, streetView, cityView, zipView, stateView, emailView, passwordView, password2;
    String name, street, city, zip, state, email, password, confirmPassword;
    ImageView picView;
    Button picButton;
    Bitmap bitmap = null;

    private static int GET_FROM_GALLERY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_rescue);
        Toolbar toolbar = findViewById(R.id.toolbar);
        picView = findViewById(R.id.pic);
        picButton = findViewById(R.id.picButton);

//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("SIGN UP!");

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
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
            Log.d("test","sign in");
            startActivity(new Intent(this, SignInActivity.class));

        } else if (id == R.id.browse_all_animals) {
            startActivity(new Intent(this, Collection.class));


        } else if (id == R.id.home) {
            startActivity(new Intent(this, MainActivity.class));
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;





    }

    public void createAccount(View view) {
        String action = "";
        nameView = findViewById(R.id.organization);
        streetView = findViewById(R.id.street);
        cityView = findViewById(R.id.city);
        zipView = findViewById(R.id.zip);
        stateView = findViewById(R.id.state);
        emailView = findViewById(R.id.email);
        passwordView = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);

        name = nameView.getText().toString().trim();
        if(name.equals("")){
            action = "Please enter your organization's name.";
        }
        street = streetView.getText().toString().trim();
        if(street.equals("")){
            action = "Please enter the street address of your organization.";
        }
        city = cityView.getText().toString().trim();
        if(city.equals("")){
            action = "Please enter the city in which your organization is located.";
        }
        zip = zipView.getText().toString().trim();

        state = stateView.getText().toString().trim();
        if(state.equals("")){
            action = "Please enter the state in which your organization is located.";
        }
        email = emailView.getText().toString().trim();
        //here, we will say:
        //if email not in database, then we will create the account
        //if(db.getAccounts.contains(email)){
        //  action="An account already exists with this email!"
        //}
        if(!isValid(email)){
            action = "The email address entered is not in a valid format.";
        }
        if (!zip.matches("[0-9]+") | zip.length() < 5) {
            action = "Zip code is invalid! Be sure to enter only numbers.";

        }



        //we probably need to sanitize the password input
        password = passwordView.getText().toString().trim();
        if(password.equals("")){
            action = "Please enter a password";
        }
        else if(!password.equals(password2.getText().toString().trim())){
            action = "Passwords do not match. Please enter them again.";
            passwordView.setText("");
            password2.setText("");
        }


        if(!action.equals("")){
            Toast t = Toast.makeText(this, action,
                    Toast.LENGTH_SHORT);
            t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
            t.show();
        }
        else{
            //    public Rescue(String name, String street, String city, String state, int zip, String email, String password){
            new Rescue(bitmap,name, street, city, state, Integer.parseInt(zip), email, password);
            Intent dashboard = new Intent(this, Dashboard.class);
            startActivity(dashboard);

        }







    }
    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public void uploadPhoto(View view){
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, GET_FROM_GALLERY);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                Glide
                        .with(this)
                        .load(bitmap)
                        .into(picView);
                picButton.setText("CHANGE PHOTO");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}