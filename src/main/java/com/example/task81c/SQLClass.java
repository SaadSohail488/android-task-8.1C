package com.example.task81c;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class SQLClass extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "your_database_name";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String TABLE_PLAYLIST = "playlist";
    private static final String COLUMN_PLAYLIST_ID = "playlist_id";
    private static final String COLUMN_YOUTUBE_URL = "youtube_url";

    public SQLClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the users table if it doesn't exist
        String createUsersTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FULL_NAME + " TEXT NOT NULL, " +
                COLUMN_USERNAME + " TEXT NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL" +
                ")";
        db.execSQL(createUsersTableQuery);

        // Create the playlist table if it doesn't exist
        String createPlaylistTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_PLAYLIST + " (" +
                COLUMN_PLAYLIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_YOUTUBE_URL + " TEXT NOT NULL" +
                ")";
        db.execSQL(createPlaylistTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database upgrades if needed
        // This method is called when the DATABASE_VERSION is incremented
    }

    // Method to signup a new user
    public boolean signupUser(String fullName, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if the username already exists
        if (isUsernameTaken(username)) {
            return false; // Username is already taken, signup failed
        }

        // Insert the new user into the database
        ContentValues values = new ContentValues();
        values.put(COLUMN_FULL_NAME, fullName);
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long newRowId = db.insert(TABLE_USERS, null, values);

        return newRowId != -1; // Signup successful if the new row ID is valid
    }

    // Method to check if a username is already taken
    private boolean isUsernameTaken(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{username});
        boolean isTaken = cursor.getCount() > 0;
        cursor.close();

        return isTaken;
    }

    // Method to validate login credentials
    public boolean validateLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USERS +
                " WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{username, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();

        return isValid;
    }

    // Method to add a YouTube URL to the playlist
    public boolean addToPlaylist(String youtubeUrl) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();

        try {
            // Insert the YouTube URL into the playlist table
            ContentValues values = new ContentValues();
            values.put(COLUMN_YOUTUBE_URL, youtubeUrl);
            long newRowId = db.insert(TABLE_PLAYLIST, null, values);

            if (newRowId != -1) {
                db.setTransactionSuccessful();
                return true; // Returns true if the insertion is successful
            } else {
                return false;
            }
        } finally {
            db.endTransaction();
        }
    }

    // Method to retrieve the playlist
    public List<String> getPlaylist() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_PLAYLIST;
        Cursor cursor = db.rawQuery(selectQuery, null);

        List<String> playlist = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                String youtubeUrl = cursor.getString(cursor.getColumnIndex(COLUMN_YOUTUBE_URL));
                playlist.add(youtubeUrl);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return playlist;
    }
}
