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

import java.util.HashMap;
import java.util.Map;

public class ParseURLPost {
    private RequestQueue mQueue;
    public ParseURLPost(RequestQueue mQueue) {
        this.mQueue = mQueue;
    }
    public void ParseData(String url, ImplementJson implementJson, Map<String,String> params) {
        //String url = "http://localhost:4000/cuahang/dexuatlist";
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url,parameters,
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
