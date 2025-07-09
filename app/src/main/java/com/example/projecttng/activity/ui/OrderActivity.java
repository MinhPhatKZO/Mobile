package com.example.projecttng.activity.ui;

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
        // Thêm hiệu ứng khi vào Activity
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Ánh xạ các view
        btnBack = findViewById(R.id.btn_back);
        btnContinue = findViewById(R.id.btn_continue);

        // Sự kiện nút quay lại
        btnBack.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out); // hiệu ứng khi thoát
        });

        // Sự kiện nút "Tiếp tục mua sắm"
        btnContinue.setOnClickListener(view -> {
            Intent intent = new Intent(OrderActivity.this, CategoryActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out); // hiệu ứng khi chuyển
            finish();
        });
    }
}
