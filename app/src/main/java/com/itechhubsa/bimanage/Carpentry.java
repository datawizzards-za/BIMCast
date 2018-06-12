package com.itechhubsa.bimanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Carpentry extends AppCompatActivity implements View.OnClickListener {
    private String _compare = "Carpentry";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.carpentry);
        initialize();
    }
    void initialize()
    {
        Button btn_carpentry_doors = findViewById(R.id.btn_carpentry_doors);
        Button btn_carpentry_kitchen_unit = findViewById(R.id.btn_carpentry_kitchen_unit);
        Button btn_carpentry_other = findViewById(R.id.btn_carpentry_other);
        Button btn_carpentry_shelves = findViewById(R.id.btn_carpentry_shelves);
        Button btn_carpentry_wardrobe = findViewById(R.id.btn_carpentry_wardrobe);
        Button btn_carpentry_windows = findViewById(R.id.btn_carpentry_windows);
        btn_carpentry_windows.setOnClickListener(this);
        btn_carpentry_wardrobe.setOnClickListener(this);
        btn_carpentry_shelves.setOnClickListener(this);
        btn_carpentry_other.setOnClickListener(this);
        btn_carpentry_kitchen_unit.setOnClickListener(this);
        btn_carpentry_doors.setOnClickListener(this);
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(),Home.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())
        {
            case R.id.btn_carpentry_doors:
                intent = new Intent(getBaseContext(),UnitMaintenance.class);
                intent.putExtra("_compare",_compare);
                intent.putExtra("_other",false);
                intent.putExtra("problem","Doors");
                startActivity(intent);
                finish();
                break;
            case R.id.btn_carpentry_kitchen_unit:
                intent = new Intent(getBaseContext(),UnitMaintenance.class);
                intent.putExtra("_compare",_compare);
                intent.putExtra("_other",false);
                intent.putExtra("problem","Kitchen units");
                startActivity(intent);
                finish();
                break;
            case R.id.btn_carpentry_other:
                intent = new Intent(getBaseContext(),UnitMaintenance.class);
                intent.putExtra("_compare",_compare);
                intent.putExtra("_other",true);
                intent.putExtra("problem","Other Specification");
                startActivity(intent);
                finish();
                break;
            case R.id.btn_carpentry_shelves:
                intent = new Intent(getBaseContext(),UnitMaintenance.class);
                intent.putExtra("_compare",_compare);
                intent.putExtra("_other",false);
                intent.putExtra("problem","Shelves");
                startActivity(intent);
                finish();
                break;
            case R.id.btn_carpentry_wardrobe:
                intent = new Intent(getBaseContext(),UnitMaintenance.class);
                intent.putExtra("_compare",_compare);
                intent.putExtra("_other",false);
                intent.putExtra("problem","Wardrobe");
                startActivity(intent);
                finish();
                break;
            case R.id.btn_carpentry_windows:
                intent = new Intent(getBaseContext(),UnitMaintenance.class);
                intent.putExtra("_compare",_compare);
                intent.putExtra("_other",false);
                intent.putExtra("problem","Windows");
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
