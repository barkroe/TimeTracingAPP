package com.example.mabdullah.timetracingapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   /* DBManager db= new DBManager(this);*/

    EditText etuserName;
    EditText etpassword;
    Button btnLogin;
    String name="";
    String pwd="";


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



       /* SQLiteDatabase mydata= db.getWritableDatabase();*/

    }


   private class HttpRequestTask extends AsyncTask<Void, Void, User> {
        @Override
        protected User doInBackground(Void... params) {
            try {
                final String url = "localhost:3000/users";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                User users = restTemplate.getForObject(url, User.class);

                return users;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.btn_login:
                User usertest= new User(1,"Mariam", "1234");
                if(usertest.getName().equals(name)&& usertest.getPassword().equals(pwd)){
                    Intent intent= new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                else{
                    Intent intent= new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                }


                break;
        }

    }

}
