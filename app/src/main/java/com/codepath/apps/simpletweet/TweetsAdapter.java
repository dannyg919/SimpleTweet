package com.codepath.apps.simpletweet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.simpletweet.models.Tweet;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {
    Context context;
    List<Tweet> tweets;
    //Pass in context and list of tweets


    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    //For each row, inflate the layout
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder( @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet,parent,false);

        return new ViewHolder(view);
    }

    //Bind values based on position of the element
    @Override
    public void onBindViewHolder( @NotNull ViewHolder holder, int position) {
        //Get the data at position
        Tweet tweet = tweets.get(position);
        //Bind the tweet with the ViewHolder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }



    //Define a ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfileImage;
        TextView tvScreenName;
        TextView tvBody;
        TextView tvCreatedAt;

        public ViewHolder( @NotNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            tvScreenName = itemView.findViewById(R.id.tvScreenName);
            tvBody = itemView.findViewById(R.id.tvBody);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
        }

        public void bind(Tweet tweet) {
            //Bind body and screenName
            tvBody.setText(tweet.body);
            tvScreenName.setText(tweet.user.screenName);



            //Bind time created (after formatting)


            tvCreatedAt.setText(tweet.createdAt);



            //Bind Profile pic
            Glide.with(context)
                    .load(tweet.user.publicImageUrl)
                    .into(ivProfileImage);
        }

    }


}
