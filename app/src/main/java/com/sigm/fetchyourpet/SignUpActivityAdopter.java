package com.sigm.fetchyourpet;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static com.sigm.fetchyourpet.SignUpActivityRescue.isValid;

public class SignUpActivityAdopter extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    EditText firstNameView, lastNameView, zipView, emailView, passwordView, password2;
    String firstName,lastName, zip, email, password, confirmPassword;
    ImageView picView;
    Button picButton;
    Bitmap bitmap = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_adopter);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("SIGN UP!");

        setSupportActionBar(toolbar);

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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sign_in) {
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
        firstNameView = findViewById(R.id.firstName);
        lastNameView = findViewById(R.id.lastName);
        emailView = findViewById(R.id.email);
        passwordView = findViewById(R.id.password);
        password2 = findViewById(R.id.confirmPassword);
        zipView = findViewById(R.id.zip);

        firstName = firstNameView.getText().toString().trim();
        lastName = lastNameView.getText().toString().trim();
        if(firstName.equals("") | lastName.equals("")){
            action = "Please enter your first and last name!";
        }

        zip = zipView.getText().toString().trim();

        email = emailView.getText().toString().trim();
        //here, we will say:
        //if email not in database, then we will create the account
        //if(db.getAccounts.contains(email)){
        //  action="An account already exists with this email!"
        //}


        if (!zip.matches("[0-9]+") | zip.length() < 5) {
            action = "Zip code is invalid! Be sure to enter only numbers.";

        }

        if(!isValid(email)){
            action = "The email address entered is not in a valid format.";
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
            new PotentialAdopter(bitmap,firstName, lastName, Integer.parseInt(zip), email, password);
            Intent dashboard = new Intent(this, Dashboard.class);
            startActivity(dashboard);

        }







    }

}


