package com.example.json3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        // queue = getRequestQueue(getApplicationContext());

        final TextView output = (TextView)findViewById(R.id.txtJSON);

        final Button request  = (Button)findViewById(R.id.btnRequest);
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url ="http://data-atgis.opendata.arcgis.com/datasets/c82756c875ff4e9fad0bc7a9f97ef7a8_0.geojson";

                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                StringBuilder localities = new StringBuilder();

                                try {
                                    JSONArray data = response.getJSONArray("features");

                                    for(int index = 0; index < data.length(); index++)
                                    {
                                        JSONObject station  = data.getJSONObject(index);

                                        JSONObject properties = station.getJSONObject("geometry");
                                        localities.append(properties.getString("coordinates")+"\n");
                                    }

                                    System.err.println(response);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                output.setText(localities.toString());

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("error",error.getLocalizedMessage());

                            }
                        });
                // Add the request to the RequestQueue.
                queue.add(jsObjRequest);


            }
        });






    }
}
