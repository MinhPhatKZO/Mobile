package com.example.projecttng.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projecttng.database.DBHelper;

public class UserDao {
    private final DBHelper dbHelper;

    public UserDao(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    // Đăng ký người dùng
    public boolean insertUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long result = db.insert("users", null, values);
        return result != -1;
    }

    // Kiểm tra tên đăng nhập đã tồn tại chưa
    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Kiểm tra tài khoản đăng nhập hợp lệ
    public boolean isValidLogin(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password});
        boolean valid = cursor.getCount() > 0;
        cursor.close();
        return valid;
    }

    // Lấy ID người dùng dựa vào username
    public int getUserId(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM users WHERE username = ?", new String[]{username});
        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
        }
        cursor.close();
        return userId;
    }
}
