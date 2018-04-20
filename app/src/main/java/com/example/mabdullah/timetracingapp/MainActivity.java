package com.example.mabdullah.timetracingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

   /* DBManager db= new DBManager(this);*/

    EditText etuserName;
    EditText etpassword;
    Button btnLogin;
    String name="";
    String pwd="";
User responseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etuserName=(EditText)findViewById(R.id.et_userName);
        etpassword=(EditText)findViewById(R.id.et_description);
        btnLogin=(Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
         name      =  etuserName.getText().toString();
         pwd      =  etpassword.getText().toString();

    }

    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_login:
                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
               break;

        }

    }

}
