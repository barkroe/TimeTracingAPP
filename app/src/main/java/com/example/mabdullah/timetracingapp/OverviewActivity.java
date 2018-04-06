package com.example.mabdullah.timetracingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OverviewActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        btnBack=(Button) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_save:
//                hier soll die Data gespeichert werden

                break;
        }
        switch (v.getId()){
            case  R.id.btn_back:
                // zurück zur Menu
                Intent intent= new Intent(OverviewActivity.this, HomeActivity.class);
                startActivity(intent);

                break;
        }
    }

}
