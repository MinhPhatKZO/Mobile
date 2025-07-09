package com.example.projecttng.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttng.R;
import com.example.projecttng.dao.CartDao;
import com.example.projecttng.model.FoodItem;

import java.util.List;

public class CategoryFoodAdapter extends RecyclerView.Adapter<CategoryFoodAdapter.ViewHolder> {

    public interface OnAddToCartListener {
        void onAdd(); // Callback để Activity xử lý khi thêm món
    }

    private final List<FoodItem> foodItems;
    private final Context context;
    private final CartDao cartDao;
    private final OnAddToCartListener listener;

    public CategoryFoodAdapter(Context context, List<FoodItem> foodItems, OnAddToCartListener listener) {
        this.context = context;
        this.foodItems = foodItems;
        this.cartDao = new CartDao(context);
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView tvFoodName, tvPrice, tvSold, tvLikes;
        ImageButton btnAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_food);
            tvFoodName = itemView.findViewById(R.id.tv_food_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvSold = itemView.findViewById(R.id.tv_sold);
            tvLikes = itemView.findViewById(R.id.tv_likes);
            btnAdd = itemView.findViewById(R.id.btn_add);
        }
    }

    @NonNull
    @Override
    public CategoryFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryFoodAdapter.ViewHolder holder, int position) {
        FoodItem item = foodItems.get(position);

        holder.tvFoodName.setText(item.getName());
        holder.tvPrice.setText(item.getFormattedPrice());
        holder.imgFood.setImageResource(item.getImageResId());

        if (holder.tvSold != null) {
            holder.tvSold.setText(item.getSoldCount() + " đã bán");
        }

        if (holder.tvLikes != null) {
            holder.tvLikes.setText(item.getLikeCount() + " thích");
        }

        View.OnClickListener addToCartListener = v -> {
            FoodItem selectedItem = new FoodItem(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getCalories(),
                    item.getPrice(),
                    item.getTime(),
                    item.getImageResId(),
                    item.getSoldCount(),
                    item.getLikeCount(),
                    item.getRating(),
                    item.getType()
            );
            selectedItem.setQuantity(1);

            // ✅ Lấy userId từ SharedPreferences
            SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            int userId = prefs.getInt("userId", -1);

            if (userId != -1) {
                cartDao.addOrUpdateItem(userId, selectedItem);
                Toast.makeText(context, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                if (listener != null) listener.onAdd();
            } else {
                Toast.makeText(context, "Lỗi: Chưa đăng nhập", Toast.LENGTH_SHORT).show();
            }
        };

        holder.btnAdd.setOnClickListener(addToCartListener);
        holder.itemView.setOnClickListener(addToCartListener);
    }

    @Override
    public int getItemCount() {
        return foodItems != null ? foodItems.size() : 0;
    }
}
