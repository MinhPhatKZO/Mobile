package com.example.projecttng.activity.sp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projecttng.R;

public class HelpCenterActivity extends AppCompatActivity {

    LinearLayout itemHowToOrder, itemForgotPassword, itemContactSupport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);

        itemHowToOrder = findViewById(R.id.itemHowToOrder);
        itemForgotPassword = findViewById(R.id.itemForgotPassword);
        itemContactSupport = findViewById(R.id.itemContactSupport);

        itemHowToOrder.setOnClickListener(v ->
                Toast.makeText(this, "Hướng dẫn đặt món sẽ được cập nhật sau.", Toast.LENGTH_SHORT).show());

        itemForgotPassword.setOnClickListener(v ->
                Toast.makeText(this, "Vào Cài đặt > Quên mật khẩu để reset.", Toast.LENGTH_SHORT).show());

        itemContactSupport.setOnClickListener(v -> {
            // Mở gọi điện đến bộ phận hỗ trợ
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:0123456789"));
            startActivity(intent);
        });
    }
}
