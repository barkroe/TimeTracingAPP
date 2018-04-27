package com.example.mabdullah.timetracingapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class ToShowResponse extends AppCompatActivity {
    private static String task_url = "http://10.0.2.2:3000/api/tasks";
    TextView outputView;
    String data="";
    String urlParameters = "?user=1&project=1&date=2.4.2012&end=14:00&start=15:00&description=to do something";
    JSONArray jsonArray ;
    JSONObject jsonObj=null;
    HashMap<String, String> params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_show_response);

        outputView = (TextView) findViewById(R.id.postOutput);

         data= getIntent().getStringExtra("jsonArray");

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = task_url; // your URL

        try {
             jsonArray = new JSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        queue.start();
        for (int i = 0; i < jsonArray.length(); i++)
        {
            try {
                jsonObj = jsonArray.getJSONObject(i);
                params = new HashMap<String,String>();
                params.put("user", jsonObj.getString("user")); // the entered data as the JSON body.
                params.put("project", jsonObj.getString("project"));
                params.put("start", jsonObj.getString("start"));
                params.put("end", jsonObj.getString("end"));
                params.put("description", jsonObj.getString("description"));
                params.put("date", jsonObj.getString("date"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            System.out.println(jsonObj);
        }







                JsonObjectRequest jsObjRequest = new
                        JsonObjectRequest(Request.Method.POST,
                        url,
                        new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    outputView.setText("Done");
                                    outputView.setText(response.getString("message"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        outputView.setText("That didn't work!");
                    }

                });
                queue.add(jsObjRequest);

    }

}

    /*public  String makePostRequest(String stringUrl, String urlParameters,
                                         Context context) throws IOException {

        URL url = new URL(stringUrl);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        String line;
        StringBuffer jsonString = new StringBuffer();

        uc.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        uc.setRequestMethod("POST");
        uc.setDoInput(true);
        uc.setInstanceFollowRedirects(false);
        //uc.connect();
        //OutputStreamWriter writer = new OutputStreamWriter(uc.getOutputStream(), "UTF-8");
        DataOutputStream wr = new DataOutputStream(uc.getOutputStream ());
        wr.writeBytes(urlParameters);
        wr.close();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while((line = br.readLine()) != null){
                jsonString.append(line);
            }
            br.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            outputView.setText("url Exeption ! ");
        }
        uc.disconnect();
        return jsonString.toString();
    }*/

