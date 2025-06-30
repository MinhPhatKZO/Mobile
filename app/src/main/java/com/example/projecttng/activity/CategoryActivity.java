package com.example.projecttng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttng.R;
import com.example.projecttng.adapter.CategoryFoodAdapter;
import com.example.projecttng.dao.FoodDao;
import com.example.projecttng.model.FoodItem;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView rvCategoryItems;
    private CategoryFoodAdapter foodAdapter;
    private List<FoodItem> allFoodList;
    private List<FoodItem> displayList;

    private ChipGroup chipGroupFilter;
    private Chip chipAll, chipFood, chipDrink;
    private TextView tvCategoryTitle, tvCategoryDescription, tvItemCount;

    private FoodDao foodDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        foodDao = new FoodDao(this);
        foodDao.insertSampleFoodsIfEmpty(); // ✅ Chỉ chèn mẫu nếu chưa có dữ liệu

        initViews();
        loadCategoryDataFromDB();
        setupRecyclerView();
        setupFilterListeners();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
    }

    private void initViews() {
        rvCategoryItems = findViewById(R.id.rv_category_items);
        chipGroupFilter = findViewById(R.id.chip_group_filter);
        chipAll = findViewById(R.id.chip_all);
        chipFood = findViewById(R.id.chip_food);
        chipDrink = findViewById(R.id.chip_drink);
        tvCategoryTitle = findViewById(R.id.tv_category_title);
        tvCategoryDescription = findViewById(R.id.tv_category_description);
        tvItemCount = findViewById(R.id.tv_item_count);

        findViewById(R.id.btn_filter_category).setOnClickListener(v -> {
            chipGroupFilter.setVisibility(
                    chipGroupFilter.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE
            );
        });
    }

    private void loadCategoryDataFromDB() {
        allFoodList = foodDao.getAllFoods(); // 👉 Lấy từ SQLite
        displayList = new ArrayList<>(allFoodList);
    }

    private void setupRecyclerView() {
        foodAdapter = new CategoryFoodAdapter(this, displayList, () -> {
            // 👉 Khi người dùng nhấn "Thêm", chuyển sang ShoppingCartActivity
            startActivity(new Intent(CategoryActivity.this, ShoppingCartActivity.class));
            overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        });

        rvCategoryItems.setLayoutManager(new LinearLayoutManager(this));
        rvCategoryItems.setAdapter(foodAdapter);

        updateCategoryInfo("Tất cả", "Tất cả món ăn và đồ uống", displayList.size());
    }

    private void setupFilterListeners() {
        chipGroupFilter.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.chip_all) {
                filterAll();
            } else if (checkedId == R.id.chip_food) {
                filterByType(FoodItem.FoodType.FOOD);
            } else if (checkedId == R.id.chip_drink) {
                filterByType(FoodItem.FoodType.DRINK);
            }
        });
    }

    private void filterAll() {
        displayList.clear();
        displayList.addAll(allFoodList);
        foodAdapter.notifyDataSetChanged();
        updateCategoryInfo("Tất cả", "Tất cả món ăn và đồ uống", displayList.size());
    }

    private void filterByType(FoodItem.FoodType type) {
        displayList.clear();
        for (FoodItem item : allFoodList) {
            if (item.getType() == type) {
                displayList.add(item);
            }
        }
        foodAdapter.notifyDataSetChanged();

        if (type == FoodItem.FoodType.FOOD) {
            updateCategoryInfo("Đồ ăn", "Các món ăn nhanh phổ biến", displayList.size());
        } else {
            updateCategoryInfo("Đồ uống", "Các loại đồ uống giải khát", displayList.size());
        }
    }

    private void updateCategoryInfo(String title, String description, int itemCount) {
        tvCategoryTitle.setText("Danh mục: " + title);
        tvCategoryDescription.setText(description);
        tvItemCount.setText(itemCount + " món");
    }
}
