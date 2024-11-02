package com.example.foodapp.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodapp.repository.CategoryRepository;
import com.example.foodapp.repository.MealRepository;
import com.example.foodapp.retrofit.ResponseCategory;
import com.example.foodapp.retrofit.ResponseMeals;

public class HomeViewModel extends ViewModel {
    private CategoryRepository categoryRepository ;
    //private MealRepository mealRepository;

    public HomeViewModel(){
        categoryRepository = new CategoryRepository();
       // mealRepository = new MealRepository();
    }
    public MutableLiveData<ResponseCategory> categoryMutableLiveData(){
        return  categoryRepository.getCategory();
    }

//    public MutableLiveData<ResponseMeals> mealsMutableLiveData(int idcate){
//        return mealRepository.getMeals(idcate);
//    }
}
