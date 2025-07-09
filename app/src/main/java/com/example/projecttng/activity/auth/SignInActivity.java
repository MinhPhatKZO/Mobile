package com.example.projecttng.activity.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projecttng.R;
import com.example.projecttng.activity.admin.AdminDashboardActivity;
import com.example.projecttng.activity.ui.HomeActivity;
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
        setContentView(R.layout.activity_main); // Giao diện đăng nhập

        userDao = new UserDao(this); // Khởi tạo DAO
        userDao.insertDefaultAdminIfNotExists(); // ✅ Thêm admin mặc định nếu chưa có

        // Ánh xạ view
        btnSignIn = findViewById(R.id.btnSignIn);
        signupText = findViewById(R.id.signup_text);
        usernameEditText = findViewById(R.id.username_edittext);
        passwordEditText = findViewById(R.id.password_edittext);

        // Xử lý đăng nhập
        btnSignIn.setOnClickListener(v -> {
            String username = usernameEditText.getText() != null ? usernameEditText.getText().toString().trim() : "";
            String password = passwordEditText.getText() != null ? passwordEditText.getText().toString().trim() : "";

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            if (userDao.isValidLogin(username, password)) {
                int userId = userDao.getUserId(username); // ✅ dùng getUserId đúng hàm
                String role = userDao.getUserRole(username);

                if (userId == -1) {
                    Toast.makeText(this, "Lỗi lấy thông tin người dùng!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // ✅ Lưu vào SharedPreferences
                SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("userId", userId);
                editor.putString("username", username);
                editor.putString("role", role);
                editor.apply();

                Toast.makeText(this, "Đăng nhập thành công với quyền: " + role, Toast.LENGTH_SHORT).show();

                // Chuyển màn
                if ("admin".equalsIgnoreCase(role)) {
                    startActivity(new Intent(this, AdminDashboardActivity.class));
                } else {
                    startActivity(new Intent(this, HomeActivity.class));
                }

                finish(); // kết thúc SignInActivity
            } else {
                Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
            }
        });

        // Chuyển sang màn đăng ký
        signupText.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });
    }
}
