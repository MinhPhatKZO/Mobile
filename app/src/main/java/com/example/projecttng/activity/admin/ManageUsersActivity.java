package com.example.projecttng.activity.admin;

import android.os.Bundle;
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
        loadUsers();

        // Long click để xóa
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            String sel = userListWithRoles.get(position);
            String username = sel.split(" ")[0];
            if ("admin".equalsIgnoreCase(username)) {
                Toast.makeText(this, "Không thể xóa tài khoản admin", Toast.LENGTH_SHORT).show();
            } else {
                userDao.deleteUser(username);
                Toast.makeText(this, "Đã xóa: " + username, Toast.LENGTH_SHORT).show();
                loadUsers();
            }
            return true;
        });

        // Click để xem mật khẩu
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String sel = userListWithRoles.get(position);
            String username = sel.split(" ")[0];
            String pw = userDao.getPasswordByUsername(username);  // lấy id
            Toast.makeText(this, "Tài khoản: " + username + "\nMật khẩu: " + pw, Toast.LENGTH_LONG).show();
        });
    }

    private void loadUsers() {
        userListWithRoles = userDao.getAllUsers(); // Chuỗi "username (role)"
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userListWithRoles);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUsers();
    }
}
