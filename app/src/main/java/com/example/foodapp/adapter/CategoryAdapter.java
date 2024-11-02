package com.example.foodapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ItemCategoryBinding;
import com.example.foodapp.listener.CategoryListener;
import com.example.foodapp.model.Category;
import com.example.foodapp.model.Meals;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHodel>{


    List<Category> list;

    CategoryListener listener;

    public CategoryAdapter(List<Category> list, CategoryListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHodel(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodel holder, int position) {
        holder.setBinding(list.get(position));
        Glide.with(holder.itemView)
                .load(list.get(position).getCategoryThumb())
                .error(R.drawable.ic_launcher_background)
                .override(220,200)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .downsample(DownsampleStrategy.AT_LEAST)
                .into(holder.binding.itemImg);
//        holder.binding.itemImg.setOnClickListener(v -> {
//            iclickitem.getIdcaterogy(list.get(position).getId());
//        });
//        Picasso.get()
//                .load(list.get(position).getCategoryThumb())
//                .error(R.drawable.ic_launcher_background)
//                .into(holder.binding.itemImg);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHodel extends RecyclerView.ViewHolder {

        ItemCategoryBinding binding;
        public MyViewHodel(ItemCategoryBinding binding) {
            super( binding.getRoot());
            this.binding = binding;

        }
        public void setBinding(Category category){
            binding.setCategory(category);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCategoryClick(category);
                }
            });
        }
    }
}
