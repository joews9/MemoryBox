package com.event.joe.myapplication.com.event.joe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.event.joe.myapplication.com.event.joe.Memory;
import com.scottyab.aescrypt.AESCrypt;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.GeneralSecurityException;
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

    private String memoryTitle = null;
    private String memoryDate = null ;
    private String memoryLocation = null;
    private String memoryImage = null;
    private String memoryId = null;
    private String memoryCategory = null;
    private String memorySmallImage = null;
    private String memoryDescription = null;
    private String memoryUserID = null;
    private String firstName = null;
    private String lastName = null;
    private String password = null;

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
    public HashMap<String, String> getUserDetails(String username) {
        HashMap<String, String> loggedInUserDetails = new HashMap<String, String>();
        int x = 1;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + "firstName, lastName, id FROM " + TABLE_LOGIN + " WHERE username = '" + username + "'", null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                String firstName = cursor.getString(cursor.getColumnIndex(FIRSTNAME));
                String lastName = cursor.getString(cursor.getColumnIndex(LASTNAME));
                String userID = cursor.getString(cursor.getColumnIndex(ID));

                loggedInUserDetails.put("firstName", firstName);
                loggedInUserDetails.put("lastName", lastName);
                loggedInUserDetails.put("userID", userID);

                x = x + 1;
                cursor.moveToNext();
            }
        }
        return loggedInUserDetails;
    }

    public String addUser(String username, String password, String firstName, String lastName) {
        db = this.getWritableDatabase();
        String passwordEncrypt = "password";
        String beforeEncryptedPassword = password;
        String encryptedPassword = "";
        try {
            encryptedPassword = AESCrypt.encrypt(passwordEncrypt, beforeEncryptedPassword);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        ContentValues values = new ContentValues();
        values.put(USERNAME, username);
        values.put(PASSWORD, encryptedPassword);
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
        String date = null;
        String description = null;
        String location = null;
        String title = null;
        String smallImage = null;
        String imageURL = null;
        String userID = null;
        String category = null;
        try {
            date = AESCrypt.encrypt(KeyID.DATE.toString(), memory.getMemoryDate());
            description = memory.getDescription();
            location = AESCrypt.encrypt(KeyID.LOCATION.toString(), memory.getDescription());
            title = AESCrypt.encrypt(KeyID.TITLE.toString(), memory.getTitle());
            smallImage = AESCrypt.encrypt(KeyID.SMALL_IMAGE.toString(), memory.getSmallImageResource());
            imageURL = AESCrypt.encrypt(KeyID.LARGE_IMAGE.toString(), memory.getImageResource());
            userID = memory.getUserID();
            category =  memory.getCategory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DATE, date);
        values.put(TITLE, title);
        values.put(DESCRIPTION, description);
        values.put(SMALLIMAGE, smallImage);
        values.put(LOCATION, location);
        values.put(IMAGE_URL, imageURL);
        values.put(USER_ID, userID);
        values.put(CATEGORY, category);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteMemory(String memoryID) {
        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE id= " + memoryID;
        db = this.getWritableDatabase();
        db.execSQL(deleteQuery);
    }

    public void deleteAccount(String userID) {
        String deleteQuery = "DELETE FROM " + TABLE_LOGIN + " WHERE id = " + userID;
        String deleteMemoriesQuery = "DELETE FROM " + TABLE_NAME + " WHERE userID = " + userID;
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
                try {
                    memoryTitle = AESCrypt.decrypt(KeyID.TITLE.toString(), cursor.getString(cursor.getColumnIndex(TITLE)));
                    memoryDate = AESCrypt.decrypt(KeyID.DATE.toString(), cursor.getString(cursor.getColumnIndex(DATE)));
                    memoryLocation = AESCrypt.decrypt(KeyID.LOCATION.toString(), cursor.getString(cursor.getColumnIndex(LOCATION)));
                    memoryImage = AESCrypt.decrypt(KeyID.LARGE_IMAGE.toString(), cursor.getString(cursor.getColumnIndex(IMAGE_URL)));
                    memoryId = cursor.getString(cursor.getColumnIndex(ID));
                    memoryCategory =  cursor.getString(cursor.getColumnIndex(CATEGORY));
                    memorySmallImage = AESCrypt.decrypt(KeyID.SMALL_IMAGE.toString(), cursor.getString(cursor.getColumnIndex(SMALLIMAGE)));
                    memoryDescription = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                    memoryUserID = cursor.getString(cursor.getColumnIndex(USER_ID));
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                }

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
    public List<Memory> getAllMemoriesSearch(String userID, String description) {
        List<Memory> memories = new ArrayList<Memory>();
        int x = 1;
        int i = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, date, title, imageURL, location, category, smallImageURL, description, userID  FROM " + TABLE_NAME + " WHERE userID = " + userID + " AND description LIKE '%" + description + "%' ORDER BY date ASC", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                try {
                    memoryTitle = AESCrypt.decrypt(KeyID.TITLE.toString(), cursor.getString(cursor.getColumnIndex(TITLE)));
                    memoryDate = AESCrypt.decrypt(KeyID.DATE.toString(), cursor.getString(cursor.getColumnIndex(DATE)));
                    memoryLocation = AESCrypt.decrypt(KeyID.LOCATION.toString(), cursor.getString(cursor.getColumnIndex(LOCATION)));
                    memoryImage = AESCrypt.decrypt(KeyID.LARGE_IMAGE.toString(), cursor.getString(cursor.getColumnIndex(IMAGE_URL)));
                    memoryId = cursor.getString(cursor.getColumnIndex(ID));
                    memoryCategory = cursor.getString(cursor.getColumnIndex(CATEGORY));
                    memorySmallImage = AESCrypt.decrypt(KeyID.SMALL_IMAGE.toString(), cursor.getString(cursor.getColumnIndex(SMALLIMAGE)));
                    memoryDescription =  cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                    memoryUserID = cursor.getString(cursor.getColumnIndex(USER_ID));
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                }

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
    public List<Memory> getAllMemoriesCategory(String userID, String category) {
        List<Memory> memories = new ArrayList<Memory>();
        int x = 1;
        int i = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, date, title, imageURL, location, category, smallImageURL, description, userID  FROM " + TABLE_NAME + " WHERE userID = " + userID + " AND category =  '" + category + "' ORDER BY date ASC", null);
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                try {
                    memoryTitle = AESCrypt.decrypt(KeyID.TITLE.toString(), cursor.getString(cursor.getColumnIndex(TITLE)));
                    memoryDate = AESCrypt.decrypt(KeyID.DATE.toString(), cursor.getString(cursor.getColumnIndex(DATE)));
                    memoryLocation = AESCrypt.decrypt(KeyID.LOCATION.toString(), cursor.getString(cursor.getColumnIndex(LOCATION)));
                    memoryImage = AESCrypt.decrypt(KeyID.LARGE_IMAGE.toString(), cursor.getString(cursor.getColumnIndex(IMAGE_URL)));
                    memoryId = cursor.getString(cursor.getColumnIndex(ID));
                    memoryCategory = cursor.getString(cursor.getColumnIndex(CATEGORY));
                    memorySmallImage = AESCrypt.decrypt(KeyID.SMALL_IMAGE.toString(), cursor.getString(cursor.getColumnIndex(SMALLIMAGE)));
                    memoryDescription = cursor.getString(cursor.getColumnIndex(DESCRIPTION));
                    memoryUserID = cursor.getString(cursor.getColumnIndex(USER_ID));
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                }

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

    public void editCurrentMemory(Memory memory){
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            memoryTitle = AESCrypt.encrypt(KeyID.TITLE.toString(), memory.getTitle());
            memoryDate = AESCrypt.encrypt(KeyID.DATE.toString(), memory.getMemoryDate());
            memoryLocation = AESCrypt.encrypt(KeyID.LOCATION.toString(), memory.getLocation());
            memoryDescription = memory.getDescription();
            memoryCategory = memory.getCategory();
            memoryImage = AESCrypt.encrypt(KeyID.LARGE_IMAGE.toString(), memory.getImageResource());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        String titleQuery = TITLE + " = '" + memoryTitle + "' ";
        String dateQuery = DATE + " = '" + memoryDate + "' ";
        String descriptionQuery = DESCRIPTION+ " = '" + memoryDescription + "' ";
        String locationQuery = LOCATION + " = '" + memoryLocation + "' ";
        String categoryQuery = CATEGORY + " = '" + memoryCategory + "' ";
        String imageQuery = IMAGE_URL + " = '" + memoryImage + "' ";
        String seperator = ", ";

        try {
            String strSQL = "UPDATE " + TABLE_NAME + " SET " + titleQuery +  seperator + dateQuery + seperator + descriptionQuery + seperator + locationQuery +  seperator + categoryQuery + seperator + imageQuery +  " WHERE id = " + memory.getId();
            db.execSQL(strSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editUserDetails(String firstName, String lastName, String userID){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            String strSQL = "UPDATE " + TABLE_LOGIN + " SET firstName = '" + firstName +  "', lastName = '" + lastName + "'  WHERE id = " + userID;
            db.execSQL(strSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

