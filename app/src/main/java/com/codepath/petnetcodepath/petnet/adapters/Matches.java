package com.codepath.petnetcodepath.petnet.adapters;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Matches")
public class Matches extends ParseObject{

    public static final String KEY_TEXT = "text";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_KEY = "createdAt";

    public String getText() {return getString(KEY_TEXT);}

    public void setText(String text) {put(KEY_TEXT, text);}

    public ParseFile getImage() {return getParseFile(KEY_IMAGE);};

    public void setImage(ParseFile parseFile) {put(KEY_IMAGE, parseFile);}

    public ParseUser getUser() {return getParseUser(KEY_USER);}

    public void setUser(ParseUser user) {put(KEY_USER, user);}
}
