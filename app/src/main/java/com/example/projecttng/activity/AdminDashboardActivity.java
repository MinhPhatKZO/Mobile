package com.example.projecttng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projecttng.R;

public class AdminDashboardActivity extends AppCompatActivity {

    private Button btnManageUsers, btnManageFoods, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        btnManageUsers = findViewById(R.id.btn_manage_users);
        btnManageFoods = findViewById(R.id.btn_manage_foods);
        btnLogout = findViewById(R.id.btn_logout_admin);

        btnManageUsers.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageUsersActivity.class));
        });

        btnManageFoods.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageFoodsActivity.class));
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
