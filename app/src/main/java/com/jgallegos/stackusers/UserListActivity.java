package com.jgallegos.stackusers;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {

    private final String URL_GET_STACK_USERS = "https://api.stackexchange.com/2.3/users?page=%s&order=desc&sort=reputation&site=stackoverflow";

    private UserAdapter userAdapter;
    private RecyclerView rvUserList;
    private FloatingActionButton fabAddUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup UI
        setContentView(R.layout.activity_userlist);

        // Link controls
        fabAddUser = (FloatingActionButton) findViewById(R.id.fabAddUser);
        rvUserList = (RecyclerView) findViewById(R.id.rvUserList);

        // Set up our custom themed actionbar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.userlist_actionbar);

        // Set our AddUser (+) floating action button onClickListener
        fabAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoAddUser();
            }
        });

        // Configure our RecyclerView
        rvUserList.setHasFixedSize(true);
        rvUserList.setLayoutManager(new LinearLayoutManager(this));

        // Queue the user download
        downloadUserData(String.format(URL_GET_STACK_USERS,"1")); // Get Page 1 of users

        }

        private void downloadUserData(String url) {
            // Create JsonObjectRequest to retrieve the user list from given URL
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                // Data received, now parse the JSON data
                                parseJSON(response);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("[JSON]",error.toString());
                            Toast.makeText(getApplicationContext(),"Error retrieving user list.", Toast.LENGTH_LONG);
                            //TODO: Add a retry button
                        }
                    });
            NetworkSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
        }

        private void parseJSON(JSONObject jsonData) throws JSONException {
            // Create array to hold our UserViewModels
            ArrayList<UserViewModel> userViewModelList = new ArrayList<>();

            // Iterate through each user
            JSONArray userData = jsonData.getJSONArray("items");
            for(int i=0; i < userData.length(); i++) {

                JSONObject jUser = userData.getJSONObject(i);

                // Store user data in local variables
                String displayName = jUser.getString("display_name");
                int reputation = jUser.getInt("reputation");
                int goldBadgeCount = jUser.getJSONObject("badge_counts").getInt("gold");
                int silverBadgeCount = jUser.getJSONObject("badge_counts").getInt("silver");
                int bronzeBadgeCount = jUser.getJSONObject("badge_counts").getInt("bronze");
                String gravatarURL = jUser.getString("profile_image");

                // Create UserViewModel from received data and add to userViewModelList
                UserViewModel uvw = new UserViewModel(displayName, bronzeBadgeCount, silverBadgeCount, goldBadgeCount, reputation, gravatarURL);
                userViewModelList.add(uvw);
            }

            // All users downloaded and stored, create an adapter for the user list and bind it to our RecyclerView
            userAdapter = new UserAdapter(userViewModelList);
            rvUserList.setAdapter(userAdapter);

        }

        private void gotoAddUser() {
            // Goto AddUser Activity for a result of new user data
            Intent intent = new Intent(this, AddUserActivity.class);
            startActivityForResult(intent, 0);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(resultCode == RESULT_OK) {
                // Get data from Add User Activity
                String displayName = data.getStringExtra("KEY_DISPLAYNAME");
                int reputation = data.getIntExtra("KEY_REPUTATION",0);
                int goldBadgeCount = data.getIntExtra("KEY_GBCOUNT",0);
                int silverBadgeCount = data.getIntExtra("KEY_SBCOUNT", 0);
                int bronzeBadgeCount = data.getIntExtra("KEY_BBCOUNT",0);

                // Add user to our list
                userAdapter.addUser(displayName, bronzeBadgeCount, silverBadgeCount, goldBadgeCount, reputation, "");
            }
        }
}