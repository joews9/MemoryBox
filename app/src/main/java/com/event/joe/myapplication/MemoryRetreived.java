package com.event.joe.myapplication;

import com.event.joe.myapplication.com.event.joe.Memory;

/**
 * Created by Joe Millership on 19/04/2016.
 */
public class MemoryRetreived extends Memory
{
    public MemoryRetreived(String description) {
        super(description);
    }

    public MemoryRetreived(String description, String memoryDate, String location, String imageResource, String title) {
        super(description, memoryDate, location, imageResource, title);
    }
}
