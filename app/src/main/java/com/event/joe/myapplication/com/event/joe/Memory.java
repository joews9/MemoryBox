package com.event.joe.myapplication.com.event.joe;

import android.location.Location;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Joe Millership on 17/04/2016.
 */
public class Memory {
    private String description;
    private String memoryDate;
    private String title;
    private String location;
    private String category;
    private String imageResource;
    private String id;

    public String getUserID() {
        return userID;
    }

    private String smallImageResource;
    private String userID;

    public Memory(String description, String userID){
        this.description = description;
        this.userID = userID;
        this.title = description.trim();
    }

    public String getDescription() {
        return description;
    }

    public String getMemoryDate() {
        if (memoryDate == null){
            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
            memoryDate = df.format(Calendar.getInstance().getTime());
        }
        return memoryDate;
    }

    public String getLocation() {
        if(location == null){
            location = "No Location Set";
        }
        return location;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public String getImageResource() {
        if(imageResource == null){
            imageResource = "none";
        }
        return imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        //if the category is null set it as a quick memory
        if(category == null){
            category = "Quick Memory";
        }
        return category;
    }

    public String getSmallImageResource() {
        if(smallImageResource == null){
            smallImageResource = "none";
        }
        return smallImageResource;
    }

    public Memory(String description, String memoryDate, String location, String imageResource, String title, String category, String smallImageResource, String userID){
        this.description = description;
        this.memoryDate = memoryDate;
        this.location = location;
        this.imageResource = imageResource;
        this.category = category;
        this.smallImageResource = smallImageResource;
        this.title = title;
        this.userID = userID;
   }
}
