package com.example.projecttng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttng.R;
import com.example.projecttng.adapter.ManageFoodAdapter;
import com.example.projecttng.dao.FoodDao;
import com.example.projecttng.model.FoodItem;

import java.util.List;

public class ManageFoodsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ManageFoodAdapter adapter;
    private FoodDao foodDao;
    private List<FoodItem> foodList;
    private Button btnAddFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_foods);

        // Ánh xạ view
        recyclerView = findViewById(R.id.recycler_foods);
        btnAddFood = findViewById(R.id.btn_add_food);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo DAO và lấy danh sách món ăn
        foodDao = new FoodDao(this);
        foodList = foodDao.getAllFoods();

        // Adapter hiển thị danh sách và xử lý sửa/xóa
        adapter = new ManageFoodAdapter(foodList, new ManageFoodAdapter.OnItemClickListener() {
            @Override
            public void onEdit(FoodItem foodItem) {
                Intent intent = new Intent(ManageFoodsActivity.this, com.example.projecttng.activity.EditFoodActivity.class);
                intent.putExtra("food", foodItem);
                startActivity(intent);
            }

            @Override
            public void onDelete(FoodItem foodItem) {
                foodDao.deleteFood(foodItem.getId());
                foodList.remove(foodItem);
                adapter.notifyDataSetChanged();
                Toast.makeText(ManageFoodsActivity.this, "Đã xóa " + foodItem.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(adapter);

        // Xử lý khi bấm nút thêm món ăn
        btnAddFood.setOnClickListener(v -> {
            Intent intent = new Intent(ManageFoodsActivity.this, com.example.projecttng.activity.AddFoodActivity.class);
            startActivity(intent);
        });
    }

    // Tự động load lại danh sách khi quay lại Activity
    @Override
    protected void onResume() {
        super.onResume();
        foodList.clear();
        foodList.addAll(foodDao.getAllFoods());
        adapter.notifyDataSetChanged();
    }
}
