package com.example.projecttng.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projecttng.database.DBHelper;
import com.example.projecttng.model.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatDao {
    private final DBHelper dbHelper;

    public ChatDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Thêm tin nhắn mới
    public void insertMessage(String message, boolean isUser) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("message", message);
        values.put("is_user", isUser ? 1 : 0);
        db.insert("chat_messages", null, values);
        db.close();
    }

    // Lấy toàn bộ tin nhắn
    public List<Message> getAllMessages() {
        List<Message> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("chat_messages", null, null, null, null, null, "id ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String text = cursor.getString(cursor.getColumnIndexOrThrow("message"));
                boolean isUser = cursor.getInt(cursor.getColumnIndexOrThrow("is_user")) == 1;
                list.add(new Message(text, isUser));
            } while (cursor.moveToNext());
            cursor.close();
        }

        db.close();
        return list;
    }
}
