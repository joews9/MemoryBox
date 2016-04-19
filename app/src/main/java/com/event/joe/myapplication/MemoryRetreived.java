package com.event.joe.myapplication;

import com.event.joe.myapplication.com.event.joe.Memory;

/**
 * Created by Joe Millership on 19/04/2016.
 */
public class MemoryRetreived extends Memory {

    private String location;
    private String imageResource;
    private String title;
    private String desccription;
    public MemoryRetreived(String description) {
        super(description);
    }

    public MemoryRetreived(String description, String memoryDate, String location, String imageResource, String title) {
        super(description, memoryDate, location, imageResource, title);
    }
}
