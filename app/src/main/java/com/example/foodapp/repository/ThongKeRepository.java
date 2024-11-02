package com.example.foodapp.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.foodapp.model.ThongKe;
import com.example.foodapp.retrofit.FoodApi;
import com.example.foodapp.retrofit.ResponseThongKe;
import com.example.foodapp.retrofit.RetrofitInstance;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongKeRepository {
    private FoodApi api;

    public ThongKeRepository() {
        api = RetrofitInstance.getRetrofit().create(FoodApi.class);
    }

    public MutableLiveData<ResponseThongKe> mutableLiveData(String id) {
        MutableLiveData<ResponseThongKe> data = new MutableLiveData<>();
        api.getThongke(id).enqueue(new Callback<ResponseThongKe>() {
            @Override
            public void onResponse(Call<ResponseThongKe> call, Response<ResponseThongKe> response) {
                data.setValue(response.body());
                Log.d("API Response", "Response body: " + new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseThongKe> call, Throwable throwable) {
                data.setValue(null);
                Log.e("API Response", throwable.getMessage());
            }
        });
        return data;
    }
}
