package com.sigm.fetchyourpet;

import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    EditText usernameView,passwordView;
    String username, password;
    Boolean adopter = true;
    Boolean success = false;
    static Class c = AdopterDashboard.class;



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
        Log.d("test","test");



        username = usernameView.getText().toString().trim();
        password = passwordView.getText().toString().trim();



        MainActivity.firestore.collection("account")
                .whereEqualTo("username", username)
                .whereEqualTo("password",Account.getMD5(password))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if(task.getResult().isEmpty()){
                                result(false);
                            }
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Account a = document.toObject(Account.class);

                                Account.currentAccount = document.toObject(Account.class);


                                result(true);

                            }
                        } else {
                            Log.d("test", "Error getting documents: ", task.getException());
                        }
                    }


                });




    }
    public void result(Boolean success){
        if(!success){
            Toast t = Toast.makeText(this, "Username or password is invalid",
                    Toast.LENGTH_SHORT);
            t.setGravity(Gravity.TOP, Gravity.CENTER, 150);
            t.show();
        }else{
            setValues();
            if(!Account.currentAccount.getIsAdopter()){
                c = RescueDashboard.class;
            }
            //startActivity(new Intent(this, c));
        }


    }



    public void setRescue(Rescue r){
        Rescue.currentRescue=r;
    }

    public void setValues(){
        Account a = Account.currentAccount;

        String s = "adopter";
        if(!a.getIsAdopter()){
            s = "rescue";
        }




        MainActivity.firestore.collection(s)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("test",document.getData().toString());
                                if(Account.currentAccount.getIsAdopter()){
                                    PotentialAdopter p = document.toObject(PotentialAdopter.class);
//                                    if(p.getEmail().equals(username)){
//                                        PotentialAdopter.currentAdopter= p;
//
//                                    }

                                }
                                else{
                                    Rescue r = document.toObject(Rescue.class);
                                    if(r.getUsername().equals(username)) {


                                        Log.d("test", r.getImage());
                                        Rescue.currentRescue = r;
                                        setRescue(r);
                                        Log.d("test", r.getOrganization());
                                        Intent i = new Intent(getApplicationContext(), c);
                                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        getApplicationContext().startActivity(i);

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
            startActivity(new Intent(this, Collection.class));


        } else if (id == R.id.home) {
            startActivity(new Intent(this, MainActivity.class));
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


