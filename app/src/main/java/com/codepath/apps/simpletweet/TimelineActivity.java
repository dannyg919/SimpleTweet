package com.codepath.apps.simpletweet;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.simpletweet.models.Tweet;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class TimelineActivity extends AppCompatActivity {

    public static final String TAG = "TimelineActivity";
    private final int REQUEST_CODE = 20;

    TwitterClient client;
    RecyclerView rvTweets;
    List<Tweet> tweets;
    TweetsAdapter adapter;

    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApp.getRestClient(this);

        //ActionBar formatting



        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        //Find recycler view
        rvTweets = findViewById(R.id.rvTweets);

        //Init the list of tweets and adapter
        tweets = new ArrayList<>();
        adapter = new TweetsAdapter(this,tweets);

        //Recycler view setup: layout manager and the adapter
        rvTweets.setLayoutManager(new LinearLayoutManager(this));
        rvTweets.setAdapter(adapter);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        populateHomeTimeline();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if present.
        getMenuInflater().inflate(R.menu.menu_main,menu);


        return true;
    }

    // Menu items logic
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.compose){
            //Compose icon has been tapped

            //Navigate to compose activity
            Intent i = new Intent(this, ComposeActivity.class);
            startActivityForResult(i,REQUEST_CODE);
            return true;
        }

        if(item.getItemId() == R.id.profile){
            //Intent i = new Intent(this, ProfileActivity.class);
            //startActivity(i);
            //return true;
        }

        if(item.getItemId() == R.id.logout){

            client.clearAccessToken();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Receiving data from composed tweet
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            //Get data from intent (tweet obj)
            Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));
            //Update recycler with new tweet
            //Modify data source
            tweets.add(0,tweet);
            //Update the adapter
            adapter.notifyItemInserted(0);
            rvTweets.smoothScrollToPosition(0);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void populateHomeTimeline() {
        //Populates Tweets onto the timeline
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.i(TAG,"onSuccess" + json.toString());
                JSONArray jsonArray = json.jsonArray;
                try {
                    tweets.addAll(Tweet.fromJSONArray(jsonArray));
                    adapter.notifyDataSetChanged();
                } catch(JSONException e) {
                    Log.e(TAG, "Json exception",e);
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG,"onFailure" +response,throwable);
            }
        });
    }

    public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                adapter.clear();
                JSONArray jsonArray = json.jsonArray;
                try {
                    adapter.addAll(Tweet.fromJSONArray(jsonArray));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                swipeContainer.setRefreshing(false);

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.e(TAG,"onFailure" +response,throwable);
            }

        });


    }





}