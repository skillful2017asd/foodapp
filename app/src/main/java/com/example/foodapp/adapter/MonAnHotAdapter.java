package com.example.foodapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ItemMonanhotBinding;
import com.example.foodapp.model.MonAnHot;

import java.util.List;

public class MonAnHotAdapter extends RecyclerView.Adapter<MonAnHotAdapter.MyViewHolder> {
    private List<MonAnHot> list;

    public MonAnHotAdapter(List<MonAnHot> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMonanhotBinding binding = ItemMonanhotBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MonAnHot monAnHot = list.get(position);
        holder.setMonAn(monAnHot);
        Glide.with(holder.itemView.getContext())
                .load(monAnHot.getAnh())
                .override(220,200)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .fitCenter()
                .downsample(DownsampleStrategy.AT_LEAST)
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.logofood)
                .into(holder.binding.imganhhot);
    }

    @Override
    public int getItemCount() {
        if(list==null){
            return 0;
        }
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ItemMonanhotBinding binding;
        public MyViewHolder(ItemMonanhotBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void setMonAn(MonAnHot monAn){
            binding.setMonanhot(monAn);
            binding.executePendingBindings();
        }
    }
}
