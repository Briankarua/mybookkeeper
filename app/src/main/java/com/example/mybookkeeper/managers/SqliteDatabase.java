package com.example.mybookkeeper.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

class SqliteDatabase extends SQLiteOpenHelper {
     private static final int DATABASE_VERSION = 1;
     private static final String DATABASE_NAME = "manage_accounts.db";
     private static final String TABLE_NAME = "managers";
     private static final String MANAGER_ID = "ManagerId";
     private static final String MANAGER_NAME = "ManagerName";
     private static final String MANAGER_NO = "Job";

     SqliteDatabase(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
     public void onCreate(SQLiteDatabase db) {
            String CREATE_CONTACTS_TABLE = "CREATE TABLE "
                    + TABLE_NAME + "(" + MANAGER_ID
                    + " INTEGER PRIMARY KEY,"
                    + MANAGER_NAME + " TEXT,"
                    + MANAGER_NO + " TEXT" + ")";
            db.execSQL(CREATE_CONTACTS_TABLE);
        }
        @Override
     public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
     ArrayList<com.example.mybookkeeper.managers.Contacts> listContacts() {
            String sql = "select * from " + TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<com.example.mybookkeeper.managers.Contacts> storeContacts = new ArrayList<>();
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
        do {
                    int id = Integer.parseInt(cursor.getString(0));
                    String name = cursor.getString(1);
                    String job = cursor.getString(2);
                    storeContacts.add(new com.example.mybookkeeper.managers.Contacts(id, name, job));
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            return storeContacts;
        }
     void addContacts(com.example.mybookkeeper.managers.Contacts contacts) {
            ContentValues values = new ContentValues();
            values.put(MANAGER_NAME, contacts.getName());
            values.put(MANAGER_NO, contacts.getJob());
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(TABLE_NAME, null, values);
        }
     void updateContacts(com.example.mybookkeeper.managers.Contacts contacts) {
            ContentValues values = new ContentValues();
            values.put(MANAGER_NAME, contacts.getName());
            values.put(MANAGER_NO, contacts.getJob());
            SQLiteDatabase db = this.getWritableDatabase();
            db.update(TABLE_NAME, values, MANAGER_ID + " = ?", new String[]{String.valueOf(contacts.getId())});
        }
     void deleteContact(int id) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME, MANAGER_ID + " = ?", new String[]{String.valueOf(id)});
        }
}
