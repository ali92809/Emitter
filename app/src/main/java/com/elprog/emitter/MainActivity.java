package com.elprog.emitter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.elprog.emitter.Controller.AppController;
import com.elprog.emitter.Controller.MyReceiver;
import com.elprog.emitter.Controller.UserAdapter;
import com.elprog.emitter.Model.Address;
import com.elprog.emitter.Model.Users;
import com.elprog.emitter.Model.company;
import com.elprog.emitter.Model.geo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.loopj.android.http.*;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = MainActivity.class.getName();
    private JsonArrayRequest userreq;

    private String url = "https://jsonplaceholder.typicode.com/users";
    private RecyclerView recyclerView;
    private UserAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<Users> usersArrayList;
	MyReceiver myReceiver;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter("com.elprog.Middelman_INTENT");
        registerReceiver(myReceiver, filter);
        builder = new AlertDialog.Builder(this);

        String struser=getIntent().getStringExtra("response");
        if(struser!=null){


            builder.setMessage("Response : "+struser);
            AlertDialog alert = builder.create();

            alert.setTitle("Response Data");
            alert.show();


        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);//every item of the RecyclerView has a fix size
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        usersArrayList=new ArrayList<>();

        // SwipeRefreshLayout
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);

        sendAndRequestResponse();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                usersArrayList.clear();
                adapter.notifyDataSetChanged();

                sendAndRequestResponse();
                // To keep animation for 4 seconds
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {
                        // Stop animation (This will be after 3 seconds)
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 4000); // Delay in millis
            }
        });

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);


    }

    private void sendAndRequestResponse() {
        mSwipeRefreshLayout.setRefreshing(true);
        //

        // Creating volley request obj
         userreq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {



                        // hidePDialog();

                        try {

                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                               /* JSONObject obj = response.getJSONObject(i);
                                JSONObject address=new JSONObject(obj.getString("address"));
                                JSONObject geo=new JSONObject(address.getString("geo"));
                                JSONObject company=new JSONObject(obj.getString("company"));
                              Users muser=  new Users(obj.getInt("id"),obj.getString("name"),
                                        obj.getString("username"),obj.getString("email"),
                                        obj.get("phone").toString(),obj.getString("website"),
                                        new Address(address.getString("street"),address.getString("suite"),
                                                address.getString("city"),address.get("zipcode").toString(),
                                                new geo(geo.getString("lat"),geo.getString("lng"))),
                                        new company(company.getString("name"),company.getString("catchPhrase"),company.getString("bs"))

                                        );*/
                                Gson gsonuser=new Gson();

                                Users muser=gsonuser.fromJson(response.get(i).toString(),Users.class);
                                 usersArrayList.add(muser);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                    }
                            adapter = new UserAdapter(MainActivity.this,usersArrayList);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                mSwipeRefreshLayout.setRefreshing(false);

            }

        });


// Adding request to request queue
        AppController.getInstance().addToRequestQueue(userreq);
    }


}