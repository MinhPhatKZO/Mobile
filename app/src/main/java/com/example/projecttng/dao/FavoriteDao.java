package com.example.projecttng.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projecttng.database.DBHelper;
import com.example.projecttng.model.FoodItem;
import com.example.projecttng.model.FoodItem.FoodType;

import java.util.ArrayList;
import java.util.List;

public class FavoriteDao {

    private final DBHelper dbHelper;

    public FavoriteDao(Context context) {
        this.dbHelper = new DBHelper(context);
        // Đảm bảo bảng favorites tồn tại (tránh lỗi khi DB đã được tạo từ trước)
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS favorites(id INTEGER PRIMARY KEY AUTOINCREMENT, foodId INTEGER UNIQUE, FOREIGN KEY(foodId) REFERENCES foods(id))");
    }

    /**
     * Thêm một món ăn vào danh sách yêu thích. Nếu đã tồn tại thì bỏ qua.
     */
    public void addFavorite(int foodId) {
        if (isFavorite(foodId))
            return; // đã tồn tại
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("foodId", foodId);
        db.insert("favorites", null, values);
        db.close();
    }

    /**
     * Xoá một món ăn khỏi danh sách yêu thích.
     */
    public void removeFavorite(int foodId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("favorites", "foodId = ?", new String[] { String.valueOf(foodId) });
        db.close();
    }

    /**
     * Kiểm tra xem món ăn đã được yêu thích chưa.
     */
    public boolean isFavorite(int foodId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        boolean exists;
        try (Cursor cursor = db.rawQuery("SELECT 1 FROM favorites WHERE foodId = ?",
                new String[] { String.valueOf(foodId) })) {
            exists = cursor.moveToFirst();
        }
        db.close();
        return exists;
    }

    /**
     * Lấy toàn bộ danh sách món ăn yêu thích.
     */
    public List<FoodItem> getAllFavoriteFoods() {
        List<FoodItem> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT f.* FROM favorites fav JOIN foods f ON fav.foodId = f.id";
        try (Cursor cursor = db.rawQuery(query, null)) {
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String desc = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                    String calories = cursor.getString(cursor.getColumnIndexOrThrow("calories"));
                    String price = cursor.getString(cursor.getColumnIndexOrThrow("price"));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                    int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow("imageResId"));
                    int sold = cursor.getInt(cursor.getColumnIndexOrThrow("soldCount"));
                    int likes = cursor.getInt(cursor.getColumnIndexOrThrow("likeCount"));
                    float rating = cursor.getFloat(cursor.getColumnIndexOrThrow("rating"));
                    String typeStr = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                    FoodType type = FoodType.valueOfSafe(typeStr);

                    FoodItem item = new FoodItem(id, name, desc, calories, price, time, imageResId, sold, likes, rating,
                            type);
                    list.add(item);
                } while (cursor.moveToNext());
            }
        }
        db.close();
        return list;
    }
}