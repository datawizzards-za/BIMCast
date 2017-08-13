package com.itechhubsa.bimanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.itechhubsa.bimanage.Pojos.Fault;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String _compare = "Home";
    private LinearLayout home;
    private static final int SIGN_IN_REQUEST_CODE = 2017;
    private FirebaseListAdapter<Fault> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(FirebaseAuth.getInstance().getCurrentUser()==null)
        {
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
        }else{
            Toast.makeText(getBaseContext(), "You are logged..", Toast.LENGTH_LONG).show();
        }
        /**
         **
         */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabComment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                ** TODO the contacts format will be UNITS numbers
                 */
                Snackbar.make(view, "This must go to contact to search", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int _id = item.getItemId();
        Intent intent;

        switch(_id){
            case R.id.nav_electricity:
                startActivity(new Intent(getBaseContext(),Electricity.class));
                finish();
                break;
            case R.id.nav_carpentry:
                startActivity(new Intent(getBaseContext(),Carpentry.class));
                finish();
                break;
            case R.id.nav_plumbing:
                startActivity(new Intent(getBaseContext(),Plumbing.class));
                finish();
                break;
            case R.id.nav_parking:
                intent = new Intent(getBaseContext(),Report.class);
                intent.putExtra("_compare",_compare);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_about_us:
                startActivity(new Intent(getBaseContext(),AboutUs.class));
                break;
            case R.id.nav_app_version:
                startActivity(new Intent(getBaseContext(),AppVersion.class));
                break;
            case R.id.nav_car_fault:
                intent = new Intent(getBaseContext(),Report.class);
                intent.putExtra("_compare",_compare);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_emergency_call:
                Toast.makeText(getBaseContext(),"call the emergency line",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_gates:
                intent = new Intent(getBaseContext(),Report.class);
                intent.putExtra("_compare",_compare);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_send_message:
                Toast.makeText(getBaseContext(),"search contact then you push message",Toast.LENGTH_LONG).show();
                break;
            case R.id.nav_washing_lane:
                intent = new Intent(getBaseContext(),Report.class);
                intent.putExtra("_compare",_compare);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == SIGN_IN_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                //Snackbar.make(home, "Loading you messages..", Snackbar.LENGTH_LONG).show();
                displayMessage();
            }else{
                //Snackbar.make(home, "We are unable to log messages...\n"+
                        //"check connection!", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    void displayMessage()
    {

    }
}