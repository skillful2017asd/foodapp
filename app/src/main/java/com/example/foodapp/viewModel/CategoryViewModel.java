package com.example.foodapp.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodapp.repository.MealRepository;
import com.example.foodapp.retrofit.ResponseMeals;

public class CategoryViewModel extends ViewModel {
    MealRepository mealRepository;

    public CategoryViewModel (){
        mealRepository = new MealRepository();
    }
    public MutableLiveData<ResponseMeals> responseMealsMutableLiveData(int idcate){
        return mealRepository.getMeals(idcate);
    }
}
