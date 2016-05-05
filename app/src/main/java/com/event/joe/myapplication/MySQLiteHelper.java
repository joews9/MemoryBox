package com.event.joe.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.event.joe.myapplication.com.event.joe.Memory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Joe Millership on 27/03/2016.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "memoriesDB";
    private static final String TABLE_NAME = "table_memory";
    private static final String KEY_ID = "id";
    private static final String DATE = "date";
    private static final String TITLE = "title";
    private static final String LOCATION = "location";
    private static final String IMAGE_URL = "imageURL";
    private static final String DESCRIPTION = "description";

    private SQLiteDatabase db;

    //Login Table
    private static final String PASSWORD = "password";
    private static final String CATEGORY = "category";
    private static final String USERNAME = "username";
    private static final String FIRSTNAME = "firstName";
    private static final String LASTNAME = "lastName";
    private static final String SMALLIMAGE = "smallImageURL";
    private static final String TABLE_LOGIN = "table_login";
    private static final String ACTIVITY = "activity";
    private static final String ID = "id";
    private static final String USER_ID = "userID";

    private static final String CREATE_LOGIN_TABLE = "CREATE TABLE table_login ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "username TEXT," + "firstName TEXT, " + "lastName TEXT," + "activity TEXT," + "password TEXT )";
    private static final String CREATE_MEMORY_TABLE = "CREATE TABLE table_memory ( " + "id INTEGER PRIMARY KEY AUTOINCREMENT, " + "date TEXT," + "title TEXT," + "location TEXT," + "imageURL TEXT," + "description TEXT," + "userID TEXT," + "category TEXT, smallImageURL TEXT )";


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

    public void deactivateSessions() {
        String deleteQuery = "UPDATE " + TABLE_LOGIN + " SET activity = 'inactive'";
        db = this.getWritableDatabase();
        db.execSQL(deleteQuery);

    }

    public void setActive(String username) {
        String deleteQuery = "UPDATE " + TABLE_LOGIN + " SET activity = 'active' WHERE username = '" + username + "'";
        db = this.getWritableDatabase();
        db.execSQL(deleteQuery);

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

    public String addUser(String username, String password, String firstName, String lastName) {
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

    public String getPassword(String currentUsername) {
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
    public String getUserID(String currentUsername) {
        String userID = null;
        System.out.println("SQUSERNAME*****" + currentUsername);
        int x = 1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM " + TABLE_LOGIN + " WHERE username = '" + currentUsername + "'", null);

        if (cursor.moveToFirst()) {
            while (cursor.isAfterLast() == false) {
                userID = cursor.getString(cursor.getColumnIndex(ID));
                x = x + 1;
                cursor.moveToNext();
            }
        }

        return userID;
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
    public void saveMemory(Memory memory) {
        String date = memory.getMemoryDate().toString();
        String description = memory.getDescription();
        String location = memory.getLocation();
        String title = memory.getTitle();
        String smallImage = memory.getSmallImageResource();
        String imageURL = memory.getImageResource();
        String userID = memory.getUserID();

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DATE, date);
        values.put(TITLE, title);
        values.put(DESCRIPTION, description);
        values.put(SMALLIMAGE, smallImage);
        values.put(LOCATION, location);
        values.put(IMAGE_URL, imageURL);
        values.put(USER_ID, userID);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteMemory(String memoryID) {
        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE id= " + memoryID;
        db = this.getWritableDatabase();
        db.execSQL(deleteQuery);


    }
    public List<Memory> getAllMemories(String userID) {
        List<Memory> memories = new ArrayList<Memory>();
        int x = 1;
        int i = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, date, title, imageURL, location, category, smallImageURL, description, userID  FROM " + TABLE_NAME + " WHERE userID = " + userID + "  ORDER BY date DESC", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                String memoryTitle = cursor.getString(cursor.getColumnIndex(TITLE));
                String memoryDate = cursor.getString(cursor.getColumnIndex(DATE));
                String memoryLocation = cursor.getString(cursor.getColumnIndex(LOCATION));
                String memoryImage = cursor.getString(cursor.getColumnIndex(IMAGE_URL));
                String memoryId = cursor.getString(cursor.getColumnIndex(ID));
                String memoryCategory = cursor.getString(cursor.getColumnIndex(CATEGORY));
                String memorySmallImage = cursor.getString(cursor.getColumnIndex(SMALLIMAGE));
                String memoryDescription = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                String memoryUserID = cursor.getString(cursor.getColumnIndex(USER_ID));

                Memory memory = new Memory(memoryDescription, memoryDate, memoryLocation, memoryImage, memoryTitle, memoryCategory, memorySmallImage, memoryUserID);
                memory.setId(memoryId);
                memories.add(memory);
                i++;
                x = x + 1;
                cursor.moveToNext();

            }
        }
        return memories;
    }
}

