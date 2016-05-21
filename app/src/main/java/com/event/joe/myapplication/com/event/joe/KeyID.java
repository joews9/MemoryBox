package com.event.joe.myapplication.com.event.joe;

import com.scottyab.aescrypt.AESCrypt;

/**
 * Created by Joe Millership on 18/05/2016.
 */
public enum KeyID {
    DATE("date"),
    DESCRIPTION("description"),
    LOCATION("location"),
    TITLE("title"),
    SMALL_IMAGE("smallImage"),
    LARGE_IMAGE("imageURL"),
    PASSWORD("password"),
    CATEGORY("category");

    private final String text;

    KeyID(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
