package com.example.project531.API;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.project531.Interface.ImplementJson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseURL {
    private RequestQueue mQueue;

    public ParseURL(RequestQueue mQueue) {
        this.mQueue = mQueue;
    }

    public void ParseData(String url, ImplementJson implementJson) {

        //String url = "http://localhost:4000/cuahang/dexuatlist";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray = response.getJSONArray("recordset");

                            implementJson.Done(jsonArray);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Log.e("", String.valueOf(error));
            }
        });
        mQueue.add(request);
    }

}
