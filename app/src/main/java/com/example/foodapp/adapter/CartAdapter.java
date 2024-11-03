package com.example.foodapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodapp.databinding.ItemCartBinding;
import com.example.foodapp.listener.CartListener;
import com.example.foodapp.model.Cart;
import com.example.foodapp.model.MealDetail;
import com.example.foodapp.untils.Utils;

import java.util.List;

import io.paperdb.Paper;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHodel>{

    private List<Cart> list;
    private Context context;

    private CartListener cartListener;
    String userid;

    public CartAdapter(List<Cart> list, Context context, CartListener cartListener, String userId) {
        this.list = list;
        this.context = context;
        this.cartListener = cartListener;
        this.userid = userId;

    }

    @NonNull
    @Override
    public MyViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding binding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHodel(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHodel holder, int position) {
        Cart cart = list.get(position);
        holder.binding.textView.setText(cart.getMealDetail().getMeal());
        Glide.with(context).load(cart.getMealDetail().getStrmealthumb()).into(holder.binding.imgCart);
        holder.binding.textView2.setText(cart.getMealDetail().getPrice()+"");
        holder.binding.imgUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtocart(holder.getAdapterPosition());
                notifyDataSetChanged();
                cartListener.changeNumberListener();
            }
        });
        holder.binding.imgDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subtocard(holder.getAdapterPosition());
                notifyDataSetChanged();
                cartListener.changeNumberListener();
            }
        });
        holder.binding.tvCount.setText(cart.getAmount()+"");
        holder.binding.text3.setText("$"+String.valueOf(cart.getAmount()* cart.getMealDetail().getPrice()));
    }

    private void subtocard(int adapterPosition) {
        if(Utils.cartList.get(adapterPosition).getAmount()==1){
            Utils.cartList.remove(adapterPosition);
        }else {
            Utils.cartList.get(adapterPosition).setAmount(Utils.cartList.get(adapterPosition).getAmount() -1);
        }
        Paper.book().write(userid+"cart",Utils.cartList);
    }

    private void addtocart(int adapterPosition) {
        Utils.cartList.get(adapterPosition).setAmount(Utils.cartList.get(adapterPosition).getAmount() +1);
        Paper.book().write(userid+"cart",Utils.cartList);
    }

    @Override
    public int getItemCount() {
        if(list == null){
            return 0;
        }
        return list.size();
    }

    public class MyViewHodel extends RecyclerView.ViewHolder {

        ItemCartBinding binding;
        public MyViewHodel(ItemCartBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
