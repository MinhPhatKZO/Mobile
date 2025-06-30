package com.example.projecttng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projecttng.R;
import com.example.projecttng.dao.UserDao;

public class SignUpActivity extends AppCompatActivity {

    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        userDao = new UserDao(this);

        // Ánh xạ view
        ImageView btnBack = findViewById(R.id.btn_back);
        EditText usernameEditText = findViewById(R.id.signup_username_edittext);
        EditText passwordEditText = findViewById(R.id.signup_password_edittext);
        EditText confirmPasswordEditText = findViewById(R.id.signup_confirm_password_edittext);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        btnBack.setOnClickListener(v -> finish());

        btnSignUp.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            if (userDao.isUsernameExists(username)) {
                Toast.makeText(this, "Tên tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
            } else {
                boolean inserted = userDao.insertUser(username, password);
                if (inserted) {
                    Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, SignInActivity.class));
                    finish();
                } else {
                    Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
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
