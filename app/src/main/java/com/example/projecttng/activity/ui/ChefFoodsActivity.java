package com.example.projecttng.activity.ui;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.projecttng.R;
import com.example.projecttng.adapter.FoodAdapter;
import com.example.projecttng.dao.FoodDao;
import com.example.projecttng.model.FoodItem;
import java.util.List;

public class ChefFoodsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private FoodDao foodDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_foods);

        recyclerView = findViewById(R.id.rv_chef_foods);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        foodDao = new FoodDao(this);

        int chefId = getIntent().getIntExtra("chefId", -1);
        String chefName = getIntent().getStringExtra("chefName");

        TextView title = findViewById(R.id.tv_chef_title);
        title.setText("Món ăn của " + chefName);

        List<FoodItem> list = foodDao.getFoodsByChef(chefId);
        foodAdapter = new FoodAdapter(this, list);
        recyclerView.setAdapter(foodAdapter);
    }
}
