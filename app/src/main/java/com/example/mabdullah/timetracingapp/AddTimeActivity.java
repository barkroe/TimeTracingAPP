package com.example.mabdullah.timetracingapp;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

import static org.springframework.http.HttpMethod.POST;

public class AddTimeActivity extends AppCompatActivity implements View.OnClickListener {

    private String TAG = AddTimeActivity.class.getSimpleName();

    //TextView outputView;

    private static String user_url = "http://10.0.2.2:3000/api/users";
    private static String project_url = "http://10.0.2.2:3000/api/projects";
    private static String task_url = "http://10.0.2.2:3000/api/tasks";


    String projectsString = "";
    String tasksString = "";
    String userString = "";

    JSONArray jsonarray = new JSONArray();

    EditText et_projectName;
    EditText et_date;
    EditText et_description;
    EditText et_start;
    EditText et_end;
    EditText et_user;
    Button btn_senddata;


    String project = "";
    String date = "";
    String description = "";
    String start = "";
    String end = "";
    String user = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);


      //  outputView = (TextView) findViewById(R.id.postOutput);
        et_projectName = (EditText) findViewById(R.id.et_projectName);
        et_date = (EditText) findViewById(R.id.et_date);
        et_description = (EditText) findViewById(R.id.et_description);
        et_start = (EditText) findViewById(R.id.et_start);
        et_end = (EditText) findViewById(R.id.et_end);
        et_user = (EditText) findViewById(R.id.et_user);
        btn_senddata = (Button) findViewById(R.id.btn_senddata);

        btn_senddata.setOnClickListener(this);

    }

    private class PostJsonData extends AsyncTask<Void, Void, String> {
        String userOK = "";
        String pwdOk = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override

        protected String doInBackground(Void... arg0) {
            HttpHandler sh_user = new HttpHandler();
            // Making a request to url and getting response
            userString = sh_user.makeServiceCall(user_url);

            HttpHandler sh_projects = new HttpHandler();
            // Making a request to url and getting response
            projectsString = sh_user.makeServiceCall(project_url);

            HttpHandler sh_tasks = new HttpHandler();
            // Making a request to url and getting response
            tasksString = sh_user.makeServiceCall(task_url);

            Log.e(TAG, "Response from url: " + userString);

            try {
                // Getting JSON Array node
                JSONArray users = new JSONArray(userString);
                JSONArray projects = new JSONArray(projectsString);

                JSONObject postData = new JSONObject();

                // looping through All Users
                for (int i = 0; i < users.length(); i++) {
                    JSONObject u = users.getJSONObject(i);
                    if (u.getString("username").equals(user)) {
                        String $loki = u.getString("$loki");
                        postData.put("id", $loki);
                        postData.put("user", $loki);
                    }
                    break;
                }

                for (int i = 0; i < projects.length(); i++) {
                    JSONObject p = projects.getJSONObject(i);
                    String name = p.getString("name");
                    if (p.getString("name").equals(project)) {
                        String $loki = p.getString("$loki");
                        postData.put("project", $loki);
                    }
                    break;
                }


                postData.put("date", date);
                postData.put("description", description);
                postData.put("start", start);
                postData.put("end", end);


                jsonarray.put(postData);


            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return jsonarray.toString();

        }

        @Override
        protected void onPostExecute(String postdata) {
            super.onPostExecute(postdata);
            Intent intent = new Intent(AddTimeActivity.this, ToShowResponse.class);
            intent.putExtra("jsonArray",postdata);
            startActivity(intent);

        }

    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_senddata:
                project = et_projectName.getText().toString();
                date = et_date.getText().toString();
                description = et_description.getText().toString();
                start = et_start.getText().toString();
                end = et_end.getText().toString();
                user = et_user.getText().toString();
                new PostJsonData().execute();

                break;
        }

    }

}




