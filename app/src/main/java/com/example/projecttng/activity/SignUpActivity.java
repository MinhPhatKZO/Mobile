package com.example.projecttng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projecttng.R;
import com.example.projecttng.database.DBHelper;

public class SignUpActivity extends AppCompatActivity {

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Hiển thị nút back trên thanh công cụ (nếu có)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Sign Up");
        }

        dbHelper = new DBHelper(this);

        // Ánh xạ các thành phần giao diện
        ImageView btnBack = findViewById(R.id.btn_back);
        EditText usernameEditText = findViewById(R.id.signup_username_edittext);
        EditText passwordEditText = findViewById(R.id.signup_password_edittext);
        EditText confirmPasswordEditText = findViewById(R.id.signup_confirm_password_edittext);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        btnSignUp.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(SignUpActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else if (password.length() < 6) {
                Toast.makeText(SignUpActivity.this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(SignUpActivity.this, "Mật khẩu xác nhận không đúng", Toast.LENGTH_SHORT).show();
            } else {
                if (!dbHelper.checkUsername(username)) {
                    boolean inserted = dbHelper.insertUser(username, password); // ✅ Đã sửa tên hàm chính xác
                    if (inserted) {
                        Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
