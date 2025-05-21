package com.example.projecttng;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnSignIn;
    private TextView signupText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Thay bằng tên file layout Sign In của bạn

        btnSignIn = findViewById(R.id.btnSignIn);
        signupText = findViewById(R.id.signup_text);

        // Sự kiện click Sign In (nếu cần)
        btnSignIn.setOnClickListener(v -> {
            // TODO: Xử lý đăng nhập
        });

        // Sự kiện click Sign Up Now - chuyển sang SignUpActivity
        signupText.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}
