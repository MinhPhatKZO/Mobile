package com.example.projecttng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projecttng.R;
import com.example.projecttng.model.FoodItem;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    private final Context context;
    private final List<FoodItem> orderItemList;

    public OrderDetailAdapter(Context context, List<FoodItem> orderItemList) {
        this.context = context;
        this.orderItemList = orderItemList;
    }

    @NonNull
    @Override
    public OrderDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.ViewHolder holder, int position) {
        FoodItem item = orderItemList.get(position);

        holder.tvName.setText(item.getName());
        holder.tvPrice.setText(item.getFormattedPrice());
        holder.tvQuantity.setText("x" + item.getQuantity());

        // Hiển thị ảnh theo resource id (nếu dùng từ drawable)
        holder.imgFood.setImageResource(item.getImageResId());

        // Nếu dùng ảnh URL thay vì resource, thay thế bằng:
        // Glide.with(context).load(item.getImage()).into(holder.imgFood);
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFood;
        TextView tvName, tvPrice, tvQuantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFood = itemView.findViewById(R.id.img_food);
            tvName = itemView.findViewById(R.id.tv_food_name);
            tvPrice = itemView.findViewById(R.id.tv_food_price);
            tvQuantity = itemView.findViewById(R.id.tv_food_quantity);
        }
    }
}
