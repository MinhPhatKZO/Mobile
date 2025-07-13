package com.example.projecttng.activity.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projecttng.R;
import com.example.projecttng.model.CreateOrder;

import org.json.JSONObject;

public class OrderZaloPayActivity extends AppCompatActivity {

    private WebView webView;
    private static final String TAG = "ZaloPay";

    private int orderId;
    private int amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);
        setContentView(R.layout.activity_order_zalopay);

        webView = findViewById(R.id.webview_zalopay);

        // Nhận dữ liệu từ ShoppingCartActivity
        orderId = getIntent().getIntExtra("orderId", -1);
        amount = getIntent().getIntExtra("amount", 10000); // fallback mặc định

        if (orderId == -1) {
            Toast.makeText(this, "Không tìm thấy đơn hàng!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Tạo đơn hàng và hiển thị giao diện ZaloPay
        createZaloPayOrder();
    }

    private void createZaloPayOrder() {
        new Thread(() -> {
            try {
                CreateOrder orderApi = new CreateOrder();
                JSONObject order = orderApi.createOrder(String.valueOf(amount));
                String code = order.optString("return_code", "");

                runOnUiThread(() -> {
                    if ("1".equals(code)) {
                        String orderUrl = order.optString("order_url", "");
                        if (orderUrl.isEmpty()) {
                            Toast.makeText(this, "Không lấy được liên kết thanh toán!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        webView.getSettings().setJavaScriptEnabled(true);

                        webView.setWebViewClient(new WebViewClient() {
                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                Log.d("ZaloPay", "URL: " + url);

                                if (url.startsWith("http") || url.startsWith("https")) {
                                    return false; // WebView tự xử lý
                                }

                                try {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                    startActivity(intent);
                                } catch (Exception e) {
                                    Toast.makeText(OrderZaloPayActivity.this, "Không thể mở liên kết ZaloPay!", Toast.LENGTH_SHORT).show();
                                }
                                return true;
                            }
                        });

                        webView.loadUrl(orderUrl);

                    } else {
                        Toast.makeText(this, "Tạo đơn hàng ZaloPay thất bại!", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Lỗi tạo đơn hàng: " + order.toString());
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(this, "Đã xảy ra lỗi khi tạo đơn hàng!", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Exception: " + e.getMessage());
                });
            }
        }).start();
    }

}
