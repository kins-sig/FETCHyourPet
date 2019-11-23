package com.sigm.fetchyourpet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

// ...
// When the user selects an option to see the licenses:

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String ACCESS_TOKEN = "zQ0PTVsAoiAAAAAAAAAAGBBC3SX0o2N1bk2odWZjy5iRyN9DoFuWE-hB1u82jYHW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("FETCH! YOUR PET");
        setSupportActionBar(toolbar);
        Log.d("test", "Hi!!!");

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

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
            Log.d("test", "sign in");
            startActivity(new Intent(this, SignInActivity.class));

        } else if (id == R.id.browse_all_animals) {
            startActivity(new Intent(this, Collection.class).putExtra("user","none"));


        } else if (id == R.id.home) {
            // startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.take_quiz) {
            startActivity(new Intent(this, QuizActivity.class).putExtra("user","none"));
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void browsePets(View v) {
        startActivity(new Intent(this, Collection.class).putExtra("user", "none"));

    }

    public void signIn(View v) {
        startActivity(new Intent(this, SignInActivity.class));

    }

    public void signUp(View v) {


        startActivity(new Intent(this, SignUpAccountType.class));
        //    startActivity(new Intent(this, AdopterDashboard.class));

//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//                try {
//
//                    DbxRequestConfig config;
//                    config = new DbxRequestConfig("dropbox/FETCH!yourPet");
//                    DbxClientV2 client;
//                    client = new DbxClientV2(config, ACCESS_TOKEN);
//                    FullAccount account;
//                    DbxUserUsersRequests r1 = client.users();
//                    account = r1.getCurrentAccount();
//                    System.out.println(account.getName().getDisplayName());
//
//                    // Get files and folder metadata from Dropbox root directory
//                    ListFolderResult result = client.files().listFolder("");
//                    while (true) {
//                        for (Metadata metadata : result.getEntries()) {
//                            System.out.println(metadata.getPathLower());
//                        }
//
//                        if (!result.getHasMore()) {
//                            break;
//                        }
//
//                        result = client.files().listFolderContinue(result.getCursor());
//                    }
//
//                } catch (DbxException ex1) {
//                    ex1.printStackTrace();
//                }
//
//
//            }
//        };
//        thread.start();
    }

}




