package com.example.projecttng.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Nhom4Mobile.db";
    public static final int DB_VERSION = 2; // Tăng version lên để upgrade database

    public DBHelper(Context context) {
        super(context, DBNAME, null, DB_VERSION);
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
                "type TEXT," +
                "chefId INTEGER)"); // ✅ thêm cột chefId


        // Bảng giỏ hàng liên kết với users và foods
        db.execSQL("CREATE TABLE IF NOT EXISTS cart (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER," +
                "foodId INTEGER," +
                "quantity INTEGER," +
                "FOREIGN KEY(userId) REFERENCES users(id)," +
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


        db.execSQL("CREATE TABLE IF NOT EXISTS chat_messages (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "message TEXT," +
                "is_user INTEGER," +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xoá tất cả bảng khi nâng cấp (hoặc bạn có thể xử lý nâng cấp từng bảng riêng)
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS foods");
        db.execSQL("DROP TABLE IF EXISTS cart");
        db.execSQL("DROP TABLE IF EXISTS orders");
        db.execSQL("DROP TABLE IF EXISTS order_details");
        db.execSQL("DROP TABLE IF EXISTS chat_messages");
        onCreate(db);
    }
}
