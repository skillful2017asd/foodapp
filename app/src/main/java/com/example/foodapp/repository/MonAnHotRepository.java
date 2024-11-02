package com.example.foodapp.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.foodapp.retrofit.FoodApi;
import com.example.foodapp.retrofit.ResponseMonAnHot;
import com.example.foodapp.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonAnHotRepository {

    FoodApi api;

    public MonAnHotRepository() {
        api = RetrofitInstance.getRetrofit().create(FoodApi.class);
    }

    public MutableLiveData<ResponseMonAnHot> mutableLiveData(){
        MutableLiveData<ResponseMonAnHot> data = new MutableLiveData<>();
        api.getMonAnHot().enqueue(new Callback<ResponseMonAnHot>() {
            @Override
            public void onResponse(Call<ResponseMonAnHot> call, Response<ResponseMonAnHot> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ResponseMonAnHot> call, Throwable throwable) {
                data.setValue(null);
                Log.d("logg",throwable.getMessage());
            }
        });
        return data;
    }
}
