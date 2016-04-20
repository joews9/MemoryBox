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
    private String memoryDate;;
    private String title;
    private String location;
    private String imageResource;

    public Memory(String description){
        this.description = description;
        this.title = description.trim();
    }

    public static String getDescription() {
        return description;
    }

    public static String getMemoryDate() {
        if (memoryDate == null){
            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
            memoryDate = df.format(Calendar.getInstance().getTime());
        }
        return memoryDate;
    }

    public static String getLocation() {
        if(location == null){
            return "Home"
        }
        return location;
    }

    public static String getImageResource() {
        if(imageResource == null){
            imageResource = "none";
        }
        return imageResource;
    }

    public static String getTitle() {
        return title;
    }

    public Memory(String description, String memoryDate, String location, String imageResource, String title){
       this.description = description;
       this.memoryDate = memoryDate;
       this.location = location;
       this.imageResource = imageResource;
        this.title = title;
   }
}
