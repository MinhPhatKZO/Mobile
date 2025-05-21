package com.example.projecttng;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class SignInActivity extends AppCompatActivity {

    private Button btnSignIn;
    private TextView signupText;
    private TextInputEditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Đổi tên layout tương ứng của bạn

        btnSignIn = findViewById(R.id.btnSignIn);
        signupText = findViewById(R.id.signup_text);
        usernameEditText = findViewById(R.id.username_edittext);
        passwordEditText = findViewById(R.id.password_edittext);

        btnSignIn.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignInActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
            } else {
                // Giả sử kiểm tra đăng nhập thành công với username/password mẫu
                if (username.equals("admin") && password.equals("123456")) {
                    Toast.makeText(SignInActivity.this, "Signing in...", Toast.LENGTH_SHORT).show();
                    // Chuyển sang HomeActivity
                    Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish(); // đóng SignInActivity để không quay lại màn hình đăng nhập khi bấm back
                } else {
                    Toast.makeText(SignInActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signupText.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}
