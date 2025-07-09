package com.example.projecttng.activity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.projecttng.R;
import com.example.projecttng.activity.sp.HelpCenterActivity;
import com.example.projecttng.activity.auth.SignInActivity;

public class UserProfileActivity extends AppCompatActivity {

    LinearLayout btnProfile, btnNotificationSetting, btnHelpCenter, btnChangeBackground, btnLanguage;
    LinearLayout layoutUserProfile;
    boolean isBackgroundChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Ánh xạ các view
        btnProfile = findViewById(R.id.btnProfile);
        btnNotificationSetting = findViewById(R.id.btnNotificationSetting);
        btnHelpCenter = findViewById(R.id.btnHelpCenter);
        btnChangeBackground = findViewById(R.id.btnChangeBackground);
        btnLanguage = findViewById(R.id.btnLanguage);
        layoutUserProfile = findViewById(R.id.layoutUserProfile); // Layout tổng cần đổi nền
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnSwitchAccount = findViewById(R.id.btnSwitchAccount);


        // Sự kiện bấm vào các nút
        btnProfile.setOnClickListener(v ->
                startActivity(new Intent(this, EditProfileActivity.class)));

        btnNotificationSetting.setOnClickListener(v ->
                startActivity(new Intent(this, NotificationSettingActivity.class)));

        btnHelpCenter.setOnClickListener(v ->
                startActivity(new Intent(this, HelpCenterActivity.class)));

        btnChangeBackground.setOnClickListener(v -> {
            if (!isBackgroundChanged) {
                layoutUserProfile.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_new));
            } else {
                layoutUserProfile.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
            }
            isBackgroundChanged = !isBackgroundChanged;
        });
        btnLogout.setOnClickListener(v -> {
            // TODO: Thực hiện xóa session, sharedPreferences,... nếu có
            // Sau đó chuyển về màn hình đăng nhập
            Intent intent = new Intent(UserProfileActivity.this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa hết các activity cũ
            startActivity(intent);
        });

        btnSwitchAccount.setOnClickListener(v -> {
            // TODO: Thực hiện logout session hiện tại
            // Rồi chuyển đến màn hình chọn tài khoản, hoặc đăng nhập lại
            Intent intent = new Intent(UserProfileActivity.this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

    }
}
