package com.event.joe.myapplication.listadapter;

/**
 * Created by Joe Millership on 16/04/2016.
 */
public class MemoryListDetailProvider {
    private String memoryTitle;
    private String memoryLocation;
    private String memoryDate;
    private String memoryImage;

    public MemoryListDetailProvider(String memoryTitle, String memoryDate, String memoryLocation, String memoryImage) {
        this.setMemoryDate(memoryDate);
        this.setMemoryTitle(memoryTitle);
        this.setMemoryLocation(memoryLocation);
        this.setMemoryImage(memoryImage);
    }

    public String getMemoryTitle() {
        return memoryTitle;
    }

    public String getMemoryLocation(){
        return memoryLocation;
    }


    public void setMemoryLocation(String memoryLocation) {
        this.memoryLocation = memoryLocation;
    }

    public String getMemoryImage() {
        return memoryImage;
    }

    public void setMemoryImage(String memoryImage) {this.memoryImage = memoryImage;}

    public void setMemoryTitle(String memoryTitle) {
        this.memoryTitle = memoryTitle;
    }

    public String getMemoryDate() {
        return memoryDate;
    }

    public void setMemoryDate(String memoryDate) {
        this.memoryDate = memoryDate;
    }
}