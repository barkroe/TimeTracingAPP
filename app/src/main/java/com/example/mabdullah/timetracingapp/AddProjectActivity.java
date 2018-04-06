package com.example.mabdullah.timetracingapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddProjectActivity extends AppCompatActivity implements View.OnClickListener {
    EditText etprojectname;
    EditText etDescription;
    Button btnSave;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_project);

        etprojectname=(EditText)findViewById(R.id.et_Projectname);
        etDescription=(EditText)findViewById(R.id.et_description);

        btnSave=(Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);

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
                Intent intent= new Intent(AddProjectActivity.this, HomeActivity.class);
                startActivity(intent);

                break;
        }
    }
}
