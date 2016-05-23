package com.event.joe.myapplication.com.event.joe;

import junit.framework.TestCase;


/**
 * Created by Joe Millership on 23/05/2016.
 */
public class MemoryTest extends TestCase {

    public void testSetMemoryFromDescription() {
        Memory memory = new Memory("Test Description", "1");
        assertEquals("Check Description", "Test Description", memory.getDescription());
    }

    public void testLocation(){
        Memory memory = new Memory("Description", "Date", "location", "image resource", "title", "category", "smallImage", "userID");
        assertEquals("location", memory.getLocation());
    }

    public void testDate(){
        Memory memory = new Memory("Description", "Date", "location", "image resource", "title", "category", "smallImage", "userID");
        assertEquals("Date", memory.getMemoryDate());
    }

    public void testImageResource(){
        Memory memory = new Memory("Description", "Date", "location", "image resource", "title", "category", "smallImage", "userID");
        assertEquals("image resource", memory.getImageResource());
    }
    public void testTitle(){
        Memory memory = new Memory("Description", "Date", "location", "image resource", "title", "category", "smallImage", "userID");
        assertEquals("title", memory.getTitle());
    }

    public void testCatgory(){
        Memory memory = new Memory("Description", "Date", "location", "image resource", "title", "category", "smallImage", "userID");
        assertEquals("category", memory.getCategory());
    }

    public void testUserID(){
        Memory memory = new Memory("Description", "Date", "location", "image resource", "title", "category", "smallImage", "userID");
        assertEquals("userID", memory.getUserID());
    }

}