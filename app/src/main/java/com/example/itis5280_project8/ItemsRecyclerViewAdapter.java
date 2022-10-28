package com.example.itis5280_project8;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.itis5280_project8.apicalls.Item;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;

import okhttp3.Request;
import okhttp3.Response;

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

        Picasso.Builder pBuilder = new Picasso.Builder(holder.imageViewItemPhoto.getContext());
        RequestCreator pRequestor = pBuilder.build().load(url);
        pBuilder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                exception.printStackTrace();
            }
        });

        pRequestor.placeholder(R.drawable.ic_outline_downloading_24)
                .into(holder.imageViewItemPhoto, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("ImageLoad", "sucess " + url);
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("ImageLoad", e.getMessage() + " " + url);
                        e.printStackTrace();
                    }
                });

                pRequestor.fetch();
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