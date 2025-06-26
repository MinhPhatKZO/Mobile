package com.example.projecttng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttng.model.FoodItem;
import com.example.projecttng.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    public interface OnCartChangedListener {
        void onCartChanged();
    }

    private Context context;
    private List<FoodItem> cartItems;
    private OnCartChangedListener listener;

    public CartAdapter(Context context, List<FoodItem> cartItems, OnCartChangedListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        FoodItem currentItem = cartItems.get(position);

        holder.tvName.setText(currentItem.getName());
        holder.tvQuantity.setText(String.valueOf(currentItem.getQuantity()));
        holder.tvPrice.setText(formatCurrency(currentItem.getParsedPrice() * currentItem.getQuantity()));
        holder.imgFood.setImageResource(currentItem.getImageResId());

        // Nút cộng
        holder.btnPlus.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;

            FoodItem item = cartItems.get(pos);
            item.setQuantity(item.getQuantity() + 1);
            notifyItemChanged(pos);
            listener.onCartChanged();
        });

        // Nút trừ (xoá nếu = 0)
        holder.btnMinus.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;

            FoodItem item = cartItems.get(pos);
            int newQuantity = item.getQuantity() - 1;

            if (newQuantity > 0) {
                item.setQuantity(newQuantity);
                notifyItemChanged(pos);
            } else {
                CartManager.removeItem(item.getId());
                cartItems.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, cartItems.size());
            }

            listener.onCartChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvQuantity;
        ImageView imgFood, btnPlus, btnMinus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_food_name_cart);
            tvPrice = itemView.findViewById(R.id.tv_product_price);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            imgFood = itemView.findViewById(R.id.img_food_cart);
            btnPlus = itemView.findViewById(R.id.btn_plus);
            btnMinus = itemView.findViewById(R.id.btn_minus);
        }
    }

    private String formatCurrency(int amount) {
        return String.format("%,d đ", amount).replace(",", ".");
    }
}
