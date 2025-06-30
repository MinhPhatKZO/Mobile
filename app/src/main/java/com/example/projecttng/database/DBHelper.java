package com.example.projecttng.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Nhom4Mobile.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)");
        db.execSQL("CREATE TABLE foods(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, description TEXT, calories TEXT, price TEXT, time TEXT, imageResId INTEGER, soldCount INTEGER, likeCount INTEGER, rating REAL, type TEXT)");
        db.execSQL("CREATE TABLE cart(id INTEGER PRIMARY KEY AUTOINCREMENT, foodId INTEGER, quantity INTEGER, FOREIGN KEY(foodId) REFERENCES foods(id))");
        db.execSQL("CREATE TABLE orders(id INTEGER PRIMARY KEY AUTOINCREMENT, userId INTEGER, totalPrice INTEGER, paymentMethod TEXT, orderDate TEXT)");
        db.execSQL("CREATE TABLE order_details(id INTEGER PRIMARY KEY AUTOINCREMENT, orderId INTEGER, foodId INTEGER, quantity INTEGER, FOREIGN KEY(orderId) REFERENCES orders(id), FOREIGN KEY(foodId) REFERENCES foods(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS foods");
        db.execSQL("DROP TABLE IF EXISTS cart");
        db.execSQL("DROP TABLE IF EXISTS orders");
        db.execSQL("DROP TABLE IF EXISTS order_details");
        onCreate(db);
    }

    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long result = db.insert("users", null, values);
        return result != -1;
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean checkUserPass(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        boolean valid = cursor.getCount() > 0;
        cursor.close();
        return valid;
    }
}
