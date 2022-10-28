package com.example.itis5280_project8;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itis5280_project8.apicalls.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemsRecyclerViewAdapter extends RecyclerView.Adapter<ItemsRecyclerViewAdapter.ContactsViewHolder> {
    List<Item> items;

    public ItemsRecyclerViewAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_item, parent, false);
        ContactsViewHolder viewHolder = new ContactsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        Item item = items.get(position);
        holder.item = item;

        holder.textViewItemName.setText(item.getName());
        holder.textViewItemPrice.setText("$" + item.getPrice());
        holder.textViewItemDiscountedPrice.setText("$" + item.getDiscountedPrice());
        holder.textViewItemDiscount.setText(item.getDiscount() + "%");

        String url = item.getPhoto();

        Picasso.get()
                .load(url)
                .placeholder(R.drawable.ic_outline_downloading_24)
                .into(holder.imageViewItemPhoto);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ContactsViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewItemPhoto;
        TextView textViewItemName;
        TextView textViewItemPrice;
        TextView textViewItemDiscount;
        TextView textViewItemDiscountedPrice;

        int position;
        Item item;

        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewItemPhoto = itemView.findViewById(R.id.imageViewItemPhoto);
            textViewItemName = itemView.findViewById(R.id.textViewItemName);
            textViewItemPrice = itemView.findViewById(R.id.textViewItemPrice);
            textViewItemDiscount = itemView.findViewById(R.id.textViewItemDiscount);
            textViewItemDiscountedPrice = itemView.findViewById(R.id.textViewItemDiscountedPrice);
        }
    }

}