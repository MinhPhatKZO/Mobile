package com.example.projecttng.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projecttng.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final DBHelper dbHelper;

    public UserDao(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    // Đăng ký người dùng với quyền cụ thể (mặc định là user)
    public boolean insertUser(String username, String password, String role) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username); // truyền giá trị vào
        values.put("password", password);
        values.put("role", role);
        long result = db.insert("users", null, values);
        return result != -1;
    }

    // Hàm đăng ký mặc định (user thường)
    public boolean insertUser(String username, String password) {
        return insertUser(username, password, "user");
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
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[]{username, password}); // tránh SQL Injection
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

    // Lấy quyền của người dùng: admin hoặc user
    public String getUserRole(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT role FROM users WHERE username = ?", new String[]{username});
        String role = "user"; // Mặc định là user nếu không tìm thấy
         // Trả về quyền của người dùng
        if (cursor.moveToFirst()) {
            role = cursor.getString(cursor.getColumnIndexOrThrow("role")); // phần cấp admin và user
        }
        cursor.close();
        return role;
    }
    // Lấy tất cả người dùng và quyền của họ
    public List<String> getAllUsers() {
        List<String> userList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT username, role FROM users", null);
        if (cursor.moveToFirst()) {
            do {
                String username = cursor.getString(0);
                String role = cursor.getString(1);
                userList.add(username + " (" + role + ")");
            } while (cursor.moveToNext());
        }
        cursor.close();
        return userList;
    }
    // Lấy tất cả tên người dùng
    public List<String> getAllUsernames() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<String> usernames = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT username FROM users", null);
        while (cursor.moveToNext()) {
            usernames.add(cursor.getString(cursor.getColumnIndexOrThrow("username")));
        }
        cursor.close();
        return usernames;
    }

    // Xóa người dùng theo username
    public void deleteUser(String username) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("users", "username = ?", new String[]{username});
    }
    //  tài khoản admin mặc định

    public void insertDefaultAdminIfNotExists() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = 'admin'", null);
        if (!cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put("username", "admin");
            values.put("password", "admin123");
            values.put("role", "admin");
            db.insert("users", null, values);
        }
        cursor.close();
    }
    // Trả về password dựa theo username
    public String getPasswordByUsername(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT password FROM users WHERE username = ?", new String[]{username});
        String password = "";
        if (cursor.moveToFirst()) {
            password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
        }
        cursor.close();
        return password;
    }
    public int getUserIdByUsername(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id FROM User WHERE username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            cursor.close();
            return id;
        }
        cursor.close();
        return -1;
    }



}
