package com.example.foodapp.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.foodapp.retrofit.FoodApi;
import com.example.foodapp.retrofit.ResponseMealDetail;
import com.example.foodapp.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealDetailRepository {

    FoodApi foodApi;

    public MealDetailRepository() {
        this.foodApi = RetrofitInstance.getRetrofit().create(FoodApi.class);
    }

    public MutableLiveData<ResponseMealDetail> detailMutableLiveData (int id){
        MutableLiveData<ResponseMealDetail> data = new MutableLiveData<>();
        foodApi.getMealDetail(id).enqueue(new Callback<ResponseMealDetail>() {
            @Override
            public void onResponse(Call<ResponseMealDetail> call, Response<ResponseMealDetail> response) {
                  data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponseMealDetail> call, Throwable throwable) {
                    data.setValue(null);
            }
        });
        return data;
    }
}
