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
import com.example.foodapp.databinding.ItemMealBinding;
import com.example.foodapp.databinding.ItemPopularBinding;
import com.example.foodapp.listener.MealListener;
import com.example.foodapp.model.Meals;

import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.MyViewHolder> {

    List<Meals> list;
    MealListener listener;

    public MealsAdapter(List<Meals> list, MealListener listener) {
        this.list = list;
        this.listener = listener;
    }
    public void UpdateList(List<Meals> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMealBinding binding = ItemMealBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setPupolar(list.get(position));
        Glide.with(holder.itemView)
                .load(list.get(position).getStrMealThumb())
                .override(220,200)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .downsample(DownsampleStrategy.AT_LEAST)
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.logofood)
                .into(holder.binding.imgPup);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ItemMealBinding binding;
        public MyViewHolder(ItemMealBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void setPupolar(Meals meals){
            binding.setMealname(meals);
            binding.executePendingBindings();
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMeallistener(meals);
                }
            });
        }
    }
}
