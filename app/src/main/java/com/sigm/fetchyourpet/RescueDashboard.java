package com.sigm.fetchyourpet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.FirebaseStorage;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;


/**
 * The default screen for Rescues
 * @author Dylan
 */
public class RescueDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    /**
     * Initialization and setting the layout
     * @param savedInstanceState - saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rescue_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        View hView = navigationView.getHeaderView(0);
        ImageView image = hView.findViewById(R.id.headerImageView);
        TextView name = hView.findViewById(R.id.headerTextView);
        Rescue r = Rescue.currentRescue;

        Bitmap b = r.getPhoto();

        //If the rescue uploaded a photo in this session, b will not be null. Use that photo if
        //it is not null. Else, use the photo stored in the database. This is done because, in some
        //instances, the photo will not be uploaded to the database quick enough, so we must use
        //the photo from the current session.
        if (b == null) {


            Glide.with(this)
                    // .using(new FirebaseImageLoader())
                    .load(FirebaseStorage.getInstance().getReference().child(r.getImage()))
                    .into(image);

        } else {
            Glide.with(this)
                    // .using(new FirebaseImageLoader())
                    .load(b)
                    .into(image);
        }
        name.setText(r.getOrganization());


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
            //super.onBackPressed();
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

        if (id == R.id.take_quiz) {
            startActivity(new Intent(this, QuizActivity.class).putExtra("user", "rescue"));


        } else if (id == R.id.browse_all_animals) {
            Intent i = new Intent(this, Collection.class);
            i.putExtra("user", "rescue");
            startActivity(i);


        } else if (id == R.id.home) {
            //  startActivity(new Intent(this, AdopterDashboard.class));
        } else if (id == R.id.logout) {
            startActivity(new Intent(this, MainActivity.class));
            SharedPreferences prefs = getSharedPreferences("Account", Context.MODE_PRIVATE);
            Dog.resetDogList();

            prefs.edit().remove("username").apply();
        } else if (id == R.id.view_dogs) {
            startActivity(new Intent(this, Collection.class).putExtra("viewDogs", true).putExtra("user", "rescue"));
        } else if (id == R.id.license) {
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
        } else if (id == R.id.quiz) {
            startActivity(new Intent(this, QuizActivity.class).putExtra("user", "none"));
//            SharedPreferences prefs = getSharedPreferences("Account", Context.MODE_PRIVATE);
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Launches the rescue signup activity with appropriate intents
     * @param v - the button view
     */
    public void editProfile(View v) {

        startActivity(new Intent(this, SignUpActivityRescue.class).putExtra("editProfile", true));

    }
    /**
     * Launches the add dog activity
     * @param v - the button view
     */
    public void registerDog(View v) {
        startActivity(new Intent(this, AddDog.class));

    }
    /**
     * Launches the collection activity with appropriate intents
     * @param v - the button view
     */
    public void viewYourDogs(View v) {
        startActivity(new Intent(this, Collection.class).putExtra("viewDogs", true).putExtra("user", "rescue"));


    }

}
