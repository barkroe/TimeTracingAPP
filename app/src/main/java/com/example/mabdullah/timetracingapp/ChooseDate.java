package com.example.mabdullah.timetracingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

public class ChooseDate extends AppCompatActivity implements View.OnClickListener {

    DatePicker simpleDatePicker;
    Button submit;
    Button btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);

        btnback=(Button) findViewById(R.id.btn_back);
        btnback.setOnClickListener(this);

        // initiate the date picker and a button
        simpleDatePicker = (DatePicker) findViewById(R.id.choosedate);
        submit = (Button) findViewById(R.id.btn_showEntity);
        // perform click event on submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get the values for day of month , month and year from a date picker
                String day = "Day = " + simpleDatePicker.getDayOfMonth();
                String month = "Month = " + (simpleDatePicker.getMonth() + 1);
                String year = "Year = " + simpleDatePicker.getYear();
                // display the values by using a toast
                Toast.makeText(getApplicationContext(), day + "\n" + month + "\n" + year, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
//                hier soll die Data gespeichert werden

                break;
        }

        switch (v.getId()) {
            case R.id.btn_back:
                Intent intent= new Intent(ChooseDate.this, HomeActivity.class);
                startActivity(intent);

                break;
        }
    }
}
