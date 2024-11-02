package com.example.foodapp.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.foodapp.retrofit.FoodApi;
import com.example.foodapp.retrofit.ResponseCategory;
import com.example.foodapp.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private FoodApi foodApi;

    public CategoryRepository (){
        foodApi = RetrofitInstance.getRetrofit().create(FoodApi.class);
    }
    public MutableLiveData<ResponseCategory> getCategory(){
        MutableLiveData<ResponseCategory> data = new MutableLiveData<>();
        foodApi.getCaterogy().enqueue(new Callback<ResponseCategory>() {
            @Override
            public void onResponse(Call<ResponseCategory> call, Response<ResponseCategory> response) {
                    data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponseCategory> call, Throwable throwable) {
                Log.d("logg",throwable.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }

}
