package com.example.projecttng.activity;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projecttng.R;

public class NotificationSettingActivity extends AppCompatActivity {

    private Switch switchAllNotifications;
    private Switch switchPromotions;
    private Switch switchOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting);

        // Ánh xạ Switch từ layout
        switchAllNotifications = findViewById(R.id.switchAllNotifications);
        switchPromotions = findViewById(R.id.switchPromotions);
        switchOrders = findViewById(R.id.switchOrders);

        // Sự kiện bật/tắt toàn bộ thông báo
        switchAllNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            switchPromotions.setChecked(isChecked);
            switchOrders.setChecked(isChecked);
            Toast.makeText(this, isChecked ? "Đã bật tất cả thông báo" : "Đã tắt tất cả thông báo", Toast.LENGTH_SHORT).show();
        });

        // Sự kiện riêng cho từng loại thông báo
        switchPromotions.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(this, isChecked ? "Bật khuyến mãi" : "Tắt khuyến mãi", Toast.LENGTH_SHORT).show();
        });

        switchOrders.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(this, isChecked ? "Bật đơn hàng" : "Tắt đơn hàng", Toast.LENGTH_SHORT).show();
        });
    }
}
