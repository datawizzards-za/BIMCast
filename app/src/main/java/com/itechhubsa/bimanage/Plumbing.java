package com.itechhubsa.bimanage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Plumbing extends AppCompatActivity implements View.OnClickListener {
    private final String _compare = "Plumbing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plumbing);
        initialize();
    }

    void initialize() {
        Button btnWaterTaps = (Button) findViewById(R.id.btnWaterTaps);
        Button btnToilet = (Button) findViewById(R.id.btnToilet);
        Button btnSinkBasin = (Button) findViewById(R.id.btnSinkBasin);
        Button btnPlumbingOther = (Button) findViewById(R.id.btnPlumbingOther);
        Button btnGeyser = (Button) findViewById(R.id.btnGeyser);
        Button btnBathShower = (Button) findViewById(R.id.btnBathShower);
        btnBathShower.setOnClickListener(this);
        btnGeyser.setOnClickListener(this);
        btnPlumbingOther.setOnClickListener(this);
        btnSinkBasin.setOnClickListener(this);
        btnToilet.setOnClickListener(this);
        btnWaterTaps.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getBaseContext(), Home.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnWaterTaps:
                intent = new Intent(getBaseContext(), Report.class);
                intent.putExtra("_compare", _compare);
                intent.putExtra("problem", "Water Taps");
                startActivity(intent);
                finish();
                break;
            case R.id.btnToilet:
                intent = new Intent(getBaseContext(), Report.class);
                intent.putExtra("_compare", _compare);
                intent.putExtra("problem", "Toilets");
                startActivity(intent);
                finish();
                break;
            case R.id.btnSinkBasin:
                intent = new Intent(getBaseContext(), Report.class);
                intent.putExtra("_compare", _compare);
                intent.putExtra("problem", "Sink and Basin");
                startActivity(intent);
                finish();
                break;
            case R.id.btnPlumbingOther:
                intent = new Intent(getBaseContext(), Report.class);
                intent.putExtra("_compare", _compare);
                intent.putExtra("problem", "Other specification");
                startActivity(intent);
                finish();
                break;
            case R.id.btnGeyser:
                intent = new Intent(getBaseContext(), Report.class);
                intent.putExtra("_compare", _compare);
                intent.putExtra("problem", "Geyser");
                startActivity(intent);
                finish();
                break;
            case R.id.btnBathShower:
                intent = new Intent(getBaseContext(), Report.class);
                intent.putExtra("_compare", _compare);
                intent.putExtra("problem", "Bath and Shower");
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }
}
