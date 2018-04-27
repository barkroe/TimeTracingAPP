package com.example.mabdullah.timetracingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


    Button btn_project;
    Button btn_time;
    Button btn_overview;
    Button btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_home);
             btn_time=(Button)findViewById(R.id.btn_time);
             btn_overview=(Button)findViewById(R.id.btn_overview);
             btn_logout=(Button)findViewById(R.id.btn_logout);
             btn_time.setOnClickListener(this);
             btn_overview.setOnClickListener(this);
             btn_logout.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_time:
                Intent intent= new Intent(HomeActivity.this, AddTimeActivity.class);
                String userId= getIntent().getStringExtra("userId");
                intent.putExtra("userId",userId);
                startActivity(intent);

                break;
        }
        switch (v.getId()){
            case  R.id.btn_overview:
                Intent intent= new Intent(HomeActivity.this, OverviewActivity.class);
                startActivity(intent);

                break;
        }
        switch (v.getId()){
            case  R.id.btn_logout:
                Intent intent= new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                this.finish();
                System.exit(0);

                break;
        }
    }
}
