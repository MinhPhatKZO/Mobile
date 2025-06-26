package com.example.projecttng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttng.R;

public class ShoppingCartActivity extends AppCompatActivity {

    private ImageView btnBack;
    private TextView tvTitle, tvAddress, tvContact, tvTotalPrice;
    private Button btnAddMore, btnCheckout;
    private RadioGroup rgPayment;
    private RadioButton rbCod, rbMomo, rbVnpay;
    private RecyclerView rvCartItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcart); // Đổi tên XML nếu cần

        // Ánh xạ view
        btnBack = findViewById(R.id.btn_back);
        tvTitle = findViewById(R.id.tv_title);
        tvAddress = findViewById(R.id.tv_address);
        tvContact = findViewById(R.id.tv_contact);
        tvTotalPrice = findViewById(R.id.tv_total_price);
        btnAddMore = findViewById(R.id.btn_add_more);
        btnCheckout = findViewById(R.id.btn_checkout);
        rgPayment = findViewById(R.id.rg_payment);
        rbCod = findViewById(R.id.rb_cod);
        rbMomo = findViewById(R.id.rb_momo);
        rbVnpay = findViewById(R.id.rb_vnpay);
        rvCartItems = findViewById(R.id.rv_cart_items);

        // Sự kiện nút quay lại
        btnBack.setOnClickListener(view -> finish());

        // Sự kiện nút chọn thêm món
        btnAddMore.setOnClickListener(view -> {
            // Gọi về màn hình chọn món (ví dụ: MainActivity)
            Intent intent = new Intent(this, CategoryActivity.class);
            startActivity(intent);
        });

        // Sự kiện thanh toán
        btnCheckout.setOnClickListener(view -> {
            int selectedId = rgPayment.getCheckedRadioButtonId();

            if (selectedId == -1) {
                Toast.makeText(this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }

            // Có thể xử lý tuỳ từng phương thức ở đây
            if (selectedId == R.id.rb_cod) {
                Toast.makeText(this, "Bạn đã chọn COD", Toast.LENGTH_SHORT).show();
            } else if (selectedId == R.id.rb_momo) {
                Toast.makeText(this, "Bạn đã chọn thanh toán Momo", Toast.LENGTH_SHORT).show();
            } else if (selectedId == R.id.rb_vnpay) {
                Toast.makeText(this, "Bạn đã chọn thanh toán VNPay", Toast.LENGTH_SHORT).show();
            }

            // Chuyển sang màn hình "Đặt hàng thành công"
            Intent intent = new Intent(this, OrderActivity.class);
            startActivity(intent);
        });
    }
}
