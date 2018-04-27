package com.example.mabdullah.timetracingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private String TAG = MainActivity.class.getSimpleName();

    private static String user_url = "http://10.0.2.2:3000/api/users";
    private static String project_url = "http://10.0.2.2:3000/api/projects";
    private static String task_url = "http://10.0.2.2:3000/api/tasks";


    ArrayList<HashMap<String, String>> userssList;
    String fileName = "userJson";
    //String jsonResponse = getJSONData(this,fileName);

   /* DBManager db= new DBManager(this);*/

    EditText etuserName;
    EditText etpassword;
    Button btnLogin;
    String name="";
    String pwd="";
    String userId="";
    String projectsString="";
    String tasksString="";
    String userString="";
User responseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etuserName=(EditText)findViewById(R.id.et_userName);
        etpassword=(EditText)findViewById(R.id.et_description);
        btnLogin=(Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);


        userssList=new ArrayList<>();




    }
    private class GetUsers extends AsyncTask<Void, Void, Boolean> {
        String userOK="";
        String pwdOk="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Boolean doInBackground(Void... arg0) {
            HttpHandler sh_user = new HttpHandler();
            // Making a request to url and getting response
             userString = sh_user.makeServiceCall(user_url);

            HttpHandler sh_projects = new HttpHandler();
            // Making a request to url and getting response
            projectsString = sh_user.makeServiceCall(project_url);

            HttpHandler sh_tasks = new HttpHandler();
            // Making a request to url and getting response
            tasksString = sh_user.makeServiceCall(task_url);
           /* try {
                saveFile("users", userString);
                saveFile("projects",projectsString);
                saveFile("tasks",tasksString);
            } catch (IOException e) {
                e.printStackTrace();
            }*/


            Log.e(TAG, "Response from url: " + userString);

            if (userString == null) userString =loadData();

            try {

                // Getting JSON Array node
                JSONArray users = new JSONArray(userString);

                // looping through All projects
                for (int i = 0; i < users.length(); i++) {
                    JSONObject p = users.getJSONObject(i);

                    String $loki = p.getString("$loki");
                    String username = p.getString("username");
                    String password = p.getString("password");
                    String id = p.getString("id");

                    if (username.equals(name)&& password.equals(pwd)){
                        userOK=username;
                        pwdOk=password;
                        userId=id;

                    }

                    // tmp hash map for single contact
                    HashMap<String, String> user = new HashMap<>();

                    // adding each child node to HashMap key => value
                    user.put("$loki", $loki);
                    user.put("username", username);
                    user.put("password", password);
                    user.put("id", id);

                    // adding contact to contact list
                    userssList.add(user);

                }
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
saveJson();
           // saveJSONData(MainActivity.this,userString);
            if(userOK!="" && pwdOk!="")
                return true;
            else
                return false;
        }

        @Override
        protected void onPostExecute(Boolean user) {
            super.onPostExecute(user);
            if(user==true) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(MainActivity.this, MainActivity.class);

                startActivity(intent);
            }

        }

    }
    /*public void saveJSONData(Context context, String mJsonResponse) {
        try {
            FileWriter file = new FileWriter(context.getFilesDir().getPath() + "/" + fileName);
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            Log.e("TAG", "Error in Writing: " + e.getLocalizedMessage());
        }
    }
    public String getJSONData(Context context, String  fileName) {
        try {
            File f = new File(context.getFilesDir().getPath() + "/" + fileName);
            //check whether file exists
            FileInputStream is = new FileInputStream(f);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer);
        } catch (IOException e) {
            Log.e("TAG", "Error in Reading: " + e.getLocalizedMessage());
            return null;
        }
    }
*/
/*    public void saveFile(String fileName, String json) throws IOException {
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write((json.getBytes()));
            fos.close();
            Toast.makeText(MainActivity.this,"Error saving file",Toast.LENGTH_SHORT);
        }catch (Exception e){
            e.printStackTrace();


        }
    }

    public String  readFile(String file) {
        String text = "";
        try {
            FileInputStream fis=openFileInput(file);
            int size=fis.available();
            byte[] buffer=new byte[size];
            fis.read(buffer);
            text=new String(buffer);
            } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,"Error reading file",Toast.LENGTH_SHORT);
        }
        return text;
    }*/
  public void  saveJson() {

       SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
       SharedPreferences.Editor editor = sp.edit();
       editor.clear();

       editor.putString("userJson", userString);
       editor.putString("projectsJson", projectsString);
       editor.putString("tasksJson", tasksString);
       editor.apply();
   }
    private String loadData(){
        SharedPreferences sp=getSharedPreferences("sp",MODE_PRIVATE);
        Gson gson=new Gson();
        String jsonstr = sp.getString("userJson",null);
        return jsonstr;
    }


    public void onClick(View v) {
        name      =  etuserName.getText().toString();
        pwd      =  etpassword.getText().toString();
        switch (v.getId()){
            case  R.id.btn_login:
                new GetUsers().execute();

               break;

        }

    }

}
