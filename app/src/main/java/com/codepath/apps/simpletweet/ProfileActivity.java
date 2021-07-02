package com.codepath.apps.simpletweet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.codepath.apps.simpletweet.models.User;

import org.parceler.Parcels;

public class ProfileActivity extends AppCompatActivity {
    ImageView ivProfile;
    TextView tvName;
    TextView tvHandle;
    TextView tvFollowing;
    TextView tvFollowers;
    TextView tvDescription;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ivProfile = findViewById(R.id.ivProfileImage);
        tvName = findViewById(R.id.tvName);
        tvHandle = findViewById(R.id.tvHandle);
        tvFollowing = findViewById(R.id.tvFollowing);
        tvFollowers = findViewById(R.id.tvFollowers);
        tvDescription = findViewById(R.id.tvDescription);

        user = (User) Parcels.unwrap(getIntent().getParcelableExtra(User.class.getSimpleName()));


        tvName.setText(user.getName());
        tvHandle.setText("@" + user.getScreenName());
        tvFollowing.setText(String.valueOf(user.getFollowing()) + " following");
        tvFollowers.setText(String.valueOf(user.getFollowers()) + " followers");
        tvDescription.setText(user.getDescription());

        Glide.with(this)
                .load(user.publicImageUrl)
                .transform(new CircleCrop())
                .into(ivProfile);




    }

}