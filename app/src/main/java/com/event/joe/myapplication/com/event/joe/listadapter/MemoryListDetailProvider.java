package com.event.joe.myapplication.com.event.joe.listadapter;

import com.event.joe.myapplication.com.event.joe.Memory;

/**
 * Created by Joe Millership on 16/04/2016.
 */
public class MemoryListDetailProvider {
    private String memoryTitle;
    private String memoryDate;

    public MemoryListDetailProvider(String memoryTitle, String memoryDate){
        this.setMemoryDate(this.memoryDate);
        this.setMemoryTitle(this.memoryTitle);
        System.out.println("Memory List Detail Provider" + memoryTitle);
    }

    public String getMemoryTitle() {
        return memoryTitle;
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
