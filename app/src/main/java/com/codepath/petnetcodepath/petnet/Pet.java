package com.codepath.petnetcodepath.petnet;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Pet")
public class Pet extends ParseObject {

    public static final String KEY_DESCRIPTION = "pdescription";
    public static final String KEY_IMAGE = "pimage";
    public static final String KEY_USER = "user";
    public static final String KEY_TYPE = "ptype";
    public static final String KEY_PREF = "ppref";
    public static final String KEY_NAME = "pname";

    public String getDescription() {
        return  getString(KEY_DESCRIPTION);
    }

    //set method for the description
    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public String getType() {
        return getString(KEY_TYPE);
    }

    public void setType(String type){
        put(KEY_TYPE, type);
    }

    public String getName(){
        return getString(KEY_NAME);
    }

    public void setName(String name){
        put(KEY_NAME, name);
    }

    public String getPref(){
        return getString(KEY_PREF);
    }

    public void setPref(String pref){
        put(KEY_PREF, pref);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    //set image
    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    // get the image
    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

}
