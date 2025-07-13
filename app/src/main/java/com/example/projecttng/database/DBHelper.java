package com.example.projecttng.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "Nhom4Mobile.db";
    public static final int DB_VERSION = 3; // Đã tăng version để áp dụng thay đổi

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

        // Bảng đầu bếp (nếu bạn muốn tách riêng)
        db.execSQL("CREATE TABLE IF NOT EXISTS chefs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "bio TEXT," +
                "image TEXT)");

        // Bảng món ăn
        db.execSQL("CREATE TABLE IF NOT EXISTS foods (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "description TEXT," +
                "calories INTEGER," +
                "price REAL," +
                "time INTEGER," +
                "imageResId TEXT," +
                "soldCount INTEGER," +
                "likeCount INTEGER," +
                "rating REAL," +
                "type TEXT," +
                "chefId INTEGER," +
                "FOREIGN KEY(chefId) REFERENCES chefs(id))");

        // Bảng giỏ hàng
        db.execSQL("CREATE TABLE IF NOT EXISTS cart (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER," +
                "foodId INTEGER," +
                "quantity INTEGER," +
                "FOREIGN KEY(userId) REFERENCES users(id)," +
                "FOREIGN KEY(foodId) REFERENCES foods(id)," +
                "UNIQUE(userId, foodId))");

        // Bảng đơn hàng
        db.execSQL("CREATE TABLE IF NOT EXISTS orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER," +
                "totalPrice REAL," +
                "paymentMethod TEXT," +
                "orderDate TEXT," +
                "status TEXT DEFAULT 'pending'," +
                "deliveryName TEXT," +
                "deliveryPhone TEXT," +
                "deliveryAddress TEXT," +
                "FOREIGN KEY(userId) REFERENCES users(id))");

        // Bảng chi tiết đơn hàng
        db.execSQL("CREATE TABLE IF NOT EXISTS order_details (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "orderId INTEGER," +
                "foodId INTEGER," +
                "quantity INTEGER," +
                "priceAtOrderTime REAL," +
                "FOREIGN KEY(orderId) REFERENCES orders(id)," +
                "FOREIGN KEY(foodId) REFERENCES foods(id))");

        // Bảng tin nhắn chat
        db.execSQL("CREATE TABLE IF NOT EXISTS chat_messages (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER," +  // Người gửi
                "message TEXT," +
                "is_user INTEGER," +
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY(userId) REFERENCES users(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xoá tất cả bảng khi nâng cấp
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS chefs");
        db.execSQL("DROP TABLE IF EXISTS foods");
        db.execSQL("DROP TABLE IF EXISTS cart");
        db.execSQL("DROP TABLE IF EXISTS orders");
        db.execSQL("DROP TABLE IF EXISTS order_details");
        db.execSQL("DROP TABLE IF EXISTS chat_messages");
        onCreate(db);
    }
}
