package com.event.joe.myapplication.com.event.joe.listadapter;

import com.event.joe.myapplication.com.event.joe.Memory;

/**
 * Created by Joe Millership on 16/04/2016.
 */
public class MemoryListDetailProvider {
    private String eventTitle;
    private String eventDate;

    public MemoryListDetailProvider(String eventTitle, String eventDate) {
        this.setEventDate(eventDate);
        this.setEventTitle(eventTitle);
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}