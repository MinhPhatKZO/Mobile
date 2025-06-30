package com.example.projecttng.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projecttng.R;
import com.example.projecttng.dao.UserDao;

import java.util.List;

public class ManageUsersActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;
    private UserDao userDao;
    private List<String> userListWithRoles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        listView = findViewById(R.id.list_users);
        userDao = new UserDao(this);

        loadUsers(); // ✅ Load user ban đầu

        // Nhấn giữ để xóa
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            String selectedItem = userListWithRoles.get(position);
            String usernameToDelete = selectedItem.split(" ")[0]; // "username (role)" → lấy username

            if ("admin".equalsIgnoreCase(usernameToDelete)) {
                Toast.makeText(this, "Không thể xóa tài khoản admin", Toast.LENGTH_SHORT).show();
                return true;
            }

            userDao.deleteUser(usernameToDelete);
            Toast.makeText(this, "Đã xóa: " + usernameToDelete, Toast.LENGTH_SHORT).show();

            loadUsers(); // ✅ Refresh danh sách
            return true;
        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = userListWithRoles.get(position);
            String username = selectedItem.split(" ")[0];

            // Lấy password
            String password = userDao.getPasswordByUsername(username);

            // Hiển thị thông tin bằng Toast (hoặc AlertDialog nếu muốn đẹp hơn)
            Toast.makeText(this, "Tài khoản: " + username + "\nMật khẩu: " + password, Toast.LENGTH_LONG).show();
        });

    }

    // ✅ Tải lại danh sách người dùng
    private void loadUsers() {
        userListWithRoles = userDao.getAllUsers(); // trả về "username (role)"
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userListWithRoles);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUsers(); // ✅ Tự cập nhật khi quay lại
    }
}
