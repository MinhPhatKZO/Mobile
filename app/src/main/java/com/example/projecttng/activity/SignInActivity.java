package com.example.projecttng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projecttng.R;
import com.example.projecttng.dao.UserDao;
import com.google.android.material.textfield.TextInputEditText;

public class SignInActivity extends AppCompatActivity {

    private Button btnSignIn;
    private TextView signupText;
    private TextInputEditText usernameEditText, passwordEditText;

    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // layout đăng nhập

        userDao = new UserDao(this); // Khởi tạo DAO
        userDao.insertDefaultAdminIfNotExists(); // ✅ Thêm admin nếu chưa có

        // Ánh xạ view
        btnSignIn = findViewById(R.id.btnSignIn);
        signupText = findViewById(R.id.signup_text);
        usernameEditText = findViewById(R.id.username_edittext);
        passwordEditText = findViewById(R.id.password_edittext);

        // Đăng nhập
        btnSignIn.setOnClickListener(v -> {
            String username = usernameEditText.getText() != null ? usernameEditText.getText().toString().trim() : "";
            String password = passwordEditText.getText() != null ? passwordEditText.getText().toString().trim() : "";

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            if (userDao.isValidLogin(username, password)) {
                String role = userDao.getUserRole(username);
                Toast.makeText(this, "Đăng nhập thành công với quyền: " + role, Toast.LENGTH_SHORT).show();

                if ("admin".equalsIgnoreCase(role)) {
                    startActivity(new Intent(this, AdminDashboardActivity.class));
                } else {
                    startActivity(new Intent(this, HomeActivity.class));
                }

                finish();
            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });

        // Chuyển sang đăng ký
        signupText.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });
    }


    }

