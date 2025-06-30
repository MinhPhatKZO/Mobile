package com.example.projecttng.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Nhom4Mobile.db";

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Bảng người dùng
        db.execSQL("CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE," +
                "password TEXT," +
                "role TEXT DEFAULT 'user')");

        // Bảng món ăn
        db.execSQL("CREATE TABLE IF NOT EXISTS foods (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "description TEXT," +
                "calories TEXT," +
                "price TEXT," +
                "time TEXT," +
                "imageResId INTEGER," +
                "soldCount INTEGER," +
                "likeCount INTEGER," +
                "rating REAL," +
                "type TEXT)");

        // Bảng giỏ hàng
        db.execSQL("CREATE TABLE IF NOT EXISTS cart (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "foodId INTEGER," +
                "quantity INTEGER," +
                "FOREIGN KEY(foodId) REFERENCES foods(id))");

        // Bảng đơn hàng
        db.execSQL("CREATE TABLE IF NOT EXISTS orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER," +
                "totalPrice INTEGER," +
                "paymentMethod TEXT," +
                "orderDate TEXT)");

        // Bảng chi tiết đơn hàng
        db.execSQL("CREATE TABLE IF NOT EXISTS order_details (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "orderId INTEGER," +
                "foodId INTEGER," +
                "quantity INTEGER," +
                "FOREIGN KEY(orderId) REFERENCES orders(id)," +
                "FOREIGN KEY(foodId) REFERENCES foods(id))");
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
}
