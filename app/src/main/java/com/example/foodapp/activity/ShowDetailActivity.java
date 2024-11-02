package com.example.foodapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foodapp.R;
import com.example.foodapp.databinding.ActivityShowDetailBinding;
import com.example.foodapp.model.Cart;
import com.example.foodapp.model.MealDetail;
import com.example.foodapp.retrofit.ResponseMealDetail;
import com.example.foodapp.untils.Utils;
import com.example.foodapp.viewModel.ShowDetailViewModel;

import java.util.List;

import io.paperdb.Paper;

public class ShowDetailActivity extends AppCompatActivity {

    ShowDetailViewModel viewModel;

    ActivityShowDetailBinding binding;
    int amount =0;
    Intent intent;

    MealDetail mealDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_show_detail);
        Paper.init(this);
        intent = getIntent();
        int id = getIntent().getIntExtra("idmeals",0);
        getData(id);
        eventClick();
        showData(id);
    }

    private void showData(int id) {
        if(Paper.book().read("cart")!=null){
            List<Cart> mlist =Paper.book().read("cart");
            Utils.cartList = mlist;
        }
        if(Utils.cartList!=null){
            if(Utils.cartList.size()>0){
                for (int i = 0; i< Utils.cartList.size(); i++){
                    if(Utils.cartList.get(i).getMealDetail().getId() == id){
                        binding.tvCount.setText(Utils.cartList.get(i).getAmount()+"");
                    }
                }
            }else{
                binding.tvCount.setText(amount+"");
            }
        }
    }

    private void eventClick() {
        binding.imgUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount = Integer.parseInt(binding.tvCount.getText().toString()) +1;
                binding.tvCount.setText(amount+"");
            }
        });
        binding.imgDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(binding.tvCount.getText().toString()) > 1){
                    amount = Integer.parseInt(binding.tvCount.getText().toString()) - 1;
                    binding.tvCount.setText(amount+"");
                }
            }
        });
        binding.btnAddtocard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCard(amount);
            }
        });
    }

    private void addToCard(int amount) {
        boolean checkExit = false;
        int n=0;
        if(Utils.cartList.size()>0){
            for (int i = 0; i< Utils.cartList.size(); i++){
                if(Utils.cartList.get(i).getMealDetail().getId() == mealDetail.getId()){
                    checkExit = true;
                    n = i;
                    break;
                }
            }
        }
        if(checkExit){
            Utils.cartList.get(n).setAmount(amount);
        }else{
            Cart cart = new Cart();
            cart.setMealDetail(mealDetail);
            cart.setAmount(amount);
            Utils.cartList.add(cart);
        }
        Toast.makeText(this,"Add to card success",Toast.LENGTH_SHORT).show();
        Paper.book().write("cart", Utils.cartList);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    private void getData(int id) {
        viewModel = new ViewModelProvider(this).get(ShowDetailViewModel.class);
        viewModel.detailMutableLiveData(id).observe(this, new Observer<ResponseMealDetail>() {
            @Override
            public void onChanged(ResponseMealDetail responseMealDetail) {
                if(responseMealDetail.isSuccess()){
                    mealDetail = responseMealDetail.getResult().get(0);
                    binding.tvName.setText(mealDetail.getMeal());
                    binding.tvDesp.setText(mealDetail.getInstructions());
                    binding.tvGiatien.setText("$"+mealDetail.getPrice());
                    Glide.with(ShowDetailActivity.this).load(mealDetail.getStrmealthumb()).into(binding.imgMeal);
                }
            }
        });
    }
}