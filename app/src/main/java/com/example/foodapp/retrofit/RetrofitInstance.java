package com.example.foodapp.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofit;
    public  static Retrofit getRetrofit(){
        if(retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://192.168.1.6/appfood/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return  retrofit;
    };
}
