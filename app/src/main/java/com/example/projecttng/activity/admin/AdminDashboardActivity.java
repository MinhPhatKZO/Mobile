package com.example.projecttng.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projecttng.R;
import com.example.projecttng.activity.auth.SignInActivity;

public class AdminDashboardActivity extends AppCompatActivity {

    private CardView cardManageUsers, cardManageFoods, cardLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        cardManageUsers = findViewById(R.id.card_manage_users);
        cardManageFoods = findViewById(R.id.card_manage_foods);
        cardLogout = findViewById(R.id.card_logout_admin);

        cardManageUsers.setOnClickListener(v ->
                startActivity(new Intent(this, ManageUsersActivity.class))
        );

        cardManageFoods.setOnClickListener(v ->
                startActivity(new Intent(this, ManageFoodsActivity.class))
        );

        cardLogout.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
