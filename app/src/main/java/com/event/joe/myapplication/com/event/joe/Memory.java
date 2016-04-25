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

    public Memory(String description){
        this.description = description;
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
        return category;
    }

    public Memory(String description, String memoryDate, String location, String imageResource, String title, String category){
       this.description = description;
        this.category = category;

       this.memoryDate = memoryDate;
       this.location = location;
       this.imageResource = imageResource;
        this.title = title;
   }
}
