package com.example.projecttng.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projecttng.R;
import com.example.projecttng.dao.FoodDao;
import com.example.projecttng.model.FoodItem;

public class AddFoodActivity extends AppCompatActivity {

    private EditText etName, etDescription, etCalories, etPrice, etTime;
    private Spinner spinnerType;
    private Button btnAdd;
    private FoodDao foodDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        foodDao = new FoodDao(this);

        etName = findViewById(R.id.et_name);
        etDescription = findViewById(R.id.et_description);
        etCalories = findViewById(R.id.et_calories);
        etPrice = findViewById(R.id.et_price);
        etTime = findViewById(R.id.et_time);
        spinnerType = findViewById(R.id.spinner_type);
        btnAdd = findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String desc = etDescription.getText().toString().trim();
            String cal = etCalories.getText().toString().trim();
            String price = etPrice.getText().toString().trim();
            String time = etTime.getText().toString().trim();
            String typeStr = spinnerType.getSelectedItem().toString();

            if (name.isEmpty() || price.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tên và giá", Toast.LENGTH_SHORT).show();
                return;
            }

            FoodItem.FoodType type = FoodItem.FoodType.FOOD;
            if (typeStr.equals("Đồ uống")) {
                type = FoodItem.FoodType.DRINK;
            } else if (typeStr.equals("Tráng miệng")) {
                type = FoodItem.FoodType.DESSERT;
            }

            FoodItem food = new FoodItem(name, desc, cal, price, time,
                    R.drawable.ic_launcher_foreground,
                    0, 0, 0.0f, type);

            foodDao.insertFood(food);

            Toast.makeText(this, "Đã thêm món ăn", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
