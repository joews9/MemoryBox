package com.event.joe.myapplication.com.event.joe.listadapter;

/**
 * Created by Joe Millership on 16/04/2016.
 */
public class MemoryListDetailProvider {
    private String memoryTitle;
    private String memoryLocation;
    private String memoryDate;

    public MemoryListDetailProvider(String memoryTitle, String memoryDate, String memoryLocation) {
        this.setMemoryDate(memoryDate);
        this.setMemoryTitle(memoryTitle);
        this.setMemoryLocation(memoryLocation);
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