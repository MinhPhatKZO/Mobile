package com.example.projecttng.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projecttng.database.DBHelper;
import com.example.projecttng.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    private DBHelper dbHelper;

    public OrderDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Thêm đơn hàng
    public long insertOrder(Order order) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", order.getUserId());
        values.put("totalPrice", order.getTotalPrice());
        values.put("paymentMethod", order.getPaymentMethod());
        values.put("orderDate", order.getOrderDate());

        return db.insert("orders", null, values); // trả về id của đơn hàng
    }

    // Lấy danh sách đơn theo userId
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orderList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM orders WHERE userId = ? ORDER BY orderDate DESC", new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                order.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow("userId")));
                order.setTotalPrice(cursor.getInt(cursor.getColumnIndexOrThrow("totalPrice")));
                order.setPaymentMethod(cursor.getString(cursor.getColumnIndexOrThrow("paymentMethod")));
                order.setOrderDate(cursor.getString(cursor.getColumnIndexOrThrow("orderDate")));
                orderList.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orderList;
    }
    // Add this method inside OrderDao
    public Order getOrderById(int orderId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM orders WHERE id = ?", new String[]{String.valueOf(orderId)});
        Order order = null;
        if (cursor.moveToFirst()) {
            order = new Order();
            order.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
            order.setUserId(cursor.getInt(cursor.getColumnIndexOrThrow("userId")));
            order.setTotalPrice(cursor.getInt(cursor.getColumnIndexOrThrow("totalPrice")));
            order.setPaymentMethod(cursor.getString(cursor.getColumnIndexOrThrow("paymentMethod")));
            order.setOrderDate(cursor.getString(cursor.getColumnIndexOrThrow("orderDate")));
        }
        cursor.close();
        return order;
    }

}
