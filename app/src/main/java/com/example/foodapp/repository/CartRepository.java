package com.example.foodapp.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.foodapp.model.MessModel;
import com.example.foodapp.retrofit.FoodApi;
import com.example.foodapp.retrofit.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartRepository {
    private FoodApi api;
    private MutableLiveData<MessModel> data;

    public CartRepository() {
        api = RetrofitInstance.getRetrofit().create(FoodApi.class);
        data = new MutableLiveData<>();
    }
    public void checkOut(String iduser,int amount,double total,String detail){
        api.postCard(iduser,amount,total,detail).enqueue(new Callback<MessModel>() {
            @Override
            public void onResponse(Call<MessModel> call, Response<MessModel> response) {
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<MessModel> call, Throwable throwable) {
                data.postValue(null);
                Log.d("logg",throwable.getMessage());
            }
        });
    }
    public MutableLiveData<MessModel> messModelMutableLiveData(){
        return data;
    }

}
