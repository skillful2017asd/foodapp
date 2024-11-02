package com.example.foodapp.retrofit;

import com.example.foodapp.model.MessModel;
import com.example.foodapp.model.ThongKe;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface FoodApi {
    @GET("category.php")
    Call<ResponseCategory>  getCaterogy();

    @POST("meals.php")
    @FormUrlEncoded
    Call<ResponseMeals> getMeals(
            @Field("idcate") int idcate
    );

    @POST("mealdetail.php")
    @FormUrlEncoded
    Call<ResponseMealDetail> getMealDetail(
            @Field("id") int id
    );
    @POST("cart.php")
    @FormUrlEncoded
    Call<MessModel> postCard(
            @Field("iduser") String id,
            @Field("amount") int amount,
            @Field("total") double total,
            @Field("detail") String  detail
    );

   @POST("thongkethang.php")
    @FormUrlEncoded
    Call<ResponseThongKe> getThongke(
            @Field("iduser") String id
   );

   @GET("monanhot.php")
    Call<ResponseMonAnHot> getMonAnHot();
}
