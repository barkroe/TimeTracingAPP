package com.example.mabdullah.timetracingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProjectDetails extends AppCompatActivity {

    String loki_id="";
    String projectName="";
    String userString="";

    private String TAG = ProjectDetails.class.getSimpleName();
    private ListView lv;
    private static String url = "http://10.0.2.2:3000/api/tasks";
    private static String user_url = "http://10.0.2.2:3000/api/users";

    ArrayList<HashMap<String, String>> projectDetailsList;
    ArrayList<HashMap<String, String>> projectDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        String data= getIntent().getStringExtra("projectSelected");
        String[] dataSplit=data.split(",");
        String[] toGeName= dataSplit[0].split("=");
        projectName=toGeName[1];
        String[] toGetLoki= dataSplit[1].split("=");
         loki_id=toGetLoki[1];



        projectDetailsList=new ArrayList<>();
        projectDetails=new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);


        new ProjectDetails.GetProjectDetails().execute();

    }

    private class GetProjectDetails extends AsyncTask<Void, Void, ArrayList> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected ArrayList doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            userString = sh.makeServiceCall(user_url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr == null) jsonStr = loadData();

            try {

                // Getting JSON Array node
                JSONArray projectDetail = new JSONArray(jsonStr);
                JSONArray users = new JSONArray(userString);


                // looping through All projects
                for (int i = 0; i < projectDetail.length(); i++) {
                    JSONObject p = projectDetail.getJSONObject(i);

                    String project_id = p.getString("project");
                    String date = p.getString("date");
                    String description = p.getString("description");
                    String start = p.getString("start");
                    String end = p.getString("end");
                    String userId= p.getString("user");
                    String id=p.getString("id");// get ID for database





                    // tmp hash map for single contact
                    HashMap<String, String> project = new HashMap<>();

                    // adding each child node to HashMap key => value
                    project.put("project", projectName);
                    project.put("date", date);
                    project.put("description", description);
                    project.put("start", start);
                    project.put("end", end);


                    for (int j = 0; j < users.length(); j++) {
                        JSONObject u = users.getJSONObject(j);
                        if(u.getString("$loki").equals(userId)){
                            String  username= u.getString("username");
                            project.put("username",username);
                        }
                    }



                    projectDetailsList.add(project);
                    if(project_id.equals(loki_id)){
                        projectDetails.add(project);
                    }
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

            saveJson(projectDetailsList);
            return projectDetails;
        }

        @Override
        protected void onPostExecute(ArrayList result) {
            super.onPostExecute(result);

            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    ProjectDetails.this, projectDetails,
                    R.layout.activity_project_details, new String[]{"project", "date",
                    "description","start","end","username"}, new int[]{R.id.tv_project,R.id.tv_date,
                    R.id.tv_description, R.id.tv_start, R.id.tv_end, R.id.tv_user});

            lv.setAdapter(adapter);

            lv.setClickable(true);


        }

    }
    private void  saveJson(ArrayList projectDetail){
        SharedPreferences sp=getSharedPreferences("sp",MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.clear();
        Gson gson=new Gson();
        String json= gson.toJson(projectDetail);
        editor.putString("projectdetailsJson",json);
        editor.apply();


    }
    private String loadData(){
        SharedPreferences sp=getSharedPreferences("sp",MODE_PRIVATE);
        Gson gson=new Gson();
        String jsonstr = sp.getString("projectdetailsJson",null);
        return jsonstr;
    }
}
