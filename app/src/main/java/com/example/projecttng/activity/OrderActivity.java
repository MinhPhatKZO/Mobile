package com.example.projecttng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projecttng.R;

public class OrderActivity extends AppCompatActivity {

    private ImageView btnBack;
    private Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order); // Đảm bảo file XML của bạn có tên là activity_order.xml

        // Ánh xạ các view
        btnBack = findViewById(R.id.btn_back);
        btnContinue = findViewById(R.id.btn_continue);

        // Sự kiện nút quay lại
        btnBack.setOnClickListener(view -> finish());

        // Sự kiện nút "Tiếp tục mua sắm"
        btnContinue.setOnClickListener(view -> {
            Intent intent = new Intent(OrderActivity.this, CategoryActivity.class); // hoặc MainActivity nếu bạn có trang chủ
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // Đóng màn hình hiện tại
        });
    }
}
