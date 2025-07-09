package com.example.projecttng.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.projecttng.database.DBHelper;
import com.example.projecttng.model.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailDao {
    private static final String TABLE_NAME = "order_details";
    private final DBHelper dbHelper;

    public OrderDetailDao(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Thêm 1 dòng chi tiết đơn hàng
    public boolean insertOrderDetail(OrderDetail detail) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("orderId", detail.getOrderId());
        values.put("foodId", detail.getFoodId());
        values.put("quantity", detail.getQuantity());

        long result = db.insert(TABLE_NAME, null, values);
        db.close(); //  Đóng sau khi ghi
        return result != -1;
    }

    //  Thêm nhiều chi tiết đơn hàng cùng lúc
    public boolean insertMultipleOrderDetails(List<OrderDetail> details) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            for (OrderDetail detail : details) {
                ContentValues values = new ContentValues();
                values.put("orderId", detail.getOrderId());
                values.put("foodId", detail.getFoodId());
                values.put("quantity", detail.getQuantity());
                db.insert(TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    // Lấy danh sách chi tiết theo orderId
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> details = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE orderId = ?", new String[]{String.valueOf(orderId)});

        if (cursor.moveToFirst()) {
            do {
                OrderDetail detail = new OrderDetail();
                detail.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                detail.setOrderId(cursor.getInt(cursor.getColumnIndexOrThrow("orderId")));
                detail.setFoodId(cursor.getInt(cursor.getColumnIndexOrThrow("foodId")));
                detail.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow("quantity")));
                details.add(detail);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close(); // Đóng sau khi đọc
        return details;
    }

    // Xoá tất cả chi tiết đơn theo orderId
    public void deleteOrderDetailsByOrderId(int orderId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TABLE_NAME, "orderId = ?", new String[]{String.valueOf(orderId)});
        db.close();
    }

}
