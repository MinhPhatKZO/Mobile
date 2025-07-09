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

public class CartDao {

    private final DBHelper dbHelper;

    public CartDao(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    // Thêm hoặc cập nhật giỏ hàng theo user
    public void addOrUpdateCartItem(int userId, int foodId, int quantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try (Cursor cursor = db.rawQuery(
                "SELECT quantity FROM cart WHERE userId = ? AND foodId = ?",
                new String[]{String.valueOf(userId), String.valueOf(foodId)})) {

            if (cursor.moveToFirst()) {
                int currentQuantity = cursor.getInt(0);
                ContentValues values = new ContentValues();
                values.put("quantity", currentQuantity + quantity);
                db.update("cart", values, "userId = ? AND foodId = ?",
                        new String[]{String.valueOf(userId), String.valueOf(foodId)});
            } else {
                ContentValues values = new ContentValues();
                values.put("userId", userId);
                values.put("foodId", foodId);
                values.put("quantity", quantity);
                db.insert("cart", null, values);
            }
        }

        db.close();
    }

    public void addOrUpdateItem(int userId, FoodItem item) {
        addOrUpdateCartItem(userId, item.getId(), item.getQuantity());
    }

    // Xóa một món khỏi giỏ hàng của user
    public void removeFromCart(int userId, int foodId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("cart", "userId = ? AND foodId = ?",
                new String[]{String.valueOf(userId), String.valueOf(foodId)});
        db.close();
    }

    // Cập nhật số lượng món trong giỏ
    public void updateQuantityByFoodId(int userId, int foodId, int quantity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", quantity);
        db.update("cart", values, "userId = ? AND foodId = ?",
                new String[]{String.valueOf(userId), String.valueOf(foodId)});
        db.close();
    }

    // Lấy tất cả món trong giỏ hàng của user
    public List<FoodItem> getAllCartItems(int userId) {
        List<FoodItem> cartItems = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT f.*, c.quantity FROM cart c " +
                "JOIN foods f ON c.foodId = f.id WHERE c.userId = ?";
        try (Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)})) {
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
                    FoodType type = FoodType.valueOf(typeStr.toUpperCase());

                    int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity"));

                    FoodItem item = new FoodItem(id, name, desc, calories, price, time,
                            imageResId, sold, likes, rating, type);
                    item.setQuantity(quantity);
                    cartItems.add(item);
                } while (cursor.moveToNext());
            }
        }

        db.close();
        return cartItems;
    }

    // Tính tổng số lượng sản phẩm trong giỏ hàng của user
    public int getTotalQuantity(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int total = 0;

        try (Cursor cursor = db.rawQuery(
                "SELECT SUM(quantity) FROM cart WHERE userId = ?",
                new String[]{String.valueOf(userId)})) {
            if (cursor.moveToFirst()) {
                total = cursor.getInt(0);
            }
        }

        db.close();
        return total;
    }

    // Tính tổng giá tiền giỏ hàng của user
    public int getTotalPrice(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int total = 0;

        String query = "SELECT f.price, c.quantity FROM cart c " +
                "JOIN foods f ON c.foodId = f.id WHERE c.userId = ?";
        try (Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)})) {
            if (cursor.moveToFirst()) {
                do {
                    String priceStr = cursor.getString(0)
                            .replace(".", "")
                            .replace("đ", "")
                            .trim();
                    int price = Integer.parseInt(priceStr);
                    int qty = cursor.getInt(1);
                    total += price * qty;
                } while (cursor.moveToNext());
            }
        }

        db.close();
        return total;
    }

    // Xóa toàn bộ giỏ hàng của user
    public void clearCart(int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("cart", "userId = ?", new String[]{String.valueOf(userId)});
        db.close();
    }
}
