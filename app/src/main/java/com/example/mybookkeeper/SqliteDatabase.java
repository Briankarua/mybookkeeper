package com.example.mybookkeeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mybookkeeper.accounts.Account;
import com.example.mybookkeeper.managers.Managers;

import java.util.ArrayList;

public class SqliteDatabase extends SQLiteOpenHelper {
    //DATABASE
     private static final int DATABASE_VERSION = 1;
     private static final String DATABASE_NAME = "my_manager.db";

    //TABLES
     private static final String MANAGER_TABLE = "managers";
    private static final String ACCOUNT_TABLE = "accounts";

     //MANAGER_TABLE COLUMNS
     private static final String MANAGER_ID = "ManagerId";
     private static final String MANAGER_NAME = "ManagerName";
     private static final String MANAGER_TASK = "Task";

    //ACCOUNT_TABLE COLUMNS
    private static final String ACCOUNT_ID = "AccountId";
    private static final String ACCOUNT_NAME = "AccountName";
    private static final String ACCOUNT_DESCRIPTION = "AccDescription";

     public SqliteDatabase(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

    //CREATE TABLES
    @Override
     public void onCreate(SQLiteDatabase db) {
            //CREATE MANAGER TABLE
            String CREATE_MANAGER_TABLE = "CREATE TABLE "
                    + MANAGER_TABLE + "(" + MANAGER_ID
                    + " INTEGER PRIMARY KEY,"
                    + MANAGER_NAME + " TEXT,"
                    + MANAGER_TASK + " TEXT" + ")";

            //CREATE ACCOUNT TABLE
            String CREATE_ACCOUNT_TABLE = "CREATE TABLE "
                    + ACCOUNT_TABLE + "(" + ACCOUNT_ID
                    + " INTEGER PRIMARY KEY,"
                    + ACCOUNT_NAME + " TEXT,"
                    + ACCOUNT_DESCRIPTION + " TEXT" + ")";

            db.execSQL(CREATE_MANAGER_TABLE);
            db.execSQL(CREATE_ACCOUNT_TABLE);
        }

    //DROP TABLE IF EXISTS
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MANAGER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + ACCOUNT_TABLE);
        onCreate(db);
    }

//FETCHING ALL MANAGERS============================================================
     public ArrayList<Managers> listManagers() {
            String sql = "select * from " + MANAGER_TABLE;
            SQLiteDatabase db = this.getReadableDatabase();
            ArrayList<Managers> storeManagers = new ArrayList<>();
            Cursor cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
        do {
                    int id = Integer.parseInt(cursor.getString(0));
                    String managerName = cursor.getString(1);
                    String task = cursor.getString(2);
                    storeManagers.add(new Managers(id, managerName, task));
                }
                while (cursor.moveToNext());
            }
            cursor.close();
            return storeManagers;
        }

    //==================== FETCHING ITEMS ========================================
    //FETCHING ALL ACCOUNTS
    public ArrayList<Account> listAccounts() {
        String sql = "select * from " + ACCOUNT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Account> storeAccounts = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String accountName = cursor.getString(1);
                String accDescription = cursor.getString(2);
                storeAccounts.add(new Account(id, accountName, accDescription));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeAccounts;
    }

    //==================== ADD ITEM ========================================
    //==================== ADD ITEM ========================================
    //ADD A MANAGER
     public void addManagers(Managers managers) {
            ContentValues values = new ContentValues();
            values.put(MANAGER_NAME, managers.getManagerName());
            values.put(MANAGER_TASK, managers.getTask());
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(MANAGER_TABLE, null, values);
        }

    //ADD AN ACCOUNT
    public void addAccounts(Account accounts) {
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_NAME, accounts.getAccountName());
        values.put(ACCOUNT_DESCRIPTION, accounts.getAccDescription());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(ACCOUNT_TABLE, null, values);
    }

    //==================== UPDAATE ITEM ========================================
    //==================== UPDAATE ITEM ========================================
    //UPDAATE MANAGER
    public void updateManagers(Managers managers) {
        ContentValues values = new ContentValues();
        values.put(MANAGER_NAME, managers.getManagerName());
        values.put(MANAGER_TASK, managers.getTask());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(MANAGER_TABLE, values, MANAGER_ID + " = ?", new String[]{String.valueOf(managers.getManagerId())});
     }

    //UPDAATE ACCOUNT
    public void updateAccounts(Account account) {
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_NAME, account.getAccountName());
        values.put(ACCOUNT_DESCRIPTION, account.getAccDescription());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(ACCOUNT_TABLE, values, ACCOUNT_ID + " = ?", new String[]{String.valueOf(account.getAccountId())});
    }

    //==================== DELETE ITEM ========================================
    //==================== DELETE ITEM ========================================
    //DELETE MANAGER
    public void deleteManager(int managerId) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(MANAGER_TABLE, MANAGER_ID + " = ?", new String[]{String.valueOf(managerId)});
        }

    //DELETE ACCOUNT
    public void deleteAccount(int accountId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(ACCOUNT_TABLE, ACCOUNT_ID + " = ?", new String[]{String.valueOf(accountId)});
    }
}
