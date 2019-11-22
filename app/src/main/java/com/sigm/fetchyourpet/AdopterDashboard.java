
package com.sigm.fetchyourpet;

        import android.content.Intent;
        import android.os.Bundle;
        import android.view.MenuItem;
        import android.view.View;

        import androidx.appcompat.app.ActionBarDrawerToggle;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.core.view.GravityCompat;
        import androidx.drawerlayout.widget.DrawerLayout;

        import com.google.android.material.navigation.NavigationView;

public class AdopterDashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopter_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Dashboard");

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

        if (id == R.id.take_quiz) {
            startActivity(new Intent(this, QuizActivity.class).putExtra("user","adopter"));


        } else if (id == R.id.browse_all_animals) {
            Intent i = new Intent(this, Collection.class);
            i.putExtra("user","adopter");
            startActivity(i);


        } else if (id == R.id.home) {
          //  startActivity(new Intent(this, AdopterDashboard.class));
        }
        else if (id==R.id.logout){
            startActivity(new Intent(this, MainActivity.class));
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void editProfile(View v){

        startActivity(new Intent(this, SignUpActivityAdopter.class).putExtra("editProfile",true));

    }
    public void takeQuiz(View v){
        startActivity(new Intent(this, QuizActivity.class).putExtra("user","adopter"));

    }
    public void viewYourMatches(View v){

    }

}
