package com.sigm.fetchyourpet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

public class SignInActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static Class c = AdopterDashboard.class;
    EditText usernameView, passwordView;
    String username, password;
    Boolean adopter = true;
    Boolean success = false;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Sign in");
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        usernameView = findViewById(R.id.username);
        passwordView = findViewById(R.id.password);
        username = usernameView.getText().toString().trim();
        password = passwordView.getText().toString().trim();

    }

    public void onClickSignIn(View view) {
        Log.d("test", "test");


        username = usernameView.getText().toString().trim();
        password = passwordView.getText().toString().trim();


        MainActivity.firestore.collection("account")
                .whereEqualTo("username", username)
                .whereEqualTo("password", Account.getMD5(password))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                result(false);
                            }
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Account a = document.toObject(Account.class);

                                Account.currentAccount = a;


                                result(true);

                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }


                });


    }

    public void signIn(String username) {

    }

    public void result(Boolean success) {
        if (!success) {
            Toast t = Toast.makeText(this, "Username or password is invalid",
                    Toast.LENGTH_SHORT);
            t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
            t.show();
        } else {
            setValues();
            if (!Account.currentAccount.getIsAdopter()) {
                c = RescueDashboard.class;
            } else {
                c = AdopterDashboard.class;
            }
            //startActivity(new Intent(this, c));
        }


    }


    public void setValues() {
        Account a = Account.currentAccount;

        String s = "adopter";
        if (!a.getIsAdopter()) {
            s = "rescue";
        }


        MainActivity.firestore.collection(s)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            prefs = getSharedPreferences("Account", Context.MODE_PRIVATE);

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("test", document.getData().toString());
                                if (Account.currentAccount.getIsAdopter()) {
                                    PotentialAdopter p = document.toObject(PotentialAdopter.class);
                                    if (p.getUsername().equals(username)) {


                                        Log.d("test", p.getImage());
                                        PotentialAdopter.currentAdopter = p;
                                        Intent i = new Intent(getApplicationContext(), c);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        getApplicationContext().startActivity(i);
                                        prefs.edit().putString("username", username).apply();

                                    }

                                } else {
                                    Rescue r = document.toObject(Rescue.class);
                                    if (r.getUsername().equals(username)) {


                                        Log.d("test", r.getImage());
                                        Rescue.currentRescue = r;
                                        Log.d("test", r.getOrganization());
                                        Intent i = new Intent(getApplicationContext(), c);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        getApplicationContext().startActivity(i);
                                        prefs.edit().putString("username", username).apply();

                                    }

                                }


                            }
                        } else {
                            Log.d("test", "Error getting documents.", task.getException());
                        }
                    }
                });


    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sign_in) {
            //startActivity(new Intent(this, SignInActivity.class));

        } else if (id == R.id.browse_all_animals) {
            startActivity(new Intent(this, Collection.class).putExtra("user", "none"));


        } else if (id == R.id.home) {
            startActivity(new Intent(this, MainActivity.class));
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
}


