package com.techbay.localpos.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techbay.localpos.R;
import com.techbay.localpos.data.LocalDatabaseHelper;

import java.util.List;
import java.util.Locale;

public class ProductBoxAdapter extends RecyclerView.Adapter<ProductBoxAdapter.BoxViewHolder> {

    public interface OnProductBoxClickListener {
        void onProductClick(LocalDatabaseHelper.Product product);
    }

    private final List<LocalDatabaseHelper.Product> items;
    private final OnProductBoxClickListener listener;

    public ProductBoxAdapter(List<LocalDatabaseHelper.Product> items, OnProductBoxClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public void updateItems(List<LocalDatabaseHelper.Product> newItems) {
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BoxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_box, parent, false);
        return new BoxViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoxViewHolder holder, int position) {
        LocalDatabaseHelper.Product p = items.get(position);
        holder.tvName.setText(p.name);
        holder.tvPrice.setText(String.format(Locale.US, "Sale: %.2f | Buy: %.2f", p.salePrice, p.purchasePrice));
        holder.tvStock.setText(String.format(Locale.US, "Stock: %d", p.stock));
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onProductClick(p);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class BoxViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvStock;

        BoxViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvBoxProductName);
            tvPrice = itemView.findViewById(R.id.tvBoxProductPrice);
            tvStock = itemView.findViewById(R.id.tvBoxProductStock);
        }
    }
}
