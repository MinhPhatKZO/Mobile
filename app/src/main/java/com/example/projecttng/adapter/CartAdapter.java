package com.example.projecttng.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecttng.R;
import com.example.projecttng.dao.CartDao;
import com.example.projecttng.model.FoodItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    public interface OnCartChangedListener {
        void onCartChanged();
    }

    private final Context context;
    private final List<FoodItem> cartItems;
    private final OnCartChangedListener listener;
    private final CartDao cartDao;

    public CartAdapter(Context context, List<FoodItem> cartItems, OnCartChangedListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
        this.cartDao = new CartDao(context);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        FoodItem item = cartItems.get(position);

        holder.tvName.setText(item.getName());
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));
        holder.tvPrice.setText(formatCurrency(item.getParsedPrice() * item.getQuantity()));
        holder.imgFood.setImageResource(item.getImageResId());

        // ➕ Tăng số lượng
        holder.btnPlus.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;

            FoodItem food = cartItems.get(pos);
            int newQty = food.getQuantity() + 1;
            food.setQuantity(newQty);

            cartDao.updateQuantityByFoodId(food.getId(), newQty);
            notifyItemChanged(pos);
            listener.onCartChanged();
        });

        // ➖ Giảm số lượng
        holder.btnMinus.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;

            FoodItem food = cartItems.get(pos);
            int newQty = food.getQuantity() - 1;

            if (newQty > 0) {
                food.setQuantity(newQty);
                cartDao.updateQuantityByFoodId(food.getId(), newQty);
                notifyItemChanged(pos);
            } else {
                cartDao.removeFromCart(food.getId());
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

    private String formatCurrency(int amount) {
        return String.format("%,d đ", amount).replace(",", ".");
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
}
