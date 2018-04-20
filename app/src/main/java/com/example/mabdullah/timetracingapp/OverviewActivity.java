package com.example.mabdullah.timetracingapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class OverviewActivity extends AppCompatActivity {
    private String TAG = OverviewActivity.class.getSimpleName();
    Button btnBack;
    private ListView lv;

    // URL to get contacts JSON
    private static String url = "http://10.0.2.2:3000/api/projects";

    ArrayList<HashMap<String, String>> projectlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        btnBack=(Button) findViewById(R.id.btn_back);


        projectlist=new ArrayList<>();
        lv = (ListView) findViewById(R.id.list);

        new GetProjects().execute();

    }
    private class GetProjects extends AsyncTask<Void, Void, ArrayList> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected ArrayList doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr == null) jsonStr = loadData();

                try {

                    // Getting JSON Array node
                    JSONArray projects = new JSONArray(jsonStr);

                    // looping through All projects
                    for (int i = 0; i < projects.length(); i++) {
                        JSONObject p = projects.getJSONObject(i);

                        String id = p.getString("$loki");
                        String name = p.getString("name");
                        String description = p.getString("description");



                        // tmp hash map for single contact
                        HashMap<String, String> project = new HashMap<>();

                        // adding each child node to HashMap key => value
                        project.put("$loki", id);
                        project.put("name", name);
                        project.put("description", description);

                        // adding contact to contact list
                        projectlist.add(project);
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

            saveJson(projectlist);
            return projectlist;
        }

        @Override
        protected void onPostExecute(ArrayList result) {
            super.onPostExecute(result);

            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    OverviewActivity.this, projectlist,
                    R.layout.activity_overview, new String[]{"name", "description",
                    "$loki"}, new int[]{R.id.tv_projectName,
                    R.id.tv_projectbeschreibung, R.id.tv_projectId});

            lv.setAdapter(adapter);

            lv.setClickable(true);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3)
                {
                    Object data = lv.getItemAtPosition(position);
                    /*String project_value=(String)lv.getItemAtPosition(position);*/
                    Intent intent= new Intent(OverviewActivity.this, ProjectDetails.class);
                    intent.putExtra("projectSelected",data.toString());
                    startActivity(intent);
                }
            });
        }

    }
    private void  saveJson(ArrayList projectlist){
        SharedPreferences sp=getSharedPreferences("sp",MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.clear();
        Gson gson=new Gson();
        String json= gson.toJson(projectlist);
        editor.putString("projectJson",json);
        editor.apply();


    }
   private String loadData(){
        SharedPreferences sp=getSharedPreferences("sp",MODE_PRIVATE);
        Gson gson=new Gson();
        String jsonstr = sp.getString("projectJson",null);
        return jsonstr;
    }

}
