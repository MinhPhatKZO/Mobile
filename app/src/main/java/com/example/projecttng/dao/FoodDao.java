package com.example.projecttng.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projecttng.R;
import com.example.projecttng.database.DBHelper;
import com.example.projecttng.model.FoodItem;

import java.util.ArrayList;
import java.util.List;

public class FoodDao {
    private final DBHelper dbHelper;

    public FoodDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    public List<FoodItem> getAllFoods() {
        List<FoodItem> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM foods", null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                FoodItem item = new FoodItem(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getString(cursor.getColumnIndexOrThrow("calories")),
                        cursor.getString(cursor.getColumnIndexOrThrow("price")),
                        cursor.getString(cursor.getColumnIndexOrThrow("time")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("imageResId")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("soldCount")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("likeCount")),
                        cursor.getFloat(cursor.getColumnIndexOrThrow("rating")),
                        FoodItem.FoodType.valueOfSafe(cursor.getString(cursor.getColumnIndexOrThrow("type"))),
                        cursor.getInt(cursor.getColumnIndexOrThrow("chefId")) // ✅ lấy chefId
                );
                list.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return list;
    }

    public long insertFood(FoodItem item) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("description", item.getDescription());
        values.put("calories", item.getCalories());
        values.put("price", item.getPrice());
        values.put("time", item.getTime());
        values.put("imageResId", item.getImageResId());
        values.put("soldCount", item.getSoldCount());
        values.put("likeCount", item.getLikeCount());
        values.put("rating", item.getRating());
        values.put("type", item.getType().toRawString());
        values.put("chefId", item.getChefId()); // ✅ thêm chefId

        return db.insert("foods", null, values);
    }

    public void insertSampleFoodsIfEmpty() {
        if (!getAllFoods().isEmpty()) return;

        insertFood(new FoodItem("Sushi Maki", "Sushi tươi ngon chuẩn vị Nhật", "100kcal", "40000", "60min", R.drawable.sushi, 100, 10, 4.8f, FoodItem.FoodType.FOOD, 1));
        insertFood(new FoodItem("Bánh Mì", "Bánh mì giòn rụm", "200kcal", "25000", "30min", R.drawable.banhmi, 100, 10, 4.5f, FoodItem.FoodType.FOOD, 1));
        insertFood(new FoodItem("Gà Rán", "Gà rán giòn tan", "1500kcal", "40000", "60min", R.drawable.garan, 80, 8, 4.6f, FoodItem.FoodType.FOOD, 2));
        insertFood(new FoodItem("Bún Bò", "Nước lèo đậm đà", "2000kcal", "35000", "30min", R.drawable.bunbo, 90, 9, 4.7f, FoodItem.FoodType.FOOD, 2));
        insertFood(new FoodItem("Trà Đá", "Trà đá mát lạnh", "10kcal", "5000", "2min", R.drawable.trada, 50, 5, 4.0f, FoodItem.FoodType.DRINK, 3));
        insertFood(new FoodItem("Trà Sữa", "Vị béo ngậy", "150kcal", "30000", "10min", R.drawable.trasua, 60, 6, 4.2f, FoodItem.FoodType.DRINK, 3));
    }

    public FoodItem getFoodById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        FoodItem item = null;

        Cursor cursor = db.rawQuery("SELECT * FROM foods WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            item = new FoodItem(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("description")),
                    cursor.getString(cursor.getColumnIndexOrThrow("calories")),
                    cursor.getString(cursor.getColumnIndexOrThrow("price")),
                    cursor.getString(cursor.getColumnIndexOrThrow("time")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("imageResId")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("soldCount")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("likeCount")),
                    cursor.getFloat(cursor.getColumnIndexOrThrow("rating")),
                    FoodItem.FoodType.valueOfSafe(cursor.getString(cursor.getColumnIndexOrThrow("type"))),
                    cursor.getInt(cursor.getColumnIndexOrThrow("chefId")) // ✅ thêm chefId
            );
            cursor.close();
        }

        return item;
    }

    public void updateFood(FoodItem food) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", food.getName());
        values.put("description", food.getDescription());
        values.put("calories", food.getCalories());
        values.put("price", food.getPrice());
        values.put("time", food.getTime());
        values.put("imageResId", food.getImageResId());
        values.put("soldCount", food.getSoldCount());
        values.put("likeCount", food.getLikeCount());
        values.put("rating", food.getRating());
        values.put("type", food.getType().toRawString());
        values.put("chefId", food.getChefId()); // ✅ update chefId

        db.update("foods", values, "id = ?", new String[]{String.valueOf(food.getId())});
        db.close();
    }

    public void deleteFood(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("foods", "id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // ✅ Lấy món ăn theo đầu bếp
    public List<FoodItem> getFoodsByChef(int chefId) {
        List<FoodItem> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM foods WHERE chefId = ?", new String[]{String.valueOf(chefId)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                FoodItem item = new FoodItem(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("description")),
                        cursor.getString(cursor.getColumnIndexOrThrow("calories")),
                        cursor.getString(cursor.getColumnIndexOrThrow("price")),
                        cursor.getString(cursor.getColumnIndexOrThrow("time")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("imageResId")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("soldCount")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("likeCount")),
                        cursor.getFloat(cursor.getColumnIndexOrThrow("rating")),
                        FoodItem.FoodType.valueOfSafe(cursor.getString(cursor.getColumnIndexOrThrow("type"))),
                        cursor.getInt(cursor.getColumnIndexOrThrow("chefId"))
                );
                list.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }

        return list;
    }
}
