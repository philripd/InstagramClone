package com.codepath.apps.instagramclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Register your parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(BuildConfig.APPLICATION_ID)
                .clientKey(BuildConfig.CLIENT_KEY)
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
