package com.codepath.petnetcodepath.petnet;

import android.app.Application;

import com.codepath.petnetcodepath.petnet.models.Pet;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //ParseObject.registerSubclass(User.class);
        ParseObject.registerSubclass(Pet.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build()
        );
    }
}
