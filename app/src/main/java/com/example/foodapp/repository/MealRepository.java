package com.example.foodapp.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.foodapp.retrofit.FoodApi;
import com.example.foodapp.retrofit.ResponseMeals;
import com.example.foodapp.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealRepository {
    private FoodApi foodApi;

    public MealRepository() {
       foodApi = RetrofitInstance.getRetrofit().create(FoodApi.class);
    }
    public MutableLiveData<ResponseMeals> getMeals(int idcate){
        MutableLiveData<ResponseMeals> data =  new MutableLiveData<>();
        foodApi.getMeals(idcate).enqueue(new Callback<ResponseMeals>() {
            @Override
            public void onResponse(Call<ResponseMeals> call, Response<ResponseMeals> response) {
                    data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponseMeals> call, Throwable throwable) {
                Log.d("logg",throwable.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }

}
