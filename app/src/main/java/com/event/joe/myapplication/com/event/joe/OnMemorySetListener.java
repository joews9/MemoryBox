package com.event.joe.myapplication.com.event.joe;

/**
 * Created by Joe Millership on 09/05/2016.
 */
public interface OnMemorySetListener {

    public void editMemory(Memory memory);

    public void editMemoryComplete();

    public void setNewName(String name);

    public void viewMemoryDetails(Memory memory);
}
