package com.sigm.fetchyourpet;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Collection extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RecyclerView cardRecycler;
    CaptionedImagesAdapter adapter;
    LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Collection");

        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        adapter = new CaptionedImagesAdapter();
        cardRecycler = findViewById(R.id.pet_recycler);

        List<Dog> dogs = new ArrayList<>();
        dogs.add(new Dog("Josie",R.drawable.josiefetch));
        dogs.add(new Dog("Rex",R.drawable.dog1));
        dogs.add(new Dog("Sailor",R.drawable.dog2));
        dogs.add(new Dog("Lil' Shit",R.drawable.dog3));
        dogs.add(new Dog("Thicky Vicky",R.drawable.dog4));
        dogs.add(new Dog("Queenie",R.drawable.dog5));
        dogs.add(new Dog("Squishy",R.drawable.dog6));
        dogs.add(new Dog("Duke",R.drawable.dog7));
        dogs.add(new Dog("Bogue",R.drawable.dog8));
        dogs.add(new Dog("Walter",R.drawable.dog9));
        dogs.add(new Dog("Mango",R.drawable.dog10));
        dogs.add(new Dog("Josie",R.drawable.josiefetch));
        dogs.add(new Dog("Rex",R.drawable.dog1));
        dogs.add(new Dog("Sailor",R.drawable.dog2));
        dogs.add(new Dog("Lil' Shit",R.drawable.dog3));
        dogs.add(new Dog("Thicky Vicky",R.drawable.dog4));
        dogs.add(new Dog("Queenie",R.drawable.dog5));
        dogs.add(new Dog("Squishy",R.drawable.dog6));
        dogs.add(new Dog("Duke",R.drawable.dog7));
        dogs.add(new Dog("Bogue",R.drawable.dog8));
        dogs.add(new Dog("Walter",R.drawable.dog9));
        dogs.add(new Dog("Mango",R.drawable.dog10));

        adapter.setDogs(dogs);
        layoutManager = new GridLayoutManager(this, 4);

        cardRecycler.setLayoutManager(layoutManager);
        cardRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();









    }

    public void displayPets(){
//        List<Dog> dogs = new ArrayList<>();
//
//        dogs.add(new Dog("Walter",R.drawable.josiefetch));
//        dogs.add(new Dog("Josie",R.drawable.josiefetch));
//        dogs.add(new Dog("PHIAEIORHG",R.drawable.josiefetch));
//        dogs.add(new Dog("Walter",R.drawable.josiefetch));
//        dogs.add(new Dog("SDSSSS",R.drawable.josiefetch));
//        dogs.add(new Dog("Walter",R.drawable.josiefetch));
//        dogs.add(new Dog("Walter",R.drawable.josiefetch));
//        dogs.add(new Dog("Walter",R.drawable.josiefetch));
//        dogs.add(new Dog("Walter",R.drawable.josiefetch));
//        dogs.add(new Dog("Walter",R.drawable.josiefetch));
//        dogs.add(new Dog("Walter",R.drawable.josiefetch));
//
//        adapter.setDogs(dogs);
//        layoutManager = new GridLayoutManager(this, 4);
//
//        cardRecycler.setLayoutManager(layoutManager);
//        cardRecycler.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//
//

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
            //In the future, add code to determine if the user is signed in or not. Then send
            //them to the right location.
            startActivity(new Intent(this, MainActivity.class));
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}