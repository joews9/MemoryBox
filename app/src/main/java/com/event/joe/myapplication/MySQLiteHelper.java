package com.event.joe.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.event.joe.myapplication.com.event.joe.Memory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Joe Millership on 27/03/2016.
 */
public class MySQLiteHelper  extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "memoriesDB";
    private static final String TABLE_NAME = "table_memory";
    private static final String KEY_ID = "id";
    private static final String DATE = "date";
    private static final String TITLE = "title";
    private static final String LOCATION = "location";
    private static final String IMAGE_URL = "imageURL";
    private static final String DESCRIPTION = "description";

    //  private static final String[] COLUMNS = {KEY_ID,KEY_PC1,KEY_PC2};

    private SQLiteDatabase db;
    private String idChosenEvent;
    private String date;
    private String title;
    private String description;
    private String location;
    private String imageURL;
    private JSONObject event;
    private String id;

    //Login Table
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private static final String PASSWORD = "password";
    private static final String USERNAME = "username";
    private static final String FIRSTNAME = "firstName";
    private static final String LASTNAME = "lastName";
    private static final String TABLE_LOGIN = "table_login";
    private static final String ACTIVITY = "activity";
    private static final String ID = "id";

    private static final String CREATE_LOGIN_TABLE = "CREATE TABLE table_login ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "username TEXT," + "firstName TEXT, " + "lastName TEXT," + "activity TEXT," + "password TEXT )";
    private static final String CREATE_MEMORY_TABLE = "CREATE TABLE table_memory ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "date TEXT," + "title TEXT," + "location TEXT," + "imageURL TEXT," + "description TEXT," + "username TEXT )";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LOGIN_TABLE);
        db.execSQL(CREATE_MEMORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXSISTS table_memory");
        db.execSQL("DROP TABLE IF EXSISTS table_login");
        onCreate(db);
    }

    public String addUser(String username, String password, String firstName, String lastName) {

        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, username);
        values.put(PASSWORD, password);
        values.put(ACTIVITY, "Active");
        values.put(FIRSTNAME, firstName);
        values.put(LASTNAME, lastName);
        db.insert(TABLE_LOGIN, null, values);
        db.close();
        return "Account Added";
    }

    public String getPassword(String currentUsername){
        String currentPassword = null;

        int x = 1;
        List<String> List = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT password FROM " + TABLE_LOGIN + " WHERE username = '" + currentUsername + "'", null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                currentPassword = cursor.getString(cursor.getColumnIndex(PASSWORD));
                x = x + 1;
                cursor.moveToNext();
            }
        }

        return currentPassword;
    }

    public List<String> getAllUsernames() {
        int x = 1;
        List<String> List = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT username FROM " + TABLE_LOGIN, null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                String usernameTemp = cursor.getString(cursor.getColumnIndex(USERNAME));
                List.add(usernameTemp);
                x = x + 1;
                cursor.moveToNext();
            }
        }
        return List;
    }


    public void deactivateSessions() {
        String deleteQuery ="UPDATE " + TABLE_LOGIN + " SET activity = 'inactive'";
        db = this.getWritableDatabase();
        db.execSQL(deleteQuery);

    }

    public void setActive(String username) {
        String deleteQuery = "UPDATE " + TABLE_LOGIN + " SET activity = 'active' WHERE username = '" + username +  "'";
        db = this.getWritableDatabase();
        db.execSQL(deleteQuery);

    }

    public void saveMemory(Memory memory) {
            date = memory.getMemoryDate().toString();
            description = memory.getDescription();
            location = memory.getLocation();
            title = memory.getTitle();
            imageURL = memory.getImageResource();

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DATE, date);
        values.put(TITLE, title);
        values.put(LOCATION, location);
        values.put(IMAGE_URL, imageURL);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteEvent(String eventID) {
        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE id= " + eventID;
        db = this.getWritableDatabase();
        db.execSQL(deleteQuery);

    }

    public List<String> getAllEvents() {

        int x = 1;
        List<String> List = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT title, date FROM " + TABLE_NAME + " ORDER BY id", null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String title = cursor.getString(cursor.getColumnIndex(TITLE));

                String titleListAdd = title;

                List.add(titleListAdd);
                x = x + 1;
                cursor.moveToNext();
            }
        }
        return List;
    }

    public List<String> getAllEventDates() {

        int x = 1;
        List<String> List = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT title, date FROM " + TABLE_NAME + " ORDER BY id", null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                String date = cursor.getString(cursor.getColumnIndex(DATE));

                String dateListAdd = date;

                List.add(dateListAdd);
                x = x + 1;
                cursor.moveToNext();
            }
        }
        return List;
    }

    public List<String> getAllEventIDs() {
        int x = 1;
        List<String> list = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM " + TABLE_NAME + " ORDER BY id", null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String date = cursor.getString(cursor.getColumnIndex(ID));

                String idListAdd = date;

                list.add(idListAdd);
                x = x + 1;
                cursor.moveToNext();
            }
        }
        return list;
    }

    public ArrayList<HashMap<String, String>> getAllMemories() {

        ArrayList<HashMap<String, String>> memories = new ArrayList<>();
        this.idChosenEvent = idChosenEvent;
        int x = 1;
        int i = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT date, title, imageURL, location  FROM " + TABLE_NAME, null);
        HashMap<String, String> chosenMemory = new HashMap<String, String>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String memoryTitle = cursor.getString(cursor.getColumnIndex(TITLE));
                String memoryDate = cursor.getString(cursor.getColumnIndex(DATE));
                String memoryLocation = cursor.getString(cursor.getColumnIndex(LOCATION));
                String memoryImage = cursor.getString(cursor.getColumnIndex(IMAGE_URL));
                chosenMemory.put("title", memoryTitle);
                chosenMemory.put("date", memoryDate);
                chosenMemory.put("location", memoryLocation);
                chosenMemory.put("imageURL", memoryImage);


                memories.add(i, chosenMemory);
                chosenMemory.clear();
                HashMap<String, String > testMap = memories.get(i);
                //TODO: Sort out problem with the array list
                System.out.println("SQLITE*******" + testMap.get("title"));
                i++;
                x = x + 1;
                cursor.moveToNext();
            }
        }

        return memories;
    }

    public HashMap<String, String> getUserDetails(String username) {
        HashMap<String, String> loggedInUserDetails = new HashMap<String, String>();
        int x = 1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + "firstName, lastName FROM " + TABLE_LOGIN + " WHERE username = '" + username + "'", null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                String firstName = cursor.getString(cursor.getColumnIndex(FIRSTNAME));
                String lastName = cursor.getString(cursor.getColumnIndex(LASTNAME));

                loggedInUserDetails.put("firstName", firstName);
                loggedInUserDetails.put("lastName", lastName);


                x = x + 1;
                cursor.moveToNext();
            }
        }

        return loggedInUserDetails;
    }
}

