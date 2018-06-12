package com.itechhubsa.bimanage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itechhubsa.bimanage.Pojos.Fault;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private final int SIGN_IN_REQUEST_CODE = 2017;
    private DatabaseReference _databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        initialize();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = findViewById(R.id.fabComment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "This must go to contact to search", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    void initialize(){
        _databaseReference = FirebaseDatabase.getInstance().getReference("Faults");
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private static class FaultViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public FaultViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        void setUser(String user){
            TextView tvNames = mView.findViewById(R.id.tv_username);
            tvNames.setText(user);
        }

        void setDescription(String description){
            TextView tvDescription = mView.findViewById(R.id.tv_description);
            tvDescription.setText(description);
        }

        void setDate(String messageDate){
            TextView tvMessageDate = mView.findViewById(R.id.tv_message_time);
            tvMessageDate.setText(messageDate);
        }

        void setImg(Context c, String img){
            ImageView imageView = mView.findViewById(R.id.user_image_profile);
            if(!img.isEmpty()){
                Picasso.with(c).load(img).into(imageView);
                imageView.setRotation(imageView.getRotation()+90);
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Fault, FaultViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Fault, FaultViewHolder>(
                Fault.class,
                R.layout.report_view,
                FaultViewHolder.class,
                _databaseReference
        ) {
            @Override
            protected void populateViewHolder(FaultViewHolder viewHolder, final Fault model, final int position) {
                viewHolder.setDescription(model.getReport_description());
                String stringDate = DateFormat.getDateTimeInstance().format(model.getReport_date());
                viewHolder.setImg(getApplicationContext(), model.getImageUrl());
                viewHolder.setDate(stringDate);
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getBaseContext(), DirectMessage.class);
                        intent.putExtra("fault", model);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
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
    @SuppressLint("MissingPermission")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
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
                intent = new Intent(getBaseContext(),BuildingMaintenance.class);
                //intent.putExtra("_compare",_compare);
                intent.putExtra("_other",false);
                intent.putExtra("_fault","parking fault");
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
                intent = new Intent(getBaseContext(),BuildingMaintenance.class);
                //intent.putExtra("_compare",_compare);
                intent.putExtra("_other",false);
                intent.putExtra("_fault","car fault");
                startActivity(intent);
                finish();
                break;
            case R.id.nav_other:
                intent = new Intent(getBaseContext(),BuildingMaintenance.class);
                //intent.putExtra("_compare",_compare);
                intent.putExtra("_other",true);
                intent.putExtra("_fault","Fault not specified");
                startActivity(intent);
                finish();
                break;
            case R.id.nav_emergency_call:
                Toast.makeText(getBaseContext(),"calling emergency line",Toast.LENGTH_LONG).show();
                Intent emergencyIntent = new Intent(Intent.ACTION_CALL);
                emergencyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emergencyIntent.setData(Uri.parse("tel:" + "911"));
                startActivity(emergencyIntent);
                break;
            case R.id.nav_gates:
                intent = new Intent(getBaseContext(),BuildingMaintenance.class);
                //intent.putExtra("_compare",_compare);
                intent.putExtra("_other",false);
                intent.putExtra("_fault","gates fault");
                startActivity(intent);
                finish();
                break;
            case R.id.nav_send_message:
//                Toast.makeText(getBaseContext(),"search contact then you push message",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getBaseContext(),ChartBoard.class));
                finish();
                break;
            case R.id.nav_washing_lane:
                intent = new Intent(getBaseContext(),BuildingMaintenance.class);
                //intent.putExtra("_compare",_compare);
                intent.putExtra("_other",false);
                intent.putExtra("_fault","washing lanes fault");
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == SIGN_IN_REQUEST_CODE){
            if(resultCode==RESULT_OK){
               Toast.makeText(getBaseContext(),"Loading you messages..",Toast.LENGTH_LONG).show();
            }else{
                //Snackbar.make(home, "We are unable to log messages...\n"+
                        //"check connection!", Snackbar.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(),"We are unable to log messages...\n"+
                        "check connection!",Toast.LENGTH_LONG).show();
            }
        }
    }
}
