package com.event.joe.myapplication;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.event.joe.myapplication.com.event.joe.Memory;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */

public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testMemory(){
        Memory memory = new Memory("Test Description");
        assert memory.getDescription() == "Test Description";
    }
}