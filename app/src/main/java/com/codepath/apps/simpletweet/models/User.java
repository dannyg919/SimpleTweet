package com.codepath.apps.simpletweet.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Parcel
public class User {

    public String name;
    public String screenName;
    public String publicImageUrl;
    public int following;
    public int followers;
    public String description;


    public User() {}    //Needed by Parceler

    public static User fromJson(JSONObject jsonObject) throws JSONException {
        User user = new User();

        user.name = jsonObject.getString("name");
        user.screenName = jsonObject.getString("screen_name");
        user.publicImageUrl = jsonObject.getString("profile_image_url_https");
        user.following = jsonObject.getInt("friends_count");
        user.followers = jsonObject.getInt("followers_count");
        user.description = jsonObject.getString("description");
        return user;

    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getPublicImageUrl() {
        return publicImageUrl;
    }

    public int getFollowing() {
        return following;
    }

    public int getFollowers() {
        return followers;
    }

    public String getDescription() {
        return description;
    }
}
